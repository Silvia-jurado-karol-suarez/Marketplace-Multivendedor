package com.marketplace.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marketplace.model.Producto;

@RestController
@RequestMapping("/prototype")
public class PrototypeController {

    @GetMapping("/duplicar")
    public String duplicarProducto() {

        Producto original =
                new Producto(
                        "P001",
                        "Laptop empresarial",
                        "Laptop para trabajo",
                        2000000,
                        5,
                        "Tecnología",
                        null
                );


        Producto copia =
                original.clone();


        copia.setNombre(
                "Laptop empresarial - Copia"
        );


        return "PRODUCTO ORIGINAL" +
               "<br>Nombre: " +
               original.getNombre() +

               "<br>Precio: $" +
               String.format(
                   "%,.0f",
                   original.getPrecio()
               ) +

               "<br><br>" +

               "PRODUCTO CLONADO" +
               "<br>Nombre: " +
               copia.getNombre() +

               "<br>Precio: $" +
               String.format(
                   "%,.0f",
                   copia.getPrecio()
               ) +

               "<br><br>" +

               "Son objetos diferentes: " +
               (original != copia);
    }
}