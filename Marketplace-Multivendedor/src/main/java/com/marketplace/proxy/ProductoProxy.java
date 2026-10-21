package com.marketplace.proxy;

import com.marketplace.model.Producto;

public interface ProductoProxy {

    Producto consultarProducto(String id);
}