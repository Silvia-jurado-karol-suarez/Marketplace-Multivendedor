package com.marketplace.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.marketplace.model.Producto;
import com.marketplace.repository.ProductoRepository;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    public Producto buscarProducto(String id) {
        return productoRepository.findById(id)
                .orElseThrow(() ->
                    new RuntimeException("Producto no encontrado"));
    }

    public Producto crearProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    public Producto actualizarProducto(String id, Producto productoActualizado) {

        Producto producto = buscarProducto(id);

        producto.setNombre(productoActualizado.getNombre());
        producto.setDescripcion(productoActualizado.getDescripcion());
        producto.setPrecio(productoActualizado.getPrecio());
        producto.setStock(productoActualizado.getStock());
        producto.setCategoria(productoActualizado.getCategoria());
        producto.setVendedor(productoActualizado.getVendedor());

        return productoRepository.save(producto);
    }

    public void eliminarProducto(String id) {

        if (!productoRepository.existsById(id)) {
            throw new RuntimeException("Producto no encontrado");
        }

        productoRepository.deleteById(id);
    }
}