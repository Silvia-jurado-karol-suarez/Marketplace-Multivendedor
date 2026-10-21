
const formularioLogin = document.getElementById("formLogin");
const mensajeLogin = document.getElementById("mensaje");
const botonLogin = document.getElementById("btnLogin");

document.getElementById("anioActual").textContent =
    new Date().getFullYear();

function mostrarMensajeLogin(texto, tipo) {
    mensajeLogin.textContent = texto;
    mensajeLogin.className =
        `auth-message auth-field-full auth-message-${tipo}`;
}

formularioLogin.addEventListener("submit", async function (event) {
    event.preventDefault();

    if (!formularioLogin.reportValidity()) {
        return;
    }

    const datos = {
        correo: document.getElementById("correo").value.trim(),
        password: document.getElementById("password").value
    };

    botonLogin.disabled = true;
    botonLogin.textContent = "Verificando...";

    try {
        const respuesta = await fetch("/auth/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Accept": "text/plain, application/json"
            },
            body: JSON.stringify(datos)
        });

        const resultado = await respuesta.text();

        if (respuesta.ok) {
            window.location.href = "/marketplace.html";
        } else {
            mostrarMensajeLogin(
                resultado || "Correo o contraseña incorrectos.",
                "error"
            );
        }
    } catch (error) {
        mostrarMensajeLogin(
            "No fue posible conectar con el servidor. Inténtalo nuevamente.",
            "error"
        );
    } finally {
        botonLogin.disabled = false;
        botonLogin.innerHTML =
            'Iniciar sesión <span aria-hidden="true">→</span>';
    }
});
