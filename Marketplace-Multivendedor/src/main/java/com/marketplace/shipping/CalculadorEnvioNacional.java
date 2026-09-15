package com.marketplace.shipping;

public class CalculadorEnvioNacional implements CalculadorEnvio {

    @Override
    public double calcularCosto(double peso) {
        return 10000 + (peso * 2000);
    }
}