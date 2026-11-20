
package com.marketplace.strategy;

public class DescuentoPorcentaje implements EstrategiaDescuento {

    private final double porcentaje;

    public DescuentoPorcentaje(double porcentaje) {
        if (porcentaje < 0 || porcentaje > 100) {
            throw new IllegalArgumentException(
                    "El porcentaje debe estar entre 0 y 100."
            );
        }

        this.porcentaje = porcentaje;
    }

    @Override
    public double calcularDescuento(double subtotal) {

        if (subtotal < 0) {
            throw new IllegalArgumentException(
                    "El subtotal no puede ser negativo."
            );
        }

        return subtotal * porcentaje / 100;
    }
}
