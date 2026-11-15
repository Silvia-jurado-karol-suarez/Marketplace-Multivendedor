package com.marketplace.iterator;

import com.marketplace.model.Producto;
import java.util.List;

public class CatalogoProductos {

    private final List<Producto> productos;

    public CatalogoProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public IteradorProductos crearIterador() {
        return new IteradorListaProductos(productos);
    }
}