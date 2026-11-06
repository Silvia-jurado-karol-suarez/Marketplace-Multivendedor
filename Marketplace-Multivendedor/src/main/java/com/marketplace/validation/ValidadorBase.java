package com.marketplace.validation;

public abstract class ValidadorBase
        implements ValidadorCompra {

    protected ValidadorCompra siguiente;

    @Override
    public void setSiguiente(ValidadorCompra siguiente) {
        this.siguiente = siguiente;
    }

    protected ResultadoValidacion pasarAlSiguiente(
            SolicitudValidacionCompra solicitud) {

        if (siguiente != null) {
            return siguiente.validar(solicitud);
        }

        return ResultadoValidacion.exitoso();
    }
}