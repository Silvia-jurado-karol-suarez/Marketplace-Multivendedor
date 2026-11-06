package com.marketplace.controller;

import com.marketplace.validation.CadenaValidacionCompra;
import com.marketplace.validation.ResultadoValidacion;
import com.marketplace.validation.SolicitudValidacionCompra;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/validaciones")
public class ValidacionCompraController {

    private final CadenaValidacionCompra cadena;

    public ValidacionCompraController(
            CadenaValidacionCompra cadena) {
        this.cadena = cadena;
    }

    @PostMapping("/compra")
    public ResultadoValidacion validarCompra(
            @RequestBody SolicitudValidacionCompra solicitud) {

        return cadena.validarCompra(solicitud);
    }
}