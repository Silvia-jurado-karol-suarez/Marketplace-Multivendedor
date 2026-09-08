package com.marketplace.payment;

public abstract class PagoFactory {

    public abstract Pago crearPago();

    public void procesar(double monto) {

        Pago pago = crearPago();

        System.out.println("Método seleccionado: "
                + pago.getMetodo());

        pago.procesarPago(monto);
    }
}