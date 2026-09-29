package com.marketplace.controller;

import java.util.List;

public class CrearPedidoRequest {

    private List<String> productosIds;

    public CrearPedidoRequest() {
    }

    public List<String> getProductosIds() {
        return productosIds;
    }

    public void setProductosIds(List<String> productosIds) {
        this.productosIds = productosIds;
    }
}