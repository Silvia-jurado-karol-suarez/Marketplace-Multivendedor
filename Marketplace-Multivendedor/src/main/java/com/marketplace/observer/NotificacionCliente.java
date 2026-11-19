
package com.marketplace.observer;

public class NotificacionCliente implements ObservadorPedido {

    private final RegistroNotificaciones registro;

    public NotificacionCliente(RegistroNotificaciones registro) {
        this.registro = registro;
    }

    @Override
    public void actualizar(String idPedido, String estado) {

        registro.registrar(
                "NotificacionCliente",
                idPedido,
                estado
        );

        System.out.println("NOTIFICACIÓN PARA EL CLIENTE");
        System.out.println("Pedido: " + idPedido);
        System.out.println("Nuevo estado: " + estado);
        System.out.println("--------------------------------");
    }
}
