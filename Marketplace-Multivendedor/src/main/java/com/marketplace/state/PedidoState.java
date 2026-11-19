
package com.marketplace.state;

import java.text.Normalizer;

public class PedidoState {

    private EstadoPedido estado;

    // Constructor: establece el estado inicial del pedido
    public PedidoState(String estadoInicial) {
        establecerEstado(estadoInicial);
    }

    // Normalizar el nombre del estado y eliminar las tildes
    private String normalizar(String nombre) {

        if (nombre == null) {
            throw new IllegalArgumentException(
                "El estado no puede ser nulo."
            );
        }

        return Normalizer.normalize(
            nombre.trim().toUpperCase(),
            Normalizer.Form.NFD
        ).replaceAll("\\p{M}", "");
    }

    // Asignar el objeto State correspondiente
    private void establecerEstado(String nombre) {

        if (nombre == null) {
            throw new IllegalArgumentException(
                "El estado no puede ser nulo."
            );
        }

        switch (normalizar(nombre)) {

            case "PENDIENTE":
                estado = new PendienteState();
                break;

            case "EN PREPARACION":
                estado = new EnPreparacionState();
                break;

            case "ENVIADO":
                estado = new EnviadoState();
                break;

            case "ENTREGADO":
                estado = new EntregadoState();
                break;

            default:
                throw new IllegalArgumentException(
                    "Estado desconocido: " + nombre
                );
        }
    }

    // Cambiar de estado respetando las transiciones permitidas
    public void cambiarEstado(String nuevoEstado) {

        String actual = normalizar(estado.obtenerNombre());
        String nuevo = normalizar(nuevoEstado);

        // Si el estado es el mismo, no realizar cambios
        if (actual.equals(nuevo)) {
            return;
        }

        switch (actual) {

            case "PENDIENTE":

                if (nuevo.equals("EN PREPARACION")) {
                    estado = estado.preparar();
                    return;
                }

                break;

            case "EN PREPARACION":

                if (nuevo.equals("ENVIADO")) {
                    estado = estado.enviar();
                    return;
                }

                break;

            case "ENVIADO":

                if (nuevo.equals("ENTREGADO")) {
                    estado = estado.entregar();
                    return;
                }

                break;
        }

        throw new IllegalStateException(
            "No se permite cambiar de "
            + estado.obtenerNombre()
            + " a "
            + nuevoEstado
        );
    }

    // Obtener el nombre del estado actual
    public String obtenerEstadoActual() {
        return estado.obtenerNombre();
    }
}
