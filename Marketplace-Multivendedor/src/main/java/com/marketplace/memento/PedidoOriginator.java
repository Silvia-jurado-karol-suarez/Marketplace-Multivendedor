
package com.marketplace.memento;

import com.marketplace.model.Pedido;

public class PedidoOriginator {

    private final Pedido pedido;

    public PedidoOriginator(Pedido pedido) {
        this.pedido = pedido;
    }

    // Guarda el estado actual del pedido
    public MementoPedido crearMemento() {
        return new MementoPedido(pedido);
    }

    // Recupera el estado guardado
    public void restaurarMemento(MementoPedido memento) {

        if (memento == null) {
            throw new IllegalArgumentException(
                "El estado guardado no puede ser nulo."
            );
        }

        pedido.setId(memento.getId());
        pedido.setProductos(memento.getProductos());
        pedido.setTotal(memento.getTotal());
        pedido.setEstado(memento.getEstado());
    }

    public Pedido getPedido() {
        return pedido;
    }
}
