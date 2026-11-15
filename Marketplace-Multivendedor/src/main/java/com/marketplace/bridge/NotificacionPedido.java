
package com.marketplace.bridge;

public class NotificacionPedido extends Notificacion {

    public NotificacionPedido(CanalNotificacion canal) {
        super(canal);
    }

    @Override
    public String enviar(String mensaje) {

        return canal.enviar(
                "Pedido del Marketplace: " + mensaje
        );
    }
}
