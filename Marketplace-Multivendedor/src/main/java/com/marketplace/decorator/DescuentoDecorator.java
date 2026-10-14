package com.marketplace.decorator;
import com.marketplace.decorator.PrecioComponent;
import com.marketplace.decorator.PrecioBase;
import com.marketplace.decorator.DescuentoDecorator;
public class DescuentoDecorator extends PrecioDecorator {

    private final double porcentaje;

    public DescuentoDecorator(
            PrecioComponent componente,
            double porcentaje) {

        super(componente);
        this.porcentaje = porcentaje;
    }

    @Override
    public double calcularPrecio() {

        double precioActual =
                componente.calcularPrecio();

        double descuento =
                precioActual * porcentaje / 100;

        return precioActual - descuento;
    }

    public double calcularDescuento() {

        double precioOriginal =
                componente.calcularPrecio();

        return precioOriginal * porcentaje / 100;
    }
}