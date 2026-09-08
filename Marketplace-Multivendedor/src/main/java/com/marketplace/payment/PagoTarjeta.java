package com.marketplace.payment;

public class PagoTarjeta implements Pago {

    @Override
    public void procesarPago(double monto) {
        System.out.println("Procesando pago con tarjeta por: $" + monto);
    }

    @Override
    public String getMetodo() {
        return "Tarjeta";
    }
}