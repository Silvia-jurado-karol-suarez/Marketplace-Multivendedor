package com.marketplace.shipping;

public interface EnvioFactory {

    CalculadorEnvio crearCalculadorEnvio();

    GeneradorGuia crearGeneradorGuia();
}