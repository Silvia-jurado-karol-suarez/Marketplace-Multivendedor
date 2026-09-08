package com.marketplace.payment;

public class PagoPSE implements Pago {

    @Override
    public void procesarPago(double monto) {
        System.out.println("Procesando pago mediante PSE por: $" + monto);
    }

    @Override
    public String getMetodo() {
        return "PSE";
    }
}