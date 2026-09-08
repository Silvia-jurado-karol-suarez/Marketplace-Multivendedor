package com.marketplace.payment;

public class PagoPSEFactory extends PagoFactory {

    @Override
    public Pago crearPago() {
        return new PagoPSE();
    }
}