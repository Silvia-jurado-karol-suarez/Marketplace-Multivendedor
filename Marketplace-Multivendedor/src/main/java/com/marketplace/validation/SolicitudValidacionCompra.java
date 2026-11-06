package com.marketplace.validation;

import java.util.List;

public class SolicitudValidacionCompra {

    private List<ItemCompra> productos;
    private String metodoPago;
    private String tipoEnvio;

    public SolicitudValidacionCompra(
            List<ItemCompra> productos,
            String metodoPago,
            String tipoEnvio) {

        this.productos = productos;
        this.metodoPago = metodoPago;
        this.tipoEnvio = tipoEnvio;
    }

    public List<ItemCompra> getProductos() {
        return productos;
    }

    public void setProductos(List<ItemCompra> productos) {
        this.productos = productos;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public String getTipoEnvio() {
        return tipoEnvio;
    }

    public void setTipoEnvio(String tipoEnvio) {
        this.tipoEnvio = tipoEnvio;
    }
}