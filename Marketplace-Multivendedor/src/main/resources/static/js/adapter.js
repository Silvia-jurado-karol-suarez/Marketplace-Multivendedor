
function esperar(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}

// Reiniciar las tarjetas
function reiniciarFlujo() {
    const componentes = [
        "marketplace",
        "adaptador",
        "pasarela"
    ];

    componentes.forEach(id => {
        const elemento = document.getElementById(id);

        elemento.classList.remove("activo", "completado");
        elemento.querySelector("small").textContent = "Pendiente";
    });
}

// Activar cada etapa del proceso
async function animarComponente(id, mensaje) {
    const elemento = document.getElementById(id);

    elemento.classList.add("activo");
    elemento.querySelector("small").textContent = "Procesando";

    document.getElementById("mensaje").textContent = mensaje;

    await esperar(1200);

    elemento.classList.remove("activo");
    elemento.classList.add("completado");

    elemento.querySelector("small").textContent = "Completado";
}

// Procesar el pago mediante Adapter
async function procesarPago() {

    const monto = Number(document.getElementById("monto").value);
    const boton = document.getElementById("btnProcesar");
    const mensaje = document.getElementById("mensaje");
    const detalle = document.getElementById("detalle");

    if (!Number.isFinite(monto) || monto <= 0) {
        mensaje.textContent = "Ingresa un monto válido.";
        return;
    }

    boton.disabled = true;
    boton.textContent = "Procesando...";

    detalle.innerHTML = "";
    reiniciarFlujo();

    try {

        mensaje.textContent = "Conectando con el Marketplace...";

        // Enviar la solicitud al controlador Java
        const respuesta = await fetch(
            "/adapter/pagar?monto=" + encodeURIComponent(monto),
            {
                method: "POST"
            }
        );

        if (!respuesta.ok) {
            throw new Error("No se pudo conectar con el controlador.");
        }

        const datos = await respuesta.json();

        if (datos.estado !== "Procesamiento exitoso") {
            throw new Error(datos.mensaje || "No se pudo procesar la solicitud.");
        }

        // Animar las etapas
        await animarComponente(
            "marketplace",
            "El Marketplace solicita procesar el pago."
        );

        await animarComponente(
            "adaptador",
            "Adapter transforma la solicitud a la interfaz externa."
        );

        await animarComponente(
            "pasarela",
            "La pasarela externa recibe la operación simulada."
        );

        // Mostrar resultado
        mensaje.textContent = datos.mensaje;

        detalle.innerHTML = `
            <strong>Patrón:</strong> ${datos.patron}<br>
            <strong>Estado:</strong> ${datos.estado}<br>
            <strong>Monto:</strong> $${Number(datos.monto).toLocaleString("es-CO")} COP<br>
            <strong>Adaptador utilizado:</strong> ${datos.adaptador}<br>
            <strong>Resultado:</strong> Operación simulada correctamente.
        `;

    } catch (error) {

        mensaje.textContent = "Error: " + error.message;
        detalle.textContent = "No fue posible completar la demostración.";

    } finally {

        boton.disabled = false;
        boton.textContent = "Procesar pago";
    }
}
