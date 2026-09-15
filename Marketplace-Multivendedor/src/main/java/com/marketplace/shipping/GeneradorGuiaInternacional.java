package com.marketplace.shipping;

public class GeneradorGuiaInternacional implements GeneradorGuia {

    @Override
    public String generarGuia(String pedidoId) {
        return "GUIA-INTERNACIONAL-" + pedidoId;
    }
}