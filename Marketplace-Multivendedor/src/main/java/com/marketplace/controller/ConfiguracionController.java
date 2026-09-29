package com.marketplace.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marketplace.config.MarketplaceConfig;

@RestController
@RequestMapping("/configuracion")
public class ConfiguracionController {

    @GetMapping
    public String mostrarConfiguracion() {

        MarketplaceConfig config = MarketplaceConfig.INSTANCE;

        return "<h3>Configuración del Marketplace</h3>"
                + "<p>Moneda: " + config.getDefaultCurrency() + "</p>"
                + "<p>Comisión: " + (config.getCommissionRate() * 100) + "%</p>"
                + "<p>Stock mínimo: " + config.getMinimumStock() + "</p>";
    }
}