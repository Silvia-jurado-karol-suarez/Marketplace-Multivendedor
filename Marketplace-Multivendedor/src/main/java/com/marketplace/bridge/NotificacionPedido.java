package com.marketplace.bridge;

public class NotificacionPedido extends Notificacion {

    public NotificacionPedido(CanalNotificacion canal) {

        super(canal);
    }

    @Override
    public void enviar(String mensaje) {

        canal.enviar(
                "Pedido del Marketplace: " + mensaje
        );
    }
}