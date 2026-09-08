package com.marketplace.payment;

public class PagoNequiFactory extends PagoFactory {

    @Override
    public Pago crearPago() {
        return new PagoNequi();
    }
}