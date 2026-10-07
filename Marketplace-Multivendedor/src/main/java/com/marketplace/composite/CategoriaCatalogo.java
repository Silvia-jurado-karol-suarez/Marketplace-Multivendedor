package com.marketplace.composite;

import java.util.ArrayList;
import java.util.List;

public class CategoriaCatalogo implements ElementoCatalogo {

    private String nombre;
    private List<ElementoCatalogo> elementos = new ArrayList<>();
    public CategoriaCatalogo(String nombre) {
        this.nombre = nombre;
    }

    public void agregar(ElementoCatalogo elemento) {
        elementos.add(elemento);
    }

    public void eliminar(ElementoCatalogo elemento) {
        elementos.remove(elemento);
    }

    public List<ElementoCatalogo> getElementos() {
        return elementos;
    }

    @Override
    public String getNombre() {
        return nombre;
    }

    @Override
    public double getPrecio() {

        double total = 0;

        for (ElementoCatalogo elemento : elementos) {
            total += elemento.getPrecio();
        }

        return total;
    }

    @Override
    public void mostrar() {

        System.out.println("Categoría: " + nombre);

        for (ElementoCatalogo elemento : elementos) {
            elemento.mostrar();
        }
    }
}