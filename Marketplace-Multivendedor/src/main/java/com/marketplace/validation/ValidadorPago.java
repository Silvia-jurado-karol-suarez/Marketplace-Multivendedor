package com.marketplace.validation;

import org.springframework.stereotype.Component;

@Component
public class ValidadorPago extends ValidadorBase {

    @Override
    public ResultadoValidacion validar(
            SolicitudValidacionCompra solicitud) {

        String metodo = solicitud.getMetodoPago();

        if (metodo == null ||
                !(metodo.equalsIgnoreCase("tarjeta")
                || metodo.equalsIgnoreCase("pse")
                || metodo.equalsIgnoreCase("nequi"))) {

            return ResultadoValidacion.error(
                "Selecciona un método de pago válido."
            );
        }

        return pasarAlSiguiente(solicitud);
    }
}