(() => {
    "use strict";

    const API_PRODUCTOS = "/api/productos";

    const lista = document.getElementById("listaProductos");
    const estado = document.getElementById("estadoCatalogo");
    const sinProductos = document.getElementById("sinProductos");
    const busqueda = document.getElementById("busqueda");
    const formBusqueda = document.getElementById("formBusqueda");
    const orden = document.getElementById("ordenProductos");
    const botonesCategoria = [
        ...document.querySelectorAll("[data-categoria]")
    ];
    const limpiar = document.getElementById("limpiarFiltros");

    let productos = [];
    let categoriaActiva = "todas";
    let termino = "";

    document.getElementById("anioActual").textContent =
        new Date().getFullYear();

    const texto = (valor) =>
        valor == null ? "" : String(valor);

    const normalizar = (valor) =>
        texto(valor)
            .normalize("NFD")
            .replace(/[\u0300-\u036f]/g, "")
            .toLowerCase()
            .trim();

    const moneda = (valor) => {
        const numero = Number(valor);

        if (!Number.isFinite(numero)) {
            return "Precio por consultar";
        }

        return new Intl.NumberFormat("es-CO", {
            style: "currency",
            currency: "COP",
            maximumFractionDigits: 0
        }).format(numero);
    };

    function categoriaProducto(producto) {
        return normalizar(producto.categoria || "otros");
    }

    function iconoCategoria(categoria) {
        const c = normalizar(categoria);

        if (
            c.includes("tecnolog") ||
            c.includes("electron") ||
            c.includes("comput")
        ) {
            return "⌁";
        }

        if (
            c.includes("hogar") ||
            c.includes("casa") ||
            c.includes("mueble")
        ) {
            return "⌂";
        }

        if (
            c.includes("moda") ||
            c.includes("ropa") ||
            c.includes("calzado")
        ) {
            return "◇";
        }

        if (c.includes("deport")) {
            return "↗";
        }

        return "✦";
    }

    function crearTarjeta(producto, indice) {
        const article = document.createElement("article");
        article.className = "product-card";

        // Imagen o ilustración del producto
        const imagen = document.createElement("div");
        imagen.className = "product-image";

        const arte = document.createElement("span");
        arte.className = "product-art";
        arte.setAttribute("aria-hidden", "true");
        arte.textContent = iconoCategoria(producto.categoria);

        imagen.appendChild(arte);

        // Etiqueta del producto
        const badge = document.createElement("span");
        badge.className = "product-badge";
        badge.textContent = indice < 3
            ? "DESTACADO"
            : "DISPONIBLE";

        imagen.appendChild(badge);

        // Información del producto
        const info = document.createElement("div");
        info.className = "product-info";

        const categoria = document.createElement("span");
        categoria.className = "product-category";
        categoria.textContent =
            texto(producto.categoria) || "General";

        const titulo = document.createElement("h3");
        titulo.textContent =
            texto(producto.nombre) || "Producto sin nombre";

        const descripcion = document.createElement("p");
        descripcion.className = "product-description";
        descripcion.textContent =
            texto(producto.descripcion) ||
            "Consulta los detalles de este producto.";

        const vendedor = document.createElement("p");
        vendedor.className = "product-seller";
        vendedor.textContent = producto.vendedor
            ? `Vendido por ${texto(producto.vendedor)}`
            : "Vendedor del Marketplace";

        // Precio y existencias
        const pie = document.createElement("div");
        pie.className = "product-footer";

        const precioGrupo = document.createElement("div");

        const precio = document.createElement("div");
        precio.className = "product-price";
        precio.textContent = moneda(producto.precio);

        const stock = document.createElement("div");
        stock.className = "product-stock";

        const cantidadStock = Number(producto.stock);

        if (Number.isFinite(cantidadStock)) {
            stock.textContent = cantidadStock > 0
                ? `${cantidadStock} disponibles`
                : "Agotado";
        } else {
            stock.textContent = "Consulta disponibilidad";
        }

        precioGrupo.append(precio, stock);

        // El usuario inicia sesión para continuar con la compra
        const boton = document.createElement("a");
        boton.className = "product-open";
        boton.href = "/login.html";
        boton.setAttribute(
            "aria-label",
            `Inicia sesión para comprar ${texto(producto.nombre)}`
        );
        boton.title = "Inicia sesión para comprar";
        boton.textContent = "→";

        pie.append(precioGrupo, boton);

        info.append(
            categoria,
            titulo,
            descripcion,
            vendedor,
            pie
        );

        article.append(imagen, info);

        return article;
    }

    function renderizar() {
        // Filtrar por categoría y por texto de búsqueda
        const filtrados = productos.filter((producto) => {
            const coincideCategoria =
                categoriaActiva === "todas" ||
                categoriaProducto(producto).includes(
                    normalizar(categoriaActiva)
                );

            const bolsa = normalizar(
                `${producto.nombre || ""} ` +
                `${producto.descripcion || ""} ` +
                `${producto.categoria || ""} ` +
                `${producto.vendedor || ""}`
            );

            const coincideBusqueda =
                !termino ||
                bolsa.includes(normalizar(termino));

            return coincideCategoria && coincideBusqueda;
        });

        // Ordenar resultados
        if (orden.value === "precio-asc") {
            filtrados.sort(
                (a, b) =>
                    Number(a.precio || 0) -
                    Number(b.precio || 0)
            );
        }

        if (orden.value === "precio-desc") {
            filtrados.sort(
                (a, b) =>
                    Number(b.precio || 0) -
                    Number(a.precio || 0)
            );
        }

        if (orden.value === "nombre") {
            filtrados.sort(
                (a, b) =>
                    texto(a.nombre).localeCompare(
                        texto(b.nombre),
                        "es"
                    )
            );
        }

        // Dibujar las tarjetas
        lista.replaceChildren();

        filtrados.forEach((producto, indice) => {
            lista.appendChild(
                crearTarjeta(producto, indice)
            );
        });

        // Mostrar el estado del catálogo
        const hayResultados = filtrados.length !== 0;

        sinProductos.classList.toggle(
            "hidden",
            hayResultados
        );

        lista.classList.toggle(
            "hidden",
            !hayResultados
        );

        estado.textContent = hayResultados
            ? `${filtrados.length} ${
                filtrados.length === 1
                    ? "producto disponible"
                    : "productos disponibles"
            }`
            : "";
    }

    async function cargarProductos() {
        estado.textContent = "Cargando productos...";

        try {
            const respuesta = await fetch(API_PRODUCTOS, {
                headers: {
                    "Accept": "application/json"
                }
            });

            if (!respuesta.ok) {
                throw new Error(
                    "No fue posible cargar el catálogo."
                );
            }

            const datos = await respuesta.json();

            if (!Array.isArray(datos)) {
                throw new Error(
                    "La respuesta del catálogo no tiene el formato esperado."
                );
            }

            productos = datos;
            renderizar();

            if (productos.length === 0) {
                estado.textContent =
                    "Todavía no hay productos publicados.";

                sinProductos.classList.remove("hidden");

                sinProductos.querySelector("h3").textContent =
                    "Todavía no hay productos";

                sinProductos.querySelector("p").textContent =
                    "Cuando los vendedores publiquen artículos, aparecerán aquí.";

                lista.classList.add("hidden");
            }
        } catch (error) {
            console.error(
                "Error cargando los productos:",
                error
            );

            estado.textContent =
                "No se pudo conectar con el catálogo. Comprueba que el servidor esté ejecutándose.";

            lista.classList.add("hidden");
            sinProductos.classList.remove("hidden");

            sinProductos.querySelector("h3").textContent =
                "No pudimos cargar los productos";

            sinProductos.querySelector("p").textContent =
                "Inténtalo de nuevo cuando el Marketplace esté disponible.";
        }
    }

    // Buscar al enviar el formulario
    formBusqueda.addEventListener("submit", (event) => {
        event.preventDefault();

        termino = busqueda.value.trim();

        renderizar();

        document.getElementById("productos").scrollIntoView({
            behavior: "smooth"
        });
    });

    // Actualizar resultados mientras se escribe
    busqueda.addEventListener("input", () => {
        termino = busqueda.value.trim();
        renderizar();
    });

    // Filtrar por categoría
    botonesCategoria.forEach((boton) => {
        boton.addEventListener("click", () => {
            categoriaActiva = boton.dataset.categoria;

            botonesCategoria.forEach((otroBoton) => {
                otroBoton.classList.toggle(
                    "selected",
                    otroBoton === boton
                );
            });

            renderizar();
        });
    });

    // Cambiar el orden de los productos
    orden.addEventListener("change", renderizar);

    // Restablecer filtros
    limpiar.addEventListener("click", () => {
        termino = "";
        busqueda.value = "";

        categoriaActiva = "todas";
        orden.value = "relevancia";

        botonesCategoria.forEach((boton) => {
            boton.classList.toggle(
                "selected",
                boton.dataset.categoria === "todas"
            );
        });

        renderizar();
    });

    // Iniciar el catálogo
    cargarProductos();
})();