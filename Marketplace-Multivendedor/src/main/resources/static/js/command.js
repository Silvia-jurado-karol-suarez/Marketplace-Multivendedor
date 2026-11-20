
const API_COMMAND = "/api/command";
const API_PRODUCTOS = "/api/productos";

let productos = [];

document.addEventListener("DOMContentLoaded", () => {
    cargarProductos();
    consultarEstado();

    document.getElementById("productoId")
        .addEventListener("change", mostrarStock);

    document.getElementById("btnEjecutar")
        .addEventListener("click", actualizarStock);

    document.getElementById("btnDeshacer")
        .addEventListener("click", deshacerComando);
});

// Cargar productos registrados en el Marketplace
async function cargarProductos() {
    const selector = document.getElementById("productoId");
    const mensaje = document.getElementById("mensaje");

    try {
        const respuesta = await fetch(API_PRODUCTOS);

        if (!respuesta.ok) {
            throw new Error("No fue posible consultar los productos.");
        }

        productos = await respuesta.json();

        selector.innerHTML =
            '<option value="">Selecciona un producto</option>';

        productos.forEach(producto => {
            const opcion = document.createElement("option");

            opcion.value = producto.id;
            opcion.textContent =
                `${producto.nombre} - Stock: ${producto.stock}`;

            selector.appendChild(opcion);
        });

        if (productos.length === 0) {
            selector.innerHTML =
                '<option value="">No hay productos registrados</option>';
        }

    } catch (error) {
        mensaje.textContent = error.message;
    }
}

// Mostrar el stock del producto seleccionado
function mostrarStock() {
    const id = document.getElementById("productoId").value;
    const producto = productos.find(p => String(p.id) === String(id));

    document.getElementById("stockActual").textContent =
        producto
            ? `Stock actual: ${producto.stock} unidades`
            : "Stock actual: --";
}

// Ejecutar el comando para actualizar el inventario
async function actualizarStock() {
    const id = document.getElementById("productoId").value;
    const stock = document.getElementById("nuevoStock").value;
    const mensaje = document.getElementById("mensaje");

    if (!id || stock === "" || Number(stock) < 0) {
        mensaje.textContent =
            "Selecciona un producto e ingresa una cantidad válida.";
        return;
    }

    try {
        const respuesta = await fetch(
            `${API_COMMAND}/actualizar-stock/${encodeURIComponent(id)}?stock=${stock}`,
            { method: "PUT" }
        );

        const texto = await respuesta.text();
        mensaje.textContent = texto;

        if (respuesta.ok) {
            await cargarProductos();

            document.getElementById("productoId").value = id;
            mostrarStock();

            document.getElementById("nuevoStock").value = "";

            await consultarEstado();
        }

    } catch (error) {
        mensaje.textContent = "No se pudo conectar con el servidor.";
    }
}

// Deshacer la última operación
async function deshacerComando() {
    const mensaje = document.getElementById("mensaje");

    try {
        const respuesta = await fetch(
            `${API_COMMAND}/deshacer`,
            { method: "POST" }
        );

        mensaje.textContent = await respuesta.text();

        if (respuesta.ok) {
            await cargarProductos();
            mostrarStock();
        }

        await consultarEstado();

    } catch (error) {
        mensaje.textContent = "No se pudo deshacer la operación.";
    }
}

// Consultar si existe una operación para deshacer
async function consultarEstado() {
    try {
        const respuesta = await fetch(`${API_COMMAND}/estado`);
        const disponible = await respuesta.json();

        document.getElementById("btnDeshacer").disabled = !disponible;

    } catch (error) {
        document.getElementById("btnDeshacer").disabled = true;
    }
}
