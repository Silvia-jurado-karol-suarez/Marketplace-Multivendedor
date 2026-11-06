package com.marketplace.validation;

import org.springframework.stereotype.Component;

@Component
public class ValidadorEnvio extends ValidadorBase {

    @Override
    public ResultadoValidacion validar(
            SolicitudValidacionCompra solicitud) {

        String envio = solicitud.getTipoEnvio();

        if (envio == null ||
                !(envio.equalsIgnoreCase("nacional")
                || envio.equalsIgnoreCase("internacional"))) {

            return ResultadoValidacion.error(
                "Selecciona un tipo de envío válido."
            );
        }

        return pasarAlSiguiente(solicitud);
    }
}