package com.marketplace.shipping;

public class CalculadorEnvioInternacional implements CalculadorEnvio {

    @Override
    public double calcularCosto(double peso) {
        return 50000 + (peso * 8000);
    }
}