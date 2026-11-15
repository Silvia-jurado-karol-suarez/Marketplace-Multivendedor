
package com.marketplace.bridge;

public abstract class Notificacion {

    protected CanalNotificacion canal;

    public Notificacion(CanalNotificacion canal) {
        this.canal = canal;
    }

    public abstract String enviar(String mensaje);
}
