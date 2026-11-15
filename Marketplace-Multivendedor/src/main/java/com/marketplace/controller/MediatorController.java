
package com.marketplace.controller;

import com.marketplace.mediator.MediadorMarketplace;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/mediator")
@CrossOrigin(origins = "*")
public class MediatorController {

    @PostMapping("/iniciar")
    public Map<String, Object> iniciarCompra() {

        MediadorMarketplace mediador = new MediadorMarketplace();

        // Ejecutar la coordinación entre componentes
        mediador.iniciarCompra();

        // Obtener los eventos reales del mediador
        List<String> eventos = mediador.obtenerEventos();

        Map<String, Object> respuesta = new LinkedHashMap<>();

        respuesta.put("patron", "Mediator");
        respuesta.put("estado", "Compra finalizada");
        respuesta.put("mensaje", "El mediador coordinó los componentes.");
        respuesta.put("eventos", eventos);

        return respuesta;
    }
}
