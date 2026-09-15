package com.marketplace.controller;

import org.springframework.web.bind.annotation.*;

import com.marketplace.service.EnvioService;
import com.marketplace.shipping.EnvioFactory;
import com.marketplace.shipping.EnvioNacionalFactory;
import com.marketplace.shipping.EnvioInternacionalFactory;

@RestController
@RequestMapping("/envios")
public class EnvioController {

    private final EnvioService envioService;

    public EnvioController(EnvioService envioService) {
        this.envioService = envioService;
    }

    @GetMapping("/{tipo}/{peso}/{pedidoId}")
    public String procesarEnvio(
            @PathVariable String tipo,
            @PathVariable double peso,
            @PathVariable String pedidoId) {

        EnvioFactory factory;

        if (tipo.equalsIgnoreCase("nacional")) {
            factory = new EnvioNacionalFactory();

        } else if (tipo.equalsIgnoreCase("internacional")) {
            factory = new EnvioInternacionalFactory();

        } else {
            return "Tipo de envío no disponible";
        }

        return envioService.procesarEnvio(
                factory,
                peso,
                pedidoId
        );
    }
}