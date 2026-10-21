
package com.marketplace.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.marketplace.model.Producto;

@Service
@Primary
public class ProductoServiceProxy implements ProductoOperations {

    private static final Logger log =
            LoggerFactory.getLogger(ProductoServiceProxy.class);

    private final ProductoService productoService;

    public ProductoServiceProxy(ProductoService productoService) {
        this.productoService = productoService;
    }

    @Override
    public List<Producto> listarProductos() {
        log.info("Proxy: listando productos");
        return productoService.listarProductos();
    }

    @Override
    public Producto buscarProducto(String id) {
        log.info("Proxy: buscando producto con ID {}", id);
        return productoService.buscarProducto(id);
    }

    @Override
    public Producto crearProducto(Producto producto) {
        log.info("Proxy: creando producto");
        return productoService.crearProducto(producto);
    }

    @Override
    public Producto actualizarProducto(
            String id, Producto producto) {
        log.info("Proxy: actualizando producto con ID {}", id);
        return productoService.actualizarProducto(id, producto);
    }

    @Override
    public void eliminarProducto(String id) {
        log.info("Proxy: eliminando producto con ID {}", id);
        productoService.eliminarProducto(id);
    }
}
