
package com.marketplace.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.marketplace.service.strategy.StrategyService;
import com.marketplace.strategy.ResultadoDescuento;

@RestController
@RequestMapping("/api/strategy")
@CrossOrigin
public class StrategyController {

    private final StrategyService strategyService;

    public StrategyController(StrategyService strategyService) {
        this.strategyService = strategyService;
    }

    @GetMapping("/calcular")
    public ResponseEntity<ResultadoDescuento> calcularDescuento(
            @RequestParam String tipo,
            @RequestParam double subtotal,
            @RequestParam(defaultValue = "0") double valor) {

        ResultadoDescuento resultado =
                strategyService.calcularDescuento(
                        tipo, subtotal, valor
                );

        return ResponseEntity.ok(resultado);
    }
}
