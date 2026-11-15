
package com.marketplace.mediator;

public interface MediadorCompra {

    void notificar(
        ComponenteCompra componente,
        String evento
    );

}
