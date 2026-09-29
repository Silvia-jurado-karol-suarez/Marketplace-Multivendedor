package com.marketplace.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.marketplace.model.Producto;
import com.marketplace.service.ProductoService;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    // Listar todos los productos
    @GetMapping
    public List<Producto> listarProductos() {
        return productoService.listarProductos();
    }

    // Buscar producto por ID
    @GetMapping("/{id}")
    public ResponseEntity<Producto> buscarProducto(
            @PathVariable String id) {

        try {

            return ResponseEntity.ok(
                    productoService.buscarProducto(id)
            );

        } catch (RuntimeException e) {

            return ResponseEntity.notFound().build();
        }
    }

    // Crear producto
    @PostMapping
    public ResponseEntity<Producto> crearProducto(
            @RequestBody Producto producto) {

        Producto nuevoProducto =
                productoService.crearProducto(producto);

        return ResponseEntity.ok(nuevoProducto);
    }

    // Actualizar producto
    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizarProducto(
            @PathVariable String id,
            @RequestBody Producto producto) {

        try {

            Producto actualizado =
                    productoService.actualizarProducto(
                            id,
                            producto
                    );

            return ResponseEntity.ok(actualizado);

        } catch (RuntimeException e) {

            return ResponseEntity.notFound().build();
        }
    }

    // Eliminar producto
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(
            @PathVariable String id) {

        try {

            productoService.eliminarProducto(id);

            return ResponseEntity.noContent().build();

        } catch (RuntimeException e) {

            return ResponseEntity.notFound().build();
        }
    }
}