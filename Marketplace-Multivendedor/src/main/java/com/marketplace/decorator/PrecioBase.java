package com.marketplace.decorator;

public class PrecioBase implements PrecioComponent {

    private final double precio;

    public PrecioBase(double precio) {
        this.precio = precio;
    }

    @Override
    public double calcularPrecio() {
        return precio;
    }
}