
package com.marketplace.observer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class RegistroNotificaciones {

    private final List<NotificacionEvento> eventos =
            new ArrayList<>();

    public synchronized void registrar(
            String observador,
            String idPedido,
            String estado) {

        NotificacionEvento evento = new NotificacionEvento(
                observador,
                idPedido,
                estado,
                LocalDateTime.now()
        );

        eventos.add(evento);
    }

    public synchronized List<NotificacionEvento> obtenerEventos() {
        return new ArrayList<>(eventos);
    }

    public synchronized void limpiar() {
        eventos.clear();
    }

    public static class NotificacionEvento {

        private final String observador;
        private final String idPedido;
        private final String estado;
        private final LocalDateTime fecha;

        public NotificacionEvento(
                String observador,
                String idPedido,
                String estado,
                LocalDateTime fecha) {

            this.observador = observador;
            this.idPedido = idPedido;
            this.estado = estado;
            this.fecha = fecha;
        }

        public String getObservador() {
            return observador;
        }

        public String getIdPedido() {
            return idPedido;
        }

        public String getEstado() {
            return estado;
        }

        public LocalDateTime getFecha() {
            return fecha;
        }
    }
}
