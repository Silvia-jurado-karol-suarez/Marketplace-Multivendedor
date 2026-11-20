
package com.marketplace.strategy;

public class ResultadoDescuento {

    private double subtotal;
    private double descuento;
    private double total;
    private String estrategia;

    public ResultadoDescuento(double subtotal, double descuento,
                              double total, String estrategia) {
        this.subtotal = subtotal;
        this.descuento = descuento;
        this.total = total;
        this.estrategia = estrategia;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public double getDescuento() {
        return descuento;
    }

    public double getTotal() {
        return total;
    }

    public String getEstrategia() {
        return estrategia;
    }
}
