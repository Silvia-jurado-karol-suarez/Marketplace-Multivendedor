package com.marketplace.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.marketplace.model.Producto;
import com.marketplace.proxy.ProductoProxy;

@RestController
@RequestMapping("/api/proxy/productos")
@CrossOrigin
public class ProductoProxyController {

    private final ProductoProxy productoProxy;

    public ProductoProxyController(ProductoProxy productoProxy) {
        this.productoProxy = productoProxy;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> consultarProducto(
            @PathVariable String id) {

        try {
            Producto producto =
                productoProxy.consultarProducto(id);

            return ResponseEntity.ok(producto);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                .body(Map.of("error", e.getMessage()));

        } catch (RuntimeException e) {
            return ResponseEntity.status(404)
                .body(Map.of("error", e.getMessage()));
        }
    }
}