package com.marketplace.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.marketplace.adapter.PasarelaExterna;
import com.marketplace.adapter.PasarelaExternaAdapter;
import com.marketplace.adapter.ProcesadorPago;

@RestController
@RequestMapping("/adapter")
public class AdapterController {

    @GetMapping("/pagar")
    public String pagar(@RequestParam double monto) {

        PasarelaExterna pasarela =
                new PasarelaExterna();

        ProcesadorPago procesador =
                new PasarelaExternaAdapter(pasarela);

        procesador.pagar(monto);

        return """
                <strong>PATRÓN ADAPTER</strong>
                <br><br>
                Pasarela externa adaptada correctamente.
                <br>
                Monto: $""" + String.format("%,.0f", monto);
    }
}