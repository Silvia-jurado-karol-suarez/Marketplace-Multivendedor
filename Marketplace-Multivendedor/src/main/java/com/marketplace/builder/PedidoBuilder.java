package com.marketplace.builder;

import java.util.List;

import com.marketplace.model.Pedido;
import com.marketplace.model.Producto;

public class PedidoBuilder {

    private List<Producto> productos;
    private double total;
    private String estado;

    public PedidoBuilder conProductos(List<Producto> productos) {
        this.productos = productos;
        return this;
    }

    public PedidoBuilder conTotal(double total) {
        this.total = total;
        return this;
    }

    public PedidoBuilder conEstado(String estado) {
        this.estado = estado;
        return this;
    }

    public Pedido build() {

        return new Pedido(
                null,
                productos,
                total,
                estado
        );
    }
}
