
package com.marketplace.observer;

public class NotificacionVendedor implements ObservadorPedido {

    private final RegistroNotificaciones registro;

    public NotificacionVendedor(RegistroNotificaciones registro) {
        this.registro = registro;
    }

    @Override
    public void actualizar(String idPedido, String estado) {

        registro.registrar(
                "NotificacionVendedor",
                idPedido,
                estado
        );

        System.out.println("NOTIFICACIÓN PARA EL VENDEDOR");
        System.out.println("Pedido relacionado: " + idPedido);
        System.out.println("Estado actualizado: " + estado);
        System.out.println("El vendedor ha sido informado del cambio.");
        System.out.println("--------------------------------");
    }
}
