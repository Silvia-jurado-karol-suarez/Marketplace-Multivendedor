package com.marketplace.validation;

import org.springframework.stereotype.Service;

@Service
public class CadenaValidacionCompra {

    private final ValidadorCarrito validadorCarrito;
    private final ValidadorStock validadorStock;
    private final ValidadorPago validadorPago;
    private final ValidadorEnvio validadorEnvio;

    public CadenaValidacionCompra(
            ValidadorCarrito validadorCarrito,
            ValidadorStock validadorStock,
            ValidadorPago validadorPago,
            ValidadorEnvio validadorEnvio) {

        this.validadorCarrito = validadorCarrito;
        this.validadorStock = validadorStock;
        this.validadorPago = validadorPago;
        this.validadorEnvio = validadorEnvio;

        construirCadena();
    }

    private void construirCadena() {

        validadorCarrito.setSiguiente(validadorStock);

        validadorStock.setSiguiente(validadorPago);

        validadorPago.setSiguiente(validadorEnvio);

        validadorEnvio.setSiguiente(null);
    }

    public ResultadoValidacion validarCompra(
            SolicitudValidacionCompra solicitud) {

        return validadorCarrito.validar(solicitud);
    }
}