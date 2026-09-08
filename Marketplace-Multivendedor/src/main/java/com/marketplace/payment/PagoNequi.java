package com.marketplace.payment;

public class PagoNequi implements Pago {

    @Override
    public void procesarPago(double monto) {
        System.out.println("Procesando pago mediante Nequi por: $" + monto);
    }

    @Override
    public String getMetodo() {
        return "Nequi";
    }
}