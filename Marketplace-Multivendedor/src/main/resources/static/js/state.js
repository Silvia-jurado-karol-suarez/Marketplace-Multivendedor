const API_PEDIDOS = "/pedidos";

let pedidos = [];
let pedidoSeleccionado = null;

document.addEventListener("DOMContentLoaded", () => {
    cargarPedidos();

    document.getElementById("pedidoSelect")
        .addEventListener("change", seleccionarPedido);
});

// Consultar pedidos registrados en MongoDB
async function cargarPedidos() {
    const selector = document.getElementById("pedidoSelect");

    mostrarMensaje("Consultando pedidos...", "");

    try {
        const respuesta = await fetch(API_PEDIDOS);

        if (!respuesta.ok) {
            throw new Error("No se pudieron consultar los pedidos.");
        }

        pedidos = await respuesta.json();

        selector.innerHTML = "";

        if (pedidos.length === 0) {
            selector.innerHTML =
                '<option value="">No hay pedidos registrados</option>';

            pedidoSeleccionado = null;
            actualizarInterfaz();
            mostrarMensaje("No existen pedidos para realizar las pruebas.", "");
            return;
        }

        selector.innerHTML =
            '<option value="">Selecciona un pedido</option>';

        pedidos.forEach(pedido => {
            const opcion = document.createElement("option");

            opcion.value = pedido.id;
            opcion.textContent =
                "Pedido #" + pedido.id +
                " - " + (pedido.estado || "Sin estado");

            selector.appendChild(opcion);
        });

        mostrarMensaje(
            "Se cargaron " + pedidos.length + " pedido(s) correctamente.",
            "exito"
        );

    } catch (error) {
        mostrarMensaje(error.message, "error");
    }
}

// Seleccionar un pedido
function seleccionarPedido() {
    const id = document.getElementById("pedidoSelect").value;

    pedidoSeleccionado = pedidos.find(
        pedido => pedido.id === id
    ) || null;

    actualizarInterfaz();

    if (pedidoSeleccionado) {
        mostrarMensaje("Pedido seleccionado correctamente.", "exito");
    } else {
        mostrarMensaje("Selecciona un pedido para comenzar.", "");
    }
}

// Actualizar los elementos visuales
function actualizarInterfaz() {
    const estadoElemento = document.getElementById("estadoActual");
    const idElemento = document.getElementById("idPedido");

    if (!pedidoSeleccionado) {
        estadoElemento.textContent = "Sin seleccionar";
        idElemento.textContent = "Selecciona un pedido para comenzar.";

        actualizarFlujo("");
        actualizarBotones("");
        return;
    }

    const estado = normalizarEstado(pedidoSeleccionado.estado);

    estadoElemento.textContent = estado;
    idElemento.textContent =
        "Identificador: " + pedidoSeleccionado.id;

    actualizarFlujo(estado);
    actualizarBotones(estado);
}

// Normalizar el nombre del estado
function normalizarEstado(estado) {
    if (!estado) {
        return "";
    }

    return estado
        .trim()
        .toUpperCase()
        .normalize("NFD")
        .replace(/[\u0300-\u036f]/g, "");
}

// Resaltar el estado actual en el flujo
function actualizarFlujo(estado) {
    const estados = [
        "PENDIENTE",
        "EN PREPARACION",
        "ENVIADO",
        "ENTREGADO"
    ];

    const elementos = [
        "pasoPendiente",
        "pasoPreparacion",
        "pasoEnviado",
        "pasoEntregado"
    ];

    const posicion = estados.indexOf(
        normalizarEstado(estado)
    );

    elementos.forEach((id, indice) => {
        const elemento = document.getElementById(id);

        elemento.classList.toggle(
            "activo",
            posicion !== -1 && indice <= posicion
        );
    });
}

// Habilitar únicamente las acciones correspondientes
function actualizarBotones(estado) {
    document.getElementById("btnPreparar").disabled =
        estado !== "PENDIENTE";

    document.getElementById("btnEnviar").disabled =
        estado !== "EN PREPARACION";

    document.getElementById("btnEntregar").disabled =
        estado !== "ENVIADO";
}

// Cambiar el estado mediante el backend State
async function cambiarEstado(nuevoEstado) {
    if (!pedidoSeleccionado) {
        mostrarMensaje("Primero debes seleccionar un pedido.", "error");
        return;
    }

    const id = pedidoSeleccionado.id;

    mostrarMensaje("Procesando transición de estado...", "");

    try {
        const url = API_PEDIDOS + "/" +
            encodeURIComponent(id) +
            "/estado?estado=" +
            encodeURIComponent(nuevoEstado);

        const respuesta = await fetch(url, {
            method: "PATCH"
        });

        if (!respuesta.ok) {
            const detalle = await respuesta.text();

            throw new Error(
                "Transición rechazada: " +
                (detalle || respuesta.statusText)
            );
        }

        const pedidoActualizado = await respuesta.json();

        pedidoSeleccionado = pedidoActualizado;

        // Actualizar el pedido en la lista local
        pedidos = pedidos.map(pedido =>
            pedido.id === pedidoActualizado.id
                ? pedidoActualizado
                : pedido
        );

        actualizarInterfaz();

        // Actualizar el texto del selector
        const opcion = document.querySelector(
            '#pedidoSelect option[value="' + id + '"]'
        );

        if (opcion) {
            opcion.textContent =
                "Pedido #" + id +
                " - " + pedidoActualizado.estado;
        }

        mostrarMensaje(
            "Transición realizada correctamente. Nuevo estado: " +
            pedidoActualizado.estado,
            "exito"
        );

    } catch (error) {
        mostrarMensaje(error.message, "error");
    }
}

// Mostrar mensajes en pantalla
function mostrarMensaje(texto, tipo) {
    const mensaje = document.getElementById("mensaje");

    mensaje.textContent = texto;
    mensaje.className = "mensaje";

    if (tipo) {
        mensaje.classList.add(tipo);
    }
}