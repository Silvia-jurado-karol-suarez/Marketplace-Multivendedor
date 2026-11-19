
package com.marketplace.state;

public class EnPreparacionState implements EstadoPedido {

    @Override
    public EstadoPedido preparar() {
        return this;
    }

    @Override
    public EstadoPedido enviar() {
        return new EnviadoState();
    }

    @Override
    public EstadoPedido entregar() {
        throw new IllegalStateException(
            "El pedido debe enviarse antes de entregarse."
        );
    }

    @Override
    public String obtenerNombre() {
        return "EN PREPARACIÓN";
    }
}
