
document.addEventListener("DOMContentLoaded", () => {

    // ==========================================
    // CONFIGURACIÓN DE LOS ENDPOINTS
    // ==========================================

    const API_PEDIDOS = "/pedidos";
    const API_OBSERVER = "/api/observer";

    // ==========================================
    // ELEMENTOS DEL HTML
    // ==========================================

    const pedidoSelect = document.getElementById("pedidoSelect");
    const nuevoEstado = document.getElementById("nuevoEstado");
    const btnActualizar = document.getElementById("btnActualizar");

    const mensaje = document.getElementById("mensaje");
    const resultado = document.getElementById("resultadoNotificaciones");
    const detallePedido = document.getElementById("detallePedido");

    const cantidadObservadores =
        document.getElementById("cantidadObservadores");

    const listaObservadores =
        document.getElementById("listaObservadores");

    const btnConsultarEventos =
        document.getElementById("btnConsultarEventos");

    const historialEventos =
        document.getElementById("historialEventos");

    // ==========================================
    // VALIDAR ELEMENTOS HTML
    // ==========================================

    if (
        !pedidoSelect ||
        !nuevoEstado ||
        !btnActualizar ||
        !mensaje ||
        !resultado ||
        !detallePedido ||
        !cantidadObservadores ||
        !listaObservadores ||
        !btnConsultarEventos ||
        !historialEventos
    ) {
        console.error(
            "Error: faltan elementos HTML necesarios para Observer."
        );
        return;
    }

    let pedidosCargados = [];

    // ==========================================
    // MOSTRAR MENSAJES
    // ==========================================

    function mostrarMensaje(texto, error = false) {
        mensaje.textContent = texto;
        mensaje.classList.toggle("error", error);
    }

    // ==========================================
    // CREAR ELEMENTOS HTML
    // ==========================================

    function crearElemento(etiqueta, texto, clase = "") {
        const elemento = document.createElement(etiqueta);
        elemento.textContent = texto;

        if (clase) {
            elemento.className = clase;
        }

        return elemento;
    }

    // ==========================================
    // CARGAR PEDIDOS
    // ==========================================

    async function cargarPedidos() {

        pedidoSelect.disabled = true;
        pedidoSelect.innerHTML =
            '<option value="">Cargando pedidos...</option>';

        try {

            const respuesta = await fetch(API_PEDIDOS, {
                method: "GET",
                headers: {
                    "Accept": "application/json"
                }
            });

            if (!respuesta.ok) {
                throw new Error(
                    "Error al consultar pedidos: " + respuesta.status
                );
            }

            const datos = await respuesta.json();

            if (!Array.isArray(datos)) {
                throw new Error(
                    "La respuesta de pedidos no es una lista."
                );
            }

            pedidosCargados = datos;

            pedidoSelect.innerHTML =
                '<option value="">Selecciona un pedido</option>';

            if (pedidosCargados.length === 0) {
                pedidoSelect.innerHTML =
                    '<option value="">No hay pedidos registrados</option>';

                mostrarMensaje("No hay pedidos registrados.", true);
                return;
            }

            pedidosCargados.forEach((pedido, indice) => {

                const opcion = document.createElement("option");

                opcion.value = pedido.id;

                const productos = pedido.productos || [];

                const nombres = productos
                    .map(producto => producto.nombre)
                    .filter(Boolean)
                    .join(", ");

                opcion.textContent =
                    "Pedido " + (indice + 1) +
                    " - " +
                    (nombres || "Sin productos") +
                    " - Estado: " +
                    (pedido.estado || "Sin estado");

                pedidoSelect.appendChild(opcion);

            });

            mostrarMensaje(
                "Pedidos cargados correctamente. Selecciona uno para comenzar."
            );

            console.log("Pedidos cargados:", pedidosCargados);

        } catch (error) {

            console.error("Error cargando pedidos:", error);

            pedidoSelect.innerHTML =
                '<option value="">Error al cargar pedidos</option>';

            mostrarMensaje(error.message, true);

        } finally {

            pedidoSelect.disabled = false;

        }
    }

    // ==========================================
    // MOSTRAR DETALLE DEL PEDIDO
    // ==========================================

    function mostrarDetallePedido(pedido) {

        detallePedido.replaceChildren();

        if (!pedido) {
            return;
        }

        detallePedido.appendChild(
            crearElemento("h3", "Productos del pedido")
        );

        detallePedido.appendChild(
            crearElemento("p", "ID del pedido: " + pedido.id)
        );

        detallePedido.appendChild(
            crearElemento(
                "p",
                "Estado actual: " + (pedido.estado || "Sin estado")
            )
        );

        detallePedido.appendChild(
            crearElemento(
                "p",
                "Total: $" +
                Number(pedido.total || 0).toLocaleString("es-CO")
            )
        );

        const productos = pedido.productos || [];

        productos.forEach(producto => {

            const tarjeta = crearElemento("div", "", "detalle-producto");

            tarjeta.appendChild(
                crearElemento(
                    "h4",
                    producto.nombre || "Producto sin nombre"
                )
            );

            tarjeta.appendChild(
                crearElemento(
                    "p",
                    "Descripción: " +
                    (producto.descripcion || "Sin descripción")
                )
            );

            tarjeta.appendChild(
                crearElemento(
                    "p",
                    "Precio: $" +
                    Number(producto.precio || 0).toLocaleString("es-CO")
                )
            );

            tarjeta.appendChild(
                crearElemento(
                    "p",
                    "Categoría: " +
                    (producto.categoria || "Sin categoría")
                )
            );

            detallePedido.appendChild(tarjeta);

        });
    }

    // ==========================================
    // CONSULTAR OBSERVADORES REGISTRADOS
    // ==========================================

    async function cargarObservadores() {

        cantidadObservadores.textContent = "Consultando...";
        listaObservadores.textContent = "Consultando observadores...";

        try {

            const [respuestaCantidad, respuestaObservadores] =
                await Promise.all([

                    fetch(API_OBSERVER + "/cantidad"),

                    fetch(API_OBSERVER + "/observadores")

                ]);

            if (!respuestaCantidad.ok || !respuestaObservadores.ok) {
                throw new Error(
                    "No se pudo consultar el sistema Observer."
                );
            }

            const cantidad = await respuestaCantidad.json();

            const observadores =
                await respuestaObservadores.json();

            cantidadObservadores.textContent = cantidad;

            listaObservadores.replaceChildren();

            if (
                !Array.isArray(observadores) ||
                observadores.length === 0
            ) {

                listaObservadores.textContent =
                    "No hay observadores registrados.";

                return;
            }

            observadores.forEach(nombre => {

                const elemento = crearElemento(
                    "p",
                    "● " + nombre,
                    "observador-activo"
                );

                listaObservadores.appendChild(elemento);

            });

        } catch (error) {

            console.error("Error consultando observadores:", error);

            cantidadObservadores.textContent = "Error";

            listaObservadores.textContent =
                "No se pudo consultar la información.";

        }
    }

    // ==========================================
    // CONSULTAR HISTORIAL DE NOTIFICACIONES
    // ==========================================

    async function consultarEventos() {

        historialEventos.textContent =
            "Consultando notificaciones...";

        try {

            const respuesta = await fetch(
                API_OBSERVER + "/eventos",
                {
                    method: "GET",
                    headers: {
                        "Accept": "application/json"
                    }
                }
            );

            if (!respuesta.ok) {
                const detalleError = await respuesta.text();

                throw new Error(
                    "Error " + respuesta.status + ": " + detalleError
                );
            }

            const eventos = await respuesta.json();

            historialEventos.replaceChildren();

            if (!Array.isArray(eventos) || eventos.length === 0) {

                historialEventos.appendChild(
                    crearElemento(
                        "p",
                        "Todavía no hay notificaciones registradas."
                    )
                );

                return;
            }

            eventos.forEach(evento => {

                const tarjeta = crearElemento(
                    "article",
                    "",
                    "tarjeta-notificacion"
                );

                tarjeta.appendChild(
                    crearElemento(
                        "h4",
                        evento.observador || "Observador"
                    )
                );

                tarjeta.appendChild(
                    crearElemento(
                        "p",
                        "Pedido: " +
                        (evento.idPedido || "No especificado")
                    )
                );

                tarjeta.appendChild(
                    crearElemento(
                        "p",
                        "Nuevo estado: " +
                        (evento.estado || "No especificado")
                    )
                );

                if (evento.fecha) {

                    tarjeta.appendChild(
                        crearElemento(
                            "p",
                            "Fecha: " + evento.fecha
                        )
                    );

                }

                historialEventos.appendChild(tarjeta);

            });

        } catch (error) {

            console.error("Error consultando eventos:", error);

            historialEventos.textContent = error.message;

        }
    }

    // ==========================================
    // SELECCIONAR PEDIDO
    // ==========================================

    pedidoSelect.addEventListener("change", () => {

        const idPedido = pedidoSelect.value;

        const pedido = pedidosCargados.find(
            elemento => elemento.id === idPedido
        );

        if (!pedido) {

            detallePedido.replaceChildren();

            mostrarMensaje("Selecciona un pedido para comenzar.");

            return;
        }

        mostrarDetallePedido(pedido);

        nuevoEstado.value = pedido.estado || "PENDIENTE";

        resultado.replaceChildren();

        mostrarMensaje("Pedido seleccionado correctamente.");

    });

    // ==========================================
    // ACTUALIZAR ESTADO DEL PEDIDO
    // ==========================================

    async function actualizarPedido() {

        const idPedido = pedidoSelect.value;
        const estado = nuevoEstado.value;

        if (!idPedido) {
            mostrarMensaje("Primero debes seleccionar un pedido.", true);
            return;
        }

        const pedidoAnterior = pedidosCargados.find(
            pedido => pedido.id === idPedido
        );

        if (
            pedidoAnterior &&
            pedidoAnterior.estado === estado
        ) {
            mostrarMensaje("El pedido ya tiene ese estado.", true);
            return;
        }

        btnActualizar.disabled = true;
        btnActualizar.textContent = "Actualizando...";

        try {

            const url =
                API_PEDIDOS + "/" +
                encodeURIComponent(idPedido) +
                "/estado?estado=" +
                encodeURIComponent(estado);

            const respuesta = await fetch(url, {
                method: "PATCH",
                headers: {
                    "Accept": "application/json"
                }
            });

            // Mostrar el mensaje real del backend
            if (!respuesta.ok) {

                const detalleError = await respuesta.text();

                throw new Error(
                    "Error " + respuesta.status + ": " + detalleError
                );
            }

            const pedidoActualizado = await respuesta.json();

            mostrarMensaje(
                "Pedido actualizado correctamente. Se notificó a los observadores."
            );

            resultado.replaceChildren();

            resultado.appendChild(
                crearElemento("h4", "Pedido actualizado")
            );

            resultado.appendChild(
                crearElemento(
                    "p",
                    "Identificador: " + pedidoActualizado.id
                )
            );

            resultado.appendChild(
                crearElemento(
                    "p",
                    "Nuevo estado: " + pedidoActualizado.estado
                )
            );

            // Recargar pedidos
            await cargarPedidos();

            pedidoSelect.value = idPedido;

            const pedidoRecargado = pedidosCargados.find(
                pedido => pedido.id === idPedido
            );

            if (pedidoRecargado) {
                mostrarDetallePedido(pedidoRecargado);
                nuevoEstado.value = pedidoRecargado.estado;
            }

            // Actualizar observadores e historial
            await cargarObservadores();

            await consultarEventos();

        } catch (error) {

            console.error("Error actualizando pedido:", error);

            mostrarMensaje(error.message, true);

        } finally {

            btnActualizar.disabled = false;
            btnActualizar.textContent = "Actualizar pedido";

        }
    }

    // ==========================================
    // EVENTOS DE LOS BOTONES
    // ==========================================

    btnActualizar.addEventListener(
        "click",
        actualizarPedido
    );

    btnConsultarEventos.addEventListener(
        "click",
        consultarEventos
    );

    // ==========================================
    // INICIAR MÓDULO OBSERVER
    // ==========================================

    console.log("Iniciando módulo Observer...");

    cargarPedidos();

    cargarObservadores();

});
