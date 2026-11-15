
package com.marketplace.bridge;

public class EmailNotificacion implements CanalNotificacion {

    @Override
    public String enviar(String mensaje) {

        return "Correo electrónico: " + mensaje;

    }
}
