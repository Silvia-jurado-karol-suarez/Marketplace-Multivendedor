package com.marketplace.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.marketplace.model.Producto;
import com.marketplace.repository.ProductoRepository;

@RestController
@RequestMapping("/prototype")
@CrossOrigin
public class PrototypeController {

    private final ProductoRepository productoRepository;

    public PrototypeController(
            ProductoRepository productoRepository) {

        this.productoRepository =
                productoRepository;
    }

    @PostMapping("/duplicar/{id}")
    public ResponseEntity<?> duplicarProducto(
            @PathVariable String id) {

        Producto original =
                productoRepository
                        .findById(id)
                        .orElse(null);

        if (original == null) {

            return ResponseEntity
                    .notFound()
                    .build();
        }

        Producto copia =
                original.clone();

        copia.setId(null);

        copia.setNombre(
                original.getNombre()
                        + " - Copia"
        );

        Producto guardado =
                productoRepository.save(copia);

        return ResponseEntity.ok(guardado);
    }
}