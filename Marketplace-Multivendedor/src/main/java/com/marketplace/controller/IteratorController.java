
package com.marketplace.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marketplace.iterator.CatalogoProductos;
import com.marketplace.iterator.CatalogoProductosService;
import com.marketplace.iterator.IteradorProductos;
import com.marketplace.model.Producto;

@RestController
@RequestMapping("/api/iterator")
@CrossOrigin
public class IteratorController {

    private final CatalogoProductosService catalogoService;

    // Constructor
    public IteratorController(
            CatalogoProductosService catalogoService) {

        this.catalogoService = catalogoService;
    }

    // Prueba del patrón Iterator
    @GetMapping("/productos")
    public Map<String, Object> recorrerProductos() {

        // Obtener el catálogo desde MongoDB
        CatalogoProductos catalogo =
                catalogoService.obtenerCatalogo();

        // Crear el iterador
        IteradorProductos iterador =
                catalogo.crearIterador();

        // Lista para almacenar los productos recorridos
        List<Producto> resultado = new ArrayList<>();

        int cantidadRecorrida = 0;

        // Recorrer los productos utilizando Iterator
        while (iterador.haySiguiente()) {

            Producto producto = iterador.siguiente();

            resultado.add(producto);

            cantidadRecorrida++;
        }

        // Construir respuesta
        Map<String, Object> respuesta = new HashMap<>();

        respuesta.put("patron", "Iterator");

        respuesta.put(
                "mensaje",
                "Recorrido de productos realizado mediante Iterator"
        );

        respuesta.put(
                "cantidadRecorrida",
                cantidadRecorrida
        );

        respuesta.put(
                "productos",
                resultado
        );

        return respuesta;
    }
}
