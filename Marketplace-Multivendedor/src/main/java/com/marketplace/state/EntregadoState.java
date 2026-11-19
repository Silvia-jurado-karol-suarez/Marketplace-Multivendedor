package com.marketplace.state;

public class EntregadoState implements EstadoPedido {

    @Override
    public EstadoPedido preparar() {
        throw new IllegalStateException(
            "El pedido ya fue entregado."
        );
    }

    @Override
    public EstadoPedido enviar() {
        throw new IllegalStateException(
            "El pedido ya fue entregado."
        );
    }

    @Override
    public EstadoPedido entregar() {
        return this;
    }

    @Override
    public String obtenerNombre() {
        return "ENTREGADO";
    }
}