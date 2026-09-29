package com.marketplace.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "pagos")
public class PagoRegistro {

    @Id
    private String id;

    private String pedidoId;
    private String metodo;
    private double monto;
    private String estado;

    public PagoRegistro() {
    }

    public PagoRegistro(
            String id,
            String pedidoId,
            String metodo,
            double monto,
            String estado) {

        this.id = id;
        this.pedidoId = pedidoId;
        this.metodo = metodo;
        this.monto = monto;
        this.estado = estado;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(String pedidoId) {
        this.pedidoId = pedidoId;
    }

    public String getMetodo() {
        return metodo;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}