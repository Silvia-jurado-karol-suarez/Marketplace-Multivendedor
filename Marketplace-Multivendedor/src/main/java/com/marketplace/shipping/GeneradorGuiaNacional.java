package com.marketplace.shipping;

public class GeneradorGuiaNacional implements GeneradorGuia {

    @Override
    public String generarGuia(String pedidoId) {
        return "GUIA-NACIONAL-" + pedidoId;
    }
}