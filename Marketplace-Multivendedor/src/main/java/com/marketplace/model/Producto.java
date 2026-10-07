package com.marketplace.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.marketplace.composite.ElementoCatalogo;

@Document(collection = "productos")
public class Producto implements ElementoCatalogo, Cloneable {

    @Id
    private String id;

    private String nombre;
    private String descripcion;
    private double precio;
    private int stock;
    private String categoria;
    private Vendedor vendedor;

    
    public Producto() {
    }

    
    public Producto(String id, String nombre, String descripcion,
                    double precio, int stock, String categoria,
                    Vendedor vendedor) {

        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.categoria = categoria;
        this.vendedor = vendedor;
    }

    // =========================
    // GETTERS Y SETTERS
    // =========================

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }

    // =========================
    // MÉTODOS DEL COMPOSITE
    // =========================

    @Override
    public void mostrar() {
        System.out.println(
            "Producto: " + nombre +
            " | Precio: $" + precio
        );
    }

    // =========================
    // MÉTODO DEL PROTOTYPE
    // =========================

    @Override
    public Producto clone() {

        try {

            return (Producto) super.clone();

        } catch (CloneNotSupportedException e) {

            throw new RuntimeException(
                "No se pudo clonar el producto",
                e
            );
        }
    }
}