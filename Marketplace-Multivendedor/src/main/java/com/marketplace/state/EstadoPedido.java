
package com.marketplace.state;

public interface EstadoPedido {

    EstadoPedido preparar();

    EstadoPedido enviar();

    EstadoPedido entregar();

    String obtenerNombre();
}
