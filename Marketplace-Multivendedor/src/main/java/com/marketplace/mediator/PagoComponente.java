
package com.marketplace.mediator;

public class PagoComponente extends ComponenteCompra {

    public PagoComponente(MediadorCompra mediador) {
        super(mediador);
    }

    public void procesarPago() {
        System.out.println("Pago: operación procesada.");

        enviarEvento("PAGO_PROCESADO");
    }
}
