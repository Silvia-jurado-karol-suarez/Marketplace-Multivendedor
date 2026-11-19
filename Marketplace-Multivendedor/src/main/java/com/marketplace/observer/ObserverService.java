
package com.marketplace.observer;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ObserverService {

    private final PedidoObservable pedidoObservable;
    private final RegistroNotificaciones registroNotificaciones;

    private final NotificacionCliente notificacionCliente;
    private final NotificacionVendedor notificacionVendedor;

    public ObserverService(RegistroNotificaciones registroNotificaciones) {

        this.registroNotificaciones = registroNotificaciones;

        this.pedidoObservable = new PedidoObservable();

        this.notificacionCliente =
                new NotificacionCliente(registroNotificaciones);

        this.notificacionVendedor =
                new NotificacionVendedor(registroNotificaciones);

        pedidoObservable.registrarObservador(notificacionCliente);
        pedidoObservable.registrarObservador(notificacionVendedor);
    }

    // Notificar cuando cambia el estado de un pedido
    public void notificarCambioEstado(String idPedido, String estado) {

        pedidoObservable.notificarObservadores(idPedido, estado);
    }

    // Consultar los observadores registrados
    public List<String> obtenerObservadores() {

        return pedidoObservable.obtenerNombresObservadores();
    }

    // Consultar la cantidad de observadores
    public int obtenerCantidadObservadores() {

        return pedidoObservable.obtenerCantidadObservadores();
    }

    // Consultar los eventos generados
    public List<RegistroNotificaciones.NotificacionEvento> obtenerEventos() {

        return registroNotificaciones.obtenerEventos();
    }

    // Limpiar el registro de eventos
    public void limpiarEventos() {

        registroNotificaciones.limpiar();
    }
}
