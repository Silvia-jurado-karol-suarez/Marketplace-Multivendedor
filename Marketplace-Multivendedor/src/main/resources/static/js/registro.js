
const formularioRegistro = document.getElementById("formRegistro");
const mensajeRegistro = document.getElementById("mensaje");
const botonRegistro = document.getElementById("btnRegistro");
const campoFechaNacimiento = document.getElementById("fechaNacimiento");

document.getElementById("anioActual").textContent =
    new Date().getFullYear();

// Evitar fechas de nacimiento futuras.
campoFechaNacimiento.max = new Date().toISOString().split("T")[0];

function mostrarMensajeRegistro(texto, tipo) {
    mensajeRegistro.textContent = texto;
    mensajeRegistro.className =
        `auth-message auth-field-full auth-message-${tipo}`;
}

formularioRegistro.addEventListener("submit", async function (event) {
    event.preventDefault();

    if (!formularioRegistro.reportValidity()) {
        return;
    }

    const datos = {
        nombre: document.getElementById("nombre").value.trim(),
        apellido: document.getElementById("apellido").value.trim(),
        correo: document.getElementById("correo").value.trim(),
        celular: document.getElementById("celular").value.trim(),
        password: document.getElementById("password").value,
        fechaNacimiento: campoFechaNacimiento.value
    };

    if (new Date(datos.fechaNacimiento + "T00:00:00") > new Date()) {
        mostrarMensajeRegistro(
            "La fecha de nacimiento no puede ser futura.",
            "error"
        );
        return;
    }

    botonRegistro.disabled = true;
    botonRegistro.textContent = "Creando cuenta...";

    try {
        const respuesta = await fetch("/auth/registro", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Accept": "text/plain, application/json"
            },
            body: JSON.stringify(datos)
        });

        const resultado = await respuesta.text();

        if (respuesta.ok) {
            mostrarMensajeRegistro(
                resultado || "¡Tu cuenta se creó correctamente!",
                "success"
            );

            formularioRegistro.reset();
            campoFechaNacimiento.max =
                new Date().toISOString().split("T")[0];
        } else {
            mostrarMensajeRegistro(
                resultado || "No fue posible completar el registro.",
                "error"
            );
        }
    } catch (error) {
        mostrarMensajeRegistro(
            "No fue posible conectar con el servidor. Verifica que la aplicación esté funcionando.",
            "error"
        );
    } finally {
        botonRegistro.disabled = false;
        botonRegistro.innerHTML =
            'Crear mi cuenta <span aria-hidden="true">→</span>';
    }
});
