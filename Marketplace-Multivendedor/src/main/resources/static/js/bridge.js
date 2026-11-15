
function esperar(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}

// Reiniciar las tarjetas
function reiniciarComponentes() {
    const componentes = [
        "notificacion",
        "canalVisual",
        "envio"
    ];

    componentes.forEach(id => {
        const elemento = document.getElementById(id);

        elemento.classList.remove("activo", "completado");
        elemento.querySelector("small").textContent = "Pendiente";
    });
}

// Animar cada etapa
async function activarComponente(id, texto) {
    const elemento = document.getElementById(id);

    elemento.classList.add("activo");
    elemento.querySelector("small").textContent = "Procesando";

    document.getElementById("mensaje").textContent = texto;

    await esperar(1200);

    elemento.classList.remove("activo");
    elemento.classList.add("completado");
    elemento.querySelector("small").textContent = "Completado";
}

// Enviar notificación
async function enviarNotificacion() {

    const canal = document.getElementById("canal").value;
    const boton = document.getElementById("btnEnviar");
    const mensaje = document.getElementById("mensaje");
    const detalle = document.getElementById("detalle");

    boton.disabled = true;
    boton.textContent = "Procesando...";

    detalle.innerHTML = "";
    reiniciarComponentes();

    try {

        mensaje.textContent = "Preparando notificación...";

        // Conectar con el controlador Java
        const respuesta = await fetch(
            "/bridge/notificar?canal=" + encodeURIComponent(canal),
            {
                method: "POST"
            }
        );

        if (!respuesta.ok) {
            throw new Error("No se pudo conectar con el servidor.");
        }

        const datos = await respuesta.json();

        if (datos.estado !== "Notificación enviada") {
            throw new Error(datos.mensaje || "No se pudo enviar la notificación.");
        }

        // Primera etapa
        await activarComponente(
            "notificacion",
            "La notificación de pedido ha sido creada."
        );

        // Segunda etapa
        await activarComponente(
            "canalVisual",
            "Bridge selecciona el canal: " +
            (canal === "email" ? "Correo electrónico" : "SMS")
        );

        // Tercera etapa
        await activarComponente(
            "envio",
            "El canal procesa la notificación."
        );

        // Mostrar resultado
        mensaje.textContent = datos.mensaje;

        detalle.innerHTML = `
            <strong>Patrón:</strong> ${datos.patron}<br>
            <strong>Tipo:</strong> ${datos.tipo}<br>
            <strong>Canal:</strong> ${datos.canal.toUpperCase()}<br>
            <strong>Estado:</strong> ${datos.estado}<br>
            <strong>Mensaje:</strong> ${datos.mensaje}
        `;

    } catch (error) {

        mensaje.textContent = "Error: " + error.message;
        detalle.textContent = "No fue posible completar la demostración.";

    } finally {

        boton.disabled = false;
        boton.textContent = "Enviar notificación";
    }
}
