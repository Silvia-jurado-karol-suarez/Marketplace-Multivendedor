
package com.marketplace.strategy;

public class DescuentoFijo implements EstrategiaDescuento {

    private final double valorDescuento;

    public DescuentoFijo(double valorDescuento) {
        if (valorDescuento < 0) {
            throw new IllegalArgumentException(
                    "El descuento no puede ser negativo."
            );
        }

        this.valorDescuento = valorDescuento;
    }

    @Override
    public double calcularDescuento(double subtotal) {

        if (subtotal < 0) {
            throw new IllegalArgumentException(
                    "El subtotal no puede ser negativo."
            );
        }

        return Math.min(valorDescuento, subtotal);
    }
}
