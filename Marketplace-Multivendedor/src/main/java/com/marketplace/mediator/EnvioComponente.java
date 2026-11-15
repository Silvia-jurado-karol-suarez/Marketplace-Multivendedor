
package com.marketplace.mediator;

public class EnvioComponente extends ComponenteCompra {

    public EnvioComponente(MediadorCompra mediador) {
        super(mediador);
    }

    public void prepararEnvio() {
        System.out.println("Envío: preparación iniciada.");

        enviarEvento("ENVIO_PREPARADO");
    }
}
