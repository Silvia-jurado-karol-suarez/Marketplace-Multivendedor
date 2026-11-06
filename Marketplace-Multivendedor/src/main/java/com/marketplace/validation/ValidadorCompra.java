package com.marketplace.validation;

public interface ValidadorCompra {

    void setSiguiente(ValidadorCompra siguiente);

    ResultadoValidacion validar(
        SolicitudValidacionCompra solicitud
    );
}