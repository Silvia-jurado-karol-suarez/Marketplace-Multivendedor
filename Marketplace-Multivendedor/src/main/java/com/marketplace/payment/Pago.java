package com.marketplace.payment;

public interface Pago {

    void procesarPago(double monto);

    String getMetodo();
}