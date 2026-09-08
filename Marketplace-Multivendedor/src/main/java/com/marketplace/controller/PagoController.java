package com.marketplace.controller;

import org.springframework.web.bind.annotation.*;

import com.marketplace.payment.PagoFactory;
import com.marketplace.payment.PagoNequiFactory;
import com.marketplace.payment.PagoPSEFactory;
import com.marketplace.payment.PagoTarjetaFactory;

@RestController
@RequestMapping("/pagos")
public class PagoController {

    @PostMapping("/{metodo}/{monto}")
    public String procesarPago(
            @PathVariable String metodo,
            @PathVariable double monto) {

        PagoFactory factory;

        switch (metodo.toLowerCase()) {

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
                return "Método de pago no disponible";
        }

        factory.procesar(monto);

        return "Pago procesado mediante " + metodo;
    }
}
