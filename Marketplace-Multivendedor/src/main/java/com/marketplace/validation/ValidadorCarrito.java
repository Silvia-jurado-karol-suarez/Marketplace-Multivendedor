package com.marketplace.validation;

import org.springframework.stereotype.Component;

@Component
public class ValidadorCarrito extends ValidadorBase {

    @Override
    public ResultadoValidacion validar(
            SolicitudValidacionCompra solicitud) {

        if (solicitud == null
                || solicitud.getProductos() == null
                || solicitud.getProductos().isEmpty()) {

            return ResultadoValidacion.error(
                "El carrito está vacío."
            );
        }

        for (ItemCompra item : solicitud.getProductos()) {

            if (item == null
                    || item.getProducto() == null
                    || item.getProducto().getStock() == null
                    || item.getCantidad() <= 0) {

                return ResultadoValidacion.error(
                    "Los datos del producto o la cantidad son inválidos."
                );
            }
        }

        return pasarAlSiguiente(solicitud);
    }
}