package com.marketplace.state;

public class PendienteState implements EstadoPedido {

    @Override
    public EstadoPedido preparar() {
        return new EnPreparacionState();
    }

    @Override
    public EstadoPedido enviar() {
        throw new IllegalStateException(
            "No se puede enviar un pedido pendiente."
        );
    }

    @Override
    public EstadoPedido entregar() {
        throw new IllegalStateException(
            "No se puede entregar un pedido pendiente."
        );
    }

    @Override
    public String obtenerNombre() {
        return "PENDIENTE";
    }
}