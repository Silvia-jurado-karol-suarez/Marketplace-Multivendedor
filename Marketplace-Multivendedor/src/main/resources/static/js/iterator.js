
const botonRecorrer = document.getElementById("btnRecorrer");
const botonSiguiente = document.getElementById("btnSiguiente");
const botonReiniciar = document.getElementById("btnReiniciar");

const productosGrid = document.getElementById("productosGrid");
const totalProductos = document.getElementById("totalProductos");
const estadoPatron = document.getElementById("estadoPatron");
const contador = document.getElementById("contador");
const mensaje = document.getElementById("mensaje");

const estadoRecorrido = document.getElementById("estadoRecorrido");
const barraProgreso = document.getElementById("barraProgreso");
const textoProgreso = document.getElementById("textoProgreso");

const posicionProducto = document.getElementById("posicionProducto");
const nombreActual = document.getElementById("nombreActual");
const descripcionActual = document.getElementById("descripcionActual");
const categoriaActual = document.getElementById("categoriaActual");
const stockActual = document.getElementById("stockActual");
const precioActual = document.getElementById("precioActual");

const haySiguiente = document.getElementById("haySiguiente");
const siguienteEstado = document.getElementById("siguienteEstado");
const posicionActual = document.getElementById("posicionActual");

const historial = document.getElementById("historialRecorrido");

let productos = [];
let posicion = 0;
let catalogoCargado = false;


// ESCAPAR TEXTO PARA MOSTRARLO EN HTML

function escaparHTML(valor) {

    return String(valor ?? "").replace(/[&<>"']/g, caracter => ({
        "&": "&amp;",
        "<": "&lt;",
        ">": "&gt;",
        '"': "&quot;",
        "'": "&#39;"
    })[caracter]);

}


// FORMATO DE PRECIO

function formatoPrecio(precio) {

    return new Intl.NumberFormat("es-CO", {
        style: "currency",
        currency: "COP",
        maximumFractionDigits: 0
    }).format(Number(precio) || 0);

}


// MOSTRAR LOS PRODUCTOS DEL CATÁLOGO

function mostrarCatalogo() {

    productosGrid.innerHTML = "";

    productos.forEach((producto, indice) => {

        const tarjeta = document.createElement("article");

        tarjeta.className = "product-card";

        tarjeta.innerHTML = `

            <div class="product-top">

                <span class="product-number">
                    PRODUCTO ${String(indice + 1).padStart(2, "0")}
                </span>

                <span class="category">
                    ${escaparHTML(producto.categoria || "Sin categoría")}
                </span>

            </div>

            <h3>
                ${escaparHTML(producto.nombre || "Sin nombre")}
            </h3>

            <p class="product-description">
                ${escaparHTML(producto.descripcion || "Sin descripción")}
            </p>

            <div class="product-price">
                ${formatoPrecio(producto.precio)}
            </div>

            <div class="stock">
                📦 Stock disponible:
                <strong>
                    ${escaparHTML(producto.stock ?? 0)}
                </strong>
            </div>

        `;

        productosGrid.appendChild(tarjeta);

    });

}


// ACTUALIZAR LOS INDICADORES

function actualizarEstado() {

    const quedanProductos = posicion < productos.length;

    haySiguiente.textContent = quedanProductos ? "true" : "false";

    posicionActual.textContent = posicion;

    textoProgreso.textContent =
        `${posicion} de ${productos.length} productos`;

    const porcentaje = productos.length > 0
        ? (posicion / productos.length) * 100
        : 0;

    barraProgreso.style.width = `${porcentaje}%`;

    botonSiguiente.disabled = !catalogoCargado || !quedanProductos;

    if (!quedanProductos && productos.length > 0) {

        estadoRecorrido.textContent = "Recorrido terminado";

        estadoPatron.textContent = "Completado";

        siguienteEstado.textContent = "Sin elementos";

    }

}


// RECORRER EL SIGUIENTE PRODUCTO

function mostrarSiguiente() {

    if (!catalogoCargado || posicion >= productos.length) {

        actualizarEstado();

        return;

    }

    const producto = productos[posicion];

    posicion++;

    posicionProducto.textContent =
        `PRODUCTO ${String(posicion).padStart(2, "0")}`;

    nombreActual.textContent =
        producto.nombre || "Sin nombre";

    descripcionActual.textContent =
        producto.descripcion || "Sin descripción";

    categoriaActual.textContent =
        `Categoría: ${producto.categoria || "Sin categoría"}`;

    stockActual.textContent =
        `Stock: ${producto.stock ?? 0}`;

    precioActual.textContent =
        formatoPrecio(producto.precio);

    siguienteEstado.textContent = "Producto obtenido";

    estadoRecorrido.textContent = "Recorriendo";

    if (posicion === 1) {

        historial.innerHTML = "";

    }

    const elemento = document.createElement("li");

    elemento.textContent =
        `${producto.nombre || "Sin nombre"} — ${producto.categoria || "Sin categoría"}`;

    historial.appendChild(elemento);

    actualizarEstado();

}


// REINICIAR RECORRIDO VISUAL

function reiniciarRecorrido() {

    posicion = 0;

    nombreActual.textContent = "Sin iniciar";

    descripcionActual.textContent =
        "Pulsa siguiente producto para comenzar.";

    categoriaActual.textContent = "Categoría: --";

    stockActual.textContent = "Stock: --";

    precioActual.textContent = "$0";

    posicionProducto.textContent = "PRODUCTO 0";

    siguienteEstado.textContent =
        catalogoCargado ? "Listo" : "Pendiente";

    estadoRecorrido.textContent =
        catalogoCargado ? "Listo para iniciar" : "Esperando inicio";

    historial.innerHTML =
        "<li>Aún no se ha iniciado el recorrido.</li>";

    actualizarEstado();

}


// CONSULTAR EL ENDPOINT REAL DE ITERATOR

async function cargarProductos() {

    botonRecorrer.disabled = true;

    botonRecorrer.textContent = "Consultando catálogo...";

    mensaje.className = "message";

    mensaje.textContent =
        "Consultando los productos mediante el patrón Iterator...";

    try {

        const respuesta = await fetch("/api/iterator/productos");

        if (!respuesta.ok) {

            throw new Error(
                "No se pudo consultar el catálogo de productos."
            );

        }

        const datos = await respuesta.json();

        if (datos.patron !== "Iterator") {

            throw new Error(
                "La respuesta recibida no corresponde al patrón Iterator."
            );

        }

        productos = datos.productos || [];

        posicion = 0;

        catalogoCargado = productos.length > 0;

        totalProductos.textContent =
            datos.cantidadRecorrida ?? productos.length;

        contador.textContent =
            `${productos.length} productos`;

        mostrarCatalogo();

        reiniciarRecorrido();

        mensaje.textContent =
            `${datos.mensaje}. Se encontraron ${productos.length} productos.`;

        botonReiniciar.disabled = !catalogoCargado;

        estadoPatron.textContent =
            catalogoCargado ? "Listo para recorrer" : "Sin productos";

        if (!catalogoCargado) {

            estadoRecorrido.textContent = "Catálogo vacío";

            siguienteEstado.textContent = "Sin elementos";

        }

    } catch (error) {

        catalogoCargado = false;

        productos = [];

        totalProductos.textContent = "0";

        contador.textContent = "0 productos";

        estadoPatron.textContent = "Error";

        estadoRecorrido.textContent = "Error de consulta";

        mensaje.className = "message error";

        mensaje.textContent = error.message;

        productosGrid.innerHTML = "";

        botonSiguiente.disabled = true;

        botonReiniciar.disabled = true;

        console.error("Error en Iterator:", error);

    } finally {

        botonRecorrer.disabled = false;

        botonRecorrer.textContent = "↻ Cargar catálogo";

    }

}


// EVENTOS

botonRecorrer.addEventListener("click", cargarProductos);

botonSiguiente.addEventListener("click", mostrarSiguiente);

botonReiniciar.addEventListener("click", reiniciarRecorrido);


// CARGAR AL ABRIR LA PÁGINA

cargarProductos();
