
package com.marketplace.state;

public class EnviadoState implements EstadoPedido {

    @Override
    public EstadoPedido preparar() {
        throw new IllegalStateException(
            "El pedido ya fue enviado."
        );
    }

    @Override
    public EstadoPedido enviar() {
        return this;
    }

    @Override
    public EstadoPedido entregar() {
        return new EntregadoState();
    }

    @Override
    public String obtenerNombre() {
        return "ENVIADO";
    }
}
