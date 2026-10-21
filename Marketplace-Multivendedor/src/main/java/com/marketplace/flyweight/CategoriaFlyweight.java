
package com.marketplace.flyweight;

public class CategoriaFlyweight {

    private final String nombre;
    private final String icono;
    private final String color;

    public CategoriaFlyweight(
            String nombre, String icono, String color) {
        this.nombre = nombre;
        this.icono = icono;
        this.color = color;
    }

    public String getNombre() {
        return nombre;
    }

    public String getIcono() {
        return icono;
    }

    public String getColor() {
        return color;
    }
}
