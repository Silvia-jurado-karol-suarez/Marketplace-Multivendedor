package com.marketplace.shipping;

public class EnvioInternacionalFactory implements EnvioFactory {

    @Override
    public CalculadorEnvio crearCalculadorEnvio() {
        return new CalculadorEnvioInternacional();
    }

    @Override
    public GeneradorGuia crearGeneradorGuia() {
        return new GeneradorGuiaInternacional();
    }
}