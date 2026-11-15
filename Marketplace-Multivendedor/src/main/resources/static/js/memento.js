
async function ejecutarMemento() {

    const boton = document.getElementById("btnEjecutar");
    const mensaje = document.getElementById("mensaje");

    boton.disabled = true;
    boton.textContent = "Ejecutando...";
    
    mensaje.textContent = "Conectando con el patrón Memento...";

    try {

        const respuesta = await fetch("/api/memento/ejecutar", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            }
        });

        if (!respuesta.ok) {
            throw new Error("Error al ejecutar la demostración.");
        }

        const datos = await respuesta.json();

        mostrarEstado("original", datos.original);
        mostrarEstado("modificado", datos.modificado);
        mostrarEstado("restaurado", datos.restaurado);

        mensaje.innerHTML = `
            <p>✓ Se creó el pedido original.</p>
            <p>✓ Se guardó una copia mediante Memento.</p>
            <p>✓ Se modificó el estado del pedido.</p>
            <p>✓ Se recuperó el estado anterior.</p>
            <br>
            <strong>${datos.mensaje}</strong>
        `;

    } catch (error) {

        mensaje.textContent =
            "No fue posible ejecutar la prueba: " + error.message;

    } finally {

        boton.disabled = false;
        boton.textContent = "Ejecutar demostración";
    }
}

function mostrarEstado(elemento, pedido) {

    document.getElementById(elemento).innerHTML = `
        <p><strong>ID:</strong> ${pedido.id}</p>
        <p><strong>Estado:</strong> ${pedido.estado}</p>
        <p><strong>Total:</strong> $${Number(pedido.total).toLocaleString("es-CO")}</p>
        <p><strong>Productos:</strong> ${pedido.cantidadProductos}</p>
    `;
}
