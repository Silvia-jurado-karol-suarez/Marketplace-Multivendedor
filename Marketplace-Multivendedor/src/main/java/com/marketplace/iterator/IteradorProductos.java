

package com.marketplace.iterator;

import com.marketplace.model.Producto;

public interface IteradorProductos {

    boolean haySiguiente();

    Producto siguiente();
}
