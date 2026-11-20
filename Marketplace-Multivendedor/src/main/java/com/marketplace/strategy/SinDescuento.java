
package com.marketplace.strategy;

public class SinDescuento implements EstrategiaDescuento {

    @Override
    public double calcularDescuento(double subtotal) {

        if (subtotal < 0) {
            throw new IllegalArgumentException(
                    "El subtotal no puede ser negativo."
            );
        }

        return 0;
    }
}
