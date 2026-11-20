
package com.marketplace.strategy;

public class DescuentoContexto {

    private EstrategiaDescuento estrategia;

    public DescuentoContexto(EstrategiaDescuento estrategia) {
        this.estrategia = estrategia;
    }

    public void setEstrategia(EstrategiaDescuento estrategia) {
        this.estrategia = estrategia;
    }

    public double calcular(double subtotal) {
        return estrategia.calcularDescuento(subtotal);
    }

    public double calcularTotal(double subtotal) {
        double descuento = calcular(subtotal);
        return subtotal - descuento;
    }
}
