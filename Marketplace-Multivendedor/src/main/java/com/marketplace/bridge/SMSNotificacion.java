
package com.marketplace.bridge;

public class SMSNotificacion implements CanalNotificacion {

    @Override
    public String enviar(String mensaje) {

        return "Mensaje SMS: " + mensaje;

    }
}
