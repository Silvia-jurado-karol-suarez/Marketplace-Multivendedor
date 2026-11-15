
package com.marketplace.mediator;

public class PedidoComponente extends ComponenteCompra {

    public PedidoComponente(MediadorCompra mediador) {
        super(mediador);
    }

    public void crearPedido() {
        System.out.println("Pedido: pedido creado.");

        enviarEvento("PEDIDO_CREADO");
    }
}
