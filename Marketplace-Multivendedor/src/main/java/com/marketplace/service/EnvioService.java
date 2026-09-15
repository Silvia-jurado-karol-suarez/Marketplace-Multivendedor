package com.marketplace.service;

import org.springframework.stereotype.Service;

import com.marketplace.shipping.CalculadorEnvio;
import com.marketplace.shipping.EnvioFactory;
import com.marketplace.shipping.GeneradorGuia;

@Service
public class EnvioService {

    public String procesarEnvio(
            EnvioFactory factory,
            double peso,
            String pedidoId) {

        CalculadorEnvio calculador =
                factory.crearCalculadorEnvio();

        GeneradorGuia generador =
                factory.crearGeneradorGuia();

        double costo = calculador.calcularCosto(peso);
        String guia = generador.generarGuia(pedidoId);

        return "Guía: " + guia +
               " | Costo de envío: $" + costo;
    }
}