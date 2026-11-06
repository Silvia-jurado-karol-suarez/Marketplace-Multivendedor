package com.marketplace.validation;

public class ResultadoValidacion {

    private boolean valido;
    private String mensaje;

    public ResultadoValidacion(boolean valido, String mensaje) {
        this.valido = valido;
        this.mensaje = mensaje;
    }

    public boolean isValido() {
        return valido;
    }

    public String getMensaje() {
        return mensaje;
    }

    public static ResultadoValidacion exitoso() {
        return new ResultadoValidacion(
            true,
            "Todas las validaciones fueron aprobadas."
        );
    }

    public static ResultadoValidacion error(String mensaje) {
        return new ResultadoValidacion(false, mensaje);
    }
}