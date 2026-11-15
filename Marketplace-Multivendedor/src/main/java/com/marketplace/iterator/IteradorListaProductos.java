
package com.marketplace.iterator;

import com.marketplace.model.Producto;
import java.util.List;

public class IteradorListaProductos implements IteradorProductos {

    private final List<Producto> productos;
    private int posicion = 0;

    public IteradorListaProductos(List<Producto> productos) {
        this.productos = productos;
    }

    @Override
    public boolean haySiguiente() {
        return posicion < productos.size();
    }

    @Override
    public Producto siguiente() {
        if (haySiguiente()) {
            return productos.get(posicion++);
        }

        return null;
    }
}
