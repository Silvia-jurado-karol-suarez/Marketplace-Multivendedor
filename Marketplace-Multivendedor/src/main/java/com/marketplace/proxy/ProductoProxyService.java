package com.marketplace.proxy;

import org.springframework.stereotype.Service;

import com.marketplace.model.Producto;
import com.marketplace.repository.ProductoRepository;

@Service
public class ProductoProxyService implements ProductoProxy {

    private final ProductoRepository productoRepository;

    public ProductoProxyService(
            ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @Override
    public Producto consultarProducto(String id) {

        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException(
                "Debes ingresar el ID del producto."
            );
        }

        return productoRepository.findById(id)
            .orElseThrow(() ->
                new RuntimeException(
                    "No existe un producto con el ID: " + id
                )
            );
    }
}