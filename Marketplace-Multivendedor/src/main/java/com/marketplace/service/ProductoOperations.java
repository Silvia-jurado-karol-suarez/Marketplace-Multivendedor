
package com.marketplace.service;

import java.util.List;
import com.marketplace.model.Producto;

public interface ProductoOperations {

    List<Producto> listarProductos();

    Producto buscarProducto(String id);

    Producto crearProducto(Producto producto);

    Producto actualizarProducto(String id, Producto producto);

    void eliminarProducto(String id);
}
