package com.marketplace.service;

import org.springframework.stereotype.Service;

import com.marketplace.config.MarketplaceConfig;

@Service
public class ProductoService {

    public void mostrarConfiguracion() {

        MarketplaceConfig config = MarketplaceConfig.INSTANCE;

        System.out.println("Comisión: "
                + (config.getCommissionRate() * 100) + "%");

        System.out.println("Moneda: "
                + config.getDefaultCurrency());

        System.out.println("Stock mínimo: "
                + config.getMinimumStock());
    }
}