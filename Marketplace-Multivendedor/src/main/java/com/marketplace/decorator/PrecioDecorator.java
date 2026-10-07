package com.marketplace.decorator;

public abstract class PrecioDecorator implements PrecioComponent {

    protected final PrecioComponent componente;

    public PrecioDecorator(PrecioComponent componente) {
        this.componente = componente;
    }

    @Override
    public double calcularPrecio() {
        return componente.calcularPrecio();
    }
}