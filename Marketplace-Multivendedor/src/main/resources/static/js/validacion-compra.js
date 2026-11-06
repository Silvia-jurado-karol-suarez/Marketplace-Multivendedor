
document.addEventListener("DOMContentLoaded", () => {

    const selector = document.getElementById("productoValidacion");
    const stockInput = document.getElementById("stockDisponible");
    const cantidadInput = document.getElementById("cantidadSolicitada");
    const boton = document.getElementById("btnValidarCompra");
    const resultado = document.getElementById("resultadoChain");

    if (!selector || !stockInput || !cantidadInput || !boton || !resultado) {
        console.warn("No se encontraron los elementos de Chain of Responsibility.");
        return;
    }

    let productos = [];

    // ==========================================
    // CARGAR PRODUCTOS DESDE COMPOSITE
    // ==========================================

    async function cargarProductosValidacion() {

        try {

            const respuesta = await fetch("/composite/catalogo");

            if (!respuesta.ok) {
                throw new Error("No se pudo cargar el catálogo.");
            }

            const datos = await respuesta.json();

            productos = [];

            (datos.categorias || []).forEach(categoria => {

                (categoria.elementos || []).forEach(producto => {
                    productos.push(producto);
                });

            });

            selector.innerHTML =
                '<option value="">Selecciona un producto</option>';

            productos.forEach(producto => {

                const opcion = document.createElement("option");

                opcion.value = producto.id;
                opcion.textContent = producto.nombre;

                selector.appendChild(opcion);

            });

            if (productos.length === 0) {
                resultado.textContent = "No hay productos disponibles.";
            }

        } catch (error) {

            resultado.textContent = error.message;
            console.error(error);

        }

    }

    // ==========================================
    // MOSTRAR STOCK DEL PRODUCTO SELECCIONADO
    // ==========================================

    selector.addEventListener("change", () => {

        const producto = productos.find(
            p => String(p.id) === String(selector.value)
        );

        if (!producto) {

            stockInput.value = "";
            cantidadInput.value = 1;
            cantidadInput.removeAttribute("max");

            resultado.textContent = "Selecciona un producto.";

            return;
        }

        stockInput.value = producto.stock ?? 0;

        cantidadInput.value = 1;
        cantidadInput.max = producto.stock ?? 0;

        resultado.textContent =
            `Producto seleccionado: ${producto.nombre}. ` +
            `Stock disponible: ${producto.stock}.`;

    });

    // ==========================================
    // EJECUTAR CHAIN OF RESPONSIBILITY
    // ==========================================

    boton.addEventListener("click", async () => {

        const producto = productos.find(
            p => String(p.id) === String(selector.value)
        );

        const cantidad = Number(cantidadInput.value);

        if (!producto) {
            resultado.textContent = "Selecciona un producto.";
            return;
        }

        if (!Number.isInteger(cantidad) || cantidad <= 0) {
            resultado.textContent = "Ingresa una cantidad válida.";
            return;
        }

        if (cantidad > Number(producto.stock)) {
            resultado.textContent =
                `Stock insuficiente. Disponibles: ${producto.stock}.`;
            return;
        }

        boton.disabled = true;

        resultado.textContent = "Ejecutando validaciones...";

        try {

            // ESTRUCTURA ESPERADA POR EL BACKEND

            const solicitud = {

                productos: [
                    {
                        producto: {
                            stock: Number(producto.stock)
                        },
                        cantidad: cantidad
                    }
                ],

                metodoPago: "tarjeta",

                tipoEnvio: "nacional"

            };

            console.log("Solicitud enviada:", solicitud);

            const respuesta = await fetch(
                "/api/validaciones/compra",
                {
                    method: "POST",

                    headers: {
                        "Content-Type": "application/json"
                    },

                    body: JSON.stringify(solicitud)
                }
            );

            const data = await respuesta.json();

            console.log("Respuesta del servidor:", data);

            if (!respuesta.ok) {
                throw new Error(
                    data.message || data.mensaje ||
                    "No se pudo validar la compra."
                );
            }

            if (data.valido) {

                resultado.innerHTML = `
                    <h3>Disponibilidad aprobada</h3>
                    <p>${data.mensaje}</p>
                    <p>Producto: ${producto.nombre}</p>
                    <p>Cantidad solicitada: ${cantidad}</p>
                `;

            } else {

                resultado.innerHTML = `
                    <h3>Validación rechazada</h3>
                    <p>${data.mensaje}</p>
                `;

            }

        } catch (error) {

            resultado.textContent = error.message;
            console.error("Error de validación:", error);

        } finally {

            boton.disabled = false;

        }

    });

    // CARGAR CATÁLOGO AL ABRIR LA PÁGINA
    cargarProductosValidacion();

});
