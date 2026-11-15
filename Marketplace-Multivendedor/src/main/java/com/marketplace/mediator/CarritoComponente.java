
package com.marketplace.mediator;

public class CarritoComponente extends ComponenteCompra {

    public CarritoComponente(MediadorCompra mediador) {
        super(mediador);
    }

    public void confirmarCarrito() {
        System.out.println("Carrito: compra confirmada.");

        enviarEvento("CARRITO_CONFIRMADO");
    }
}
