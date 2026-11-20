package com.marketplace.command;

import com.marketplace.model.Producto;
import com.marketplace.repository.ProductoRepository;

public class ActualizarStockCommand implements Command {

    private final ProductoRepository productoRepository;
    private final String productoId;
    private final int nuevoStock;

    private int stockAnterior;
    private boolean ejecutado = false;

    public ActualizarStockCommand(
            ProductoRepository productoRepository,
            String productoId,
            int nuevoStock) {

        this.productoRepository = productoRepository;
        this.productoId = productoId;
        this.nuevoStock = nuevoStock;
    }

    @Override
    public void ejecutar() {

        if (nuevoStock < 0) {
            throw new IllegalArgumentException(
                    "El stock no puede ser negativo");
        }

        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() ->
                        new RuntimeException("Producto no encontrado"));

        stockAnterior = producto.getStock();

        producto.setStock(nuevoStock);
        productoRepository.save(producto);

        ejecutado = true;
    }

    @Override
    public void deshacer() {

        if (!ejecutado) {
            throw new IllegalStateException(
                    "Primero debes ejecutar el comando");
        }

        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() ->
                        new RuntimeException("Producto no encontrado"));

        producto.setStock(stockAnterior);
        productoRepository.save(producto);

        ejecutado = false;
    }
}