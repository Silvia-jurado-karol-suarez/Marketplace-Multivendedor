package com.marketplace.bridge;

public class EmailNotificacion implements CanalNotificacion {

    @Override
    public void enviar(String mensaje) {

        System.out.println(
                "Enviando correo: " + mensaje
        );
    }
}