
// Pausa entre cada evento para mostrar la animación
function esperar(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}

// Mostrar un evento en pantalla
function mostrarEvento(evento) {
    const contenedor = document.getElementById("eventos");

    const linea = document.createElement("div");
    linea.className = "evento";
    linea.textContent = evento;

    contenedor.appendChild(linea);
}

// Activar visualmente cada componente
function activarComponente(id) {
    const elemento = document.getElementById(id);

    if (!elemento) return;

    elemento.classList.add("completado");
    elemento.classList.remove("activo");

    elemento.querySelector("small").textContent = "Completado";
}

// Reiniciar los componentes
function reiniciarComponentes() {
    const componentes = ["carrito", "pedido", "pago", "envio"];

    componentes.forEach(id => {
        const elemento = document.getElementById(id);

        elemento.classList.remove("activo", "completado");
        elemento.querySelector("small").textContent = "Pendiente";
    });
}

// Ejecutar la compra
async function iniciarCompra() {
    const boton = document.getElementById("btnIniciar");
    const mensaje = document.getElementById("mensaje");
    const contenedorEventos = document.getElementById("eventos");

    boton.disabled = true;
    boton.textContent = "Procesando...";

    contenedorEventos.innerHTML = "";
    reiniciarComponentes();

    mensaje.textContent = "Iniciando proceso de compra...";

    try {
        const respuesta = await fetch("/api/mediator/iniciar", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            }
        });

        if (!respuesta.ok) {
            throw new Error("No se pudo ejecutar el proceso.");
        }

        const datos = await respuesta.json();

        // Relacionar los eventos con sus componentes
        const componentes = {
            "CARRITO_CONFIRMADO": "carrito",
            "PEDIDO_CREADO": "pedido",
            "PAGO_PROCESADO": "pago",
            "ENVIO_PREPARADO": "envio"
        };

        // Recorrer los eventos recibidos desde Java
        for (const evento of datos.eventos) {

            mostrarEvento(evento);

            if (componentes[evento]) {
                const id = componentes[evento];

                const elemento = document.getElementById(id);

                elemento.classList.add("activo");
                elemento.querySelector("small").textContent = "Procesando";

                mensaje.textContent =
                    "El Mediator está coordinando: " + id;

                await esperar(1200);

                activarComponente(id);

            } else {
                mensaje.textContent = evento;
                await esperar(800);
            }
        }

        mensaje.textContent =
            datos.mensaje + " Estado: " + datos.estado;

    } catch (error) {
        mensaje.textContent = "Error: " + error.message;

    } finally {
        boton.disabled = false;
        boton.textContent = "Iniciar proceso de compra";
    }
}
