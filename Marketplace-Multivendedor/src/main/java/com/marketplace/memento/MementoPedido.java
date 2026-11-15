
package com.marketplace.memento;

import java.util.ArrayList;
import java.util.List;

import com.marketplace.model.Pedido;
import com.marketplace.model.Producto;

public class MementoPedido {

    private final String id;
    private final List<Producto> productos;
    private final double total;
    private final String estado;

    public MementoPedido(Pedido pedido) {
        this.id = pedido.getId();

        this.productos = pedido.getProductos() == null
                ? new ArrayList<>()
                : new ArrayList<>(pedido.getProductos());

        this.total = pedido.getTotal();
        this.estado = pedido.getEstado();
    }

    String getId() {
        return id;
    }

    List<Producto> getProductos() {
        return new ArrayList<>(productos);
    }

    double getTotal() {
        return total;
    }

    String getEstado() {
        return estado;
    }
}
