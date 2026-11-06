package com.marketplace.validation;

import org.springframework.stereotype.Component;

@Component
public class ValidadorStock extends ValidadorBase {

    @Override
    public ResultadoValidacion validar(
            SolicitudValidacionCompra solicitud) {

        for (ItemCompra item : solicitud.getProductos()) {

            int stockDisponible =
                item.getProducto().getStock();

            if (stockDisponible < item.getCantidad()) {

                return ResultadoValidacion.error(
                    "No hay existencias suficientes para uno de los productos."
                );
            }
        }

        return pasarAlSiguiente(solicitud);
    }}