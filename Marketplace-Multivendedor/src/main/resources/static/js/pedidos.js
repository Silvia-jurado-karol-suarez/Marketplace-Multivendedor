// Pedidos: Builder, Decorator y Facade

async function crearPedido() {
    if (carrito.length === 0) {
        alert("El carrito está vacío.");
        return;
    }

    const productosIds = carrito.map(producto => producto.id);
    const subtotal = calcularSubtotal();

    let totalPedido = subtotal;

    if (totalDecorado !== null && !isNaN(totalDecorado)) {
        totalPedido = Number(totalDecorado);
    }

    try {
        const respuesta = await fetch("/pedidos", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                productosIds: productosIds,
                total: totalPedido
            })
        });

        if (!respuesta.ok) {
            const mensaje = await respuesta.text();
            throw new Error(mensaje || "No se pudo crear el pedido.");
        }

        const pedido = await respuesta.json();

        const resultado = document.getElementById("resultadoPedido");

        if (resultado) {
            resultado.innerHTML = `
                <div class="resultado">
                    <h3>✅ Pedido creado correctamente</h3>
                    <p><strong>ID del pedido:</strong> ${pedido.id}</p>
                    <p><strong>Total:</strong>
                        $${Number(pedido.total).toLocaleString("es-CO")}
                    </p>
                    <p><strong>Estado:</strong> ${pedido.estado}</p>
                </div>
            `;
        }

        localStorage.setItem("pedidoActual", JSON.stringify(pedido));

        const campoPedido = document.getElementById("pedidoEnvio");
        if (campoPedido) {
            campoPedido.value = pedido.id;
        }
    } catch (error) {
        console.error("Error creando pedido:", error);

        const resultado = document.getElementById("resultadoPedido");
        if (resultado) {
            resultado.innerHTML = `
                <div class="resultado">
                    <p>❌ Error al crear el pedido.</p>
                    <p>${error.message}</p>
                </div>
            `;
        }
    }
}

async function finalizarCompraFacade() {
    const resultadoPedido =
        document.getElementById("resultadoPedido");
    const resultadoPago =
        document.getElementById("resultadoPago");
    const resultadoDecorator =
        document.getElementById("resultadoDecorator");

    if (!Array.isArray(carrito) || carrito.length === 0) {
        alert("El carrito está vacío.");
        return;
    }

    const porcentajeCampo =
        document.getElementById("porcentajeDescuento");
    const metodoCampo =
        document.getElementById("metodoPago");
    const envioCampo =
        document.getElementById("tipoEnvio");

    const porcentaje = Number(
        porcentajeCampo ? porcentajeCampo.value : 0
    );
    const subtotal = calcularSubtotal();

    if (
        !Number.isFinite(porcentaje) ||
        porcentaje < 0 ||
        porcentaje > 100
    ) {
        alert("Ingresa un descuento entre 0 y 100.");
        return;
    }

    const productosIds = carrito
        .map(producto => producto.id)
        .filter(Boolean);

    if (productosIds.length !== carrito.length) {
        alert("Hay productos sin identificador.");
        return;
    }

    const boton = document.querySelector(
        'button[onclick="finalizarCompraFacade()"]'
    );

    if (boton) {
        boton.disabled = true;
        boton.textContent = "Procesando compra...";
    }

    try {
        const respuesta = await fetch("/marketplace/finalizar", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                productosIds: productosIds,
                subtotal: subtotal,
                porcentajeDescuento: porcentaje,
                metodoPago: metodoCampo
                    ? metodoCampo.value
                    : "tarjeta",
                tipoEnvio: envioCampo
                    ? envioCampo.value
                    : "nacional",
                peso: 1
            })
        });

        const datos = await respuesta.json();

        if (!respuesta.ok) {
            throw new Error(
                datos.error || "No se pudo finalizar la compra."
            );
        }

        const pedido = datos.pedido;
        const pago = datos.pago;

        totalDecorado = Number(datos.total);

        localStorage.setItem(
            "pedidoActual",
            JSON.stringify(pedido)
        );

        const campoPedidoEnvio =
            document.getElementById("pedidoEnvio");

        if (campoPedidoEnvio) {
            campoPedidoEnvio.value = pedido.id;
        }

        if (resultadoDecorator) {
            resultadoDecorator.innerHTML = `
                <div class="resultado">
                    <h3>Decorator aplicado correctamente</h3>
                    <p>Subtotal: $${formatearPrecio(datos.subtotal)}</p>
                    <p>
                        Descuento (${datos.porcentajeDescuento}%):
                        -$${formatearPrecio(datos.descuento)}
                    </p>
                    <p>
                        <strong>
                            Total final: $${formatearPrecio(datos.total)}
                        </strong>
                    </p>
                </div>
            `;
        }

        if (resultadoPedido) {
            resultadoPedido.innerHTML = `
                <div class="resultado">
                    <h3>Compra procesada con Facade</h3>
                    <p>Pedido: <strong>${pedido.id}</strong></p>
                    <p>
                        Total:
                        <strong>$${formatearPrecio(pedido.total)}</strong>
                    </p>
                    <p>Estado del pedido: ${pedido.estado}</p>
                    <p>Envío: ${datos.envio}</p>
                </div>
            `;
        }

        if (resultadoPago) {
            resultadoPago.innerHTML = `
                <div class="resultado">
                    <h3>Pago registrado</h3>
                    <p>Método: ${pago.metodo}</p>
                    <p>Monto: $${formatearPrecio(pago.monto)}</p>
                    <p>Estado: ${pago.estado}</p>
                </div>
            `;
        }
    } catch (error) {
        console.error("Error en la compra integrada:", error);

        if (resultadoPedido) {
            resultadoPedido.innerHTML = `
                <div class="resultado">
                    <p>Error: ${error.message}</p>
                </div>
            `;
        }
    } finally {
        if (boton) {
            boton.disabled = false;
            boton.textContent =
                "Finalizar compra (Facade + Decorator)";
        }
    }
}