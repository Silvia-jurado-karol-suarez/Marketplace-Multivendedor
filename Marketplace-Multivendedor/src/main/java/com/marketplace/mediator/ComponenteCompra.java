
package com.marketplace.mediator;

public abstract class ComponenteCompra {

    protected MediadorCompra mediador;

    public ComponenteCompra(MediadorCompra mediador) {
        this.mediador = mediador;
    }

    public void enviarEvento(String evento) {
        if (mediador != null) {
            mediador.notificar(this, evento);
        }
    }
}
