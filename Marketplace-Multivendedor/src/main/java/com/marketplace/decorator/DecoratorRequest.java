package com.marketplace.decorator;

public class DecoratorRequest {

    private double subtotal;
    private double porcentajeDescuento;

    public DecoratorRequest() {
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getPorcentajeDescuento() {
        return porcentajeDescuento;
    }

    public void setPorcentajeDescuento(
            double porcentajeDescuento) {

        this.porcentajeDescuento =
                porcentajeDescuento;
    }
}