package com.marketplace.payment;

public class PagoTarjetaFactory extends PagoFactory {

    @Override
    public Pago crearPago() {
        return new PagoTarjeta();
    }
}
