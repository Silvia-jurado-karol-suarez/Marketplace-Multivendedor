package com.marketplace.config;

public enum MarketplaceConfig {

    INSTANCE;

    private final double commissionRate = 0.10;
    private final String defaultCurrency = "COP";
    private final int minimumStock = 5;

    public double getCommissionRate() {
        return commissionRate;
    }

    public String getDefaultCurrency() {
        return defaultCurrency;
    }

    public int getMinimumStock() {
        return minimumStock;
    }
}