package com.marketplace.shipping;

public class EnvioNacionalFactory implements EnvioFactory {

    @Override
    public CalculadorEnvio crearCalculadorEnvio() {
        return new CalculadorEnvioNacional();
    }

    @Override
    public GeneradorGuia crearGeneradorGuia() {
        return new GeneradorGuiaNacional();
    }
}