package com.marketplace.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.marketplace.decorator.DecoratorRequest;
import com.marketplace.decorator.DescuentoDecorator;
import com.marketplace.decorator.PrecioBase;
import com.marketplace.decorator.PrecioComponent;

import java.util.Map;

@RestController
@RequestMapping("/decorator")
@CrossOrigin
public class DecoratorController {

    @PostMapping("/calcular")
    public ResponseEntity<?> calcular(
            @RequestBody DecoratorRequest request) {

        if (request.getSubtotal() < 0) {

            return ResponseEntity
                    .badRequest()
                    .body(Map.of(
                            "error",
                            "El subtotal no puede ser negativo"
                    ));
        }

        if (request.getPorcentajeDescuento() < 0 ||
            request.getPorcentajeDescuento() > 100) {

            return ResponseEntity
                    .badRequest()
                    .body(Map.of(
                            "error",
                            "El descuento debe estar entre 0 y 100"
                    ));
        }

        PrecioComponent precioBase =
                new PrecioBase(request.getSubtotal());

        DescuentoDecorator descuento =
                new DescuentoDecorator(
                        precioBase,
                        request.getPorcentajeDescuento()
                );

        double subtotal =
                precioBase.calcularPrecio();

        double total =
                descuento.calcularPrecio();

        double valorDescuento =
                descuento.calcularDescuento();

        return ResponseEntity.ok(
                Map.of(
                        "subtotal", subtotal,
                        "descuento", valorDescuento,
                        "total", total
                )
        );
    }
}