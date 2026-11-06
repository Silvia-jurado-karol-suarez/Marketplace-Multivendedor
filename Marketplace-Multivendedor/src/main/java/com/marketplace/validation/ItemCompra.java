package com.marketplace.validation;

public class ItemCompra {

    private ProductoValidacionDTO producto;
    private int cantidad;

    public ItemCompra() {
    }

    public ItemCompra(ProductoValidacionDTO producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public ProductoValidacionDTO getProducto() {
        return producto;
    }

    public void setProducto(ProductoValidacionDTO producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}