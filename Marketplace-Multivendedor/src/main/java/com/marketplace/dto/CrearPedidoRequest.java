package com.marketplace.dto;

import java.util.List;

public class CrearPedidoRequest {

    private List<String> productosIds;

    private double total;


    public List<String> getProductosIds() {
        return productosIds;
    }

    public void setProductosIds(List<String> productosIds) {

        this.productosIds = productosIds;
    }


    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {

        this.total = total;
    }
}