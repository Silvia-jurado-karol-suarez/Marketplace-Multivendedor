package com.marketplace.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.marketplace.model.PagoRegistro;
import com.marketplace.payment.PagoFactory;
import com.marketplace.payment.PagoNequiFactory;
import com.marketplace.payment.PagoPSEFactory;
import com.marketplace.payment.PagoTarjetaFactory;
import com.marketplace.service.PagoService;

@RestController
@RequestMapping("/pagos")
@CrossOrigin
public class PagoController {

    private final PagoService pagoService;

    public PagoController(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    @PostMapping
    public ResponseEntity<?> procesarPago(
            @RequestBody PagoRequest request) {

        PagoFactory factory;

        switch (request.getMetodo().toLowerCase()) {

            case "tarjeta":
                factory = new PagoTarjetaFactory();
                break;

            case "pse":
                factory = new PagoPSEFactory();
                break;

            case "nequi":
                factory = new PagoNequiFactory();
                break;

            default:
                return ResponseEntity
                        .badRequest()
                        .body("Método de pago no disponible.");
        }

        PagoRegistro pago =
                pagoService.procesarPago(
                        factory,
                        request.getMonto(),
                        request.getPedidoId()
                );

        return ResponseEntity.ok(pago);
    }
}