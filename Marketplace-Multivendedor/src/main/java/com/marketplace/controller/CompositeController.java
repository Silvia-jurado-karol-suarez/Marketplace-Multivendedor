package com.marketplace.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marketplace.composite.CategoriaCatalogo;
import com.marketplace.composite.ElementoCatalogo;
import com.marketplace.model.Producto;
import com.marketplace.repository.ProductoRepository;

@RestController
@RequestMapping("/composite")
@CrossOrigin
public class CompositeController {

    private final ProductoRepository productoRepository;

    public CompositeController(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @GetMapping("/catalogo")
    public Map<String, Object> mostrarCatalogo() {

      
        List<Producto> productos = productoRepository.findAll();

        
        CategoriaCatalogo catalogo =
                new CategoriaCatalogo("Catálogo");

        Map<String, CategoriaCatalogo> categorias =
                new HashMap<>();

        for (Producto producto : productos) {

            String nombreCategoria = producto.getCategoria();

            if (nombreCategoria == null ||
                nombreCategoria.trim().isEmpty()) {

                nombreCategoria = "Sin categoría";
            }

            CategoriaCatalogo categoria =
                    categorias.computeIfAbsent(
                        nombreCategoria,
                        CategoriaCatalogo::new
                    );

       
            categoria.agregar(producto);
        }

   
        for (CategoriaCatalogo categoria : categorias.values()) {
            catalogo.agregar(categoria);
        }

        // Mostrar estructura Composite en consola
        catalogo.mostrar();

        // Respuesta para la página
        Map<String, Object> respuesta =
                new HashMap<>();

        respuesta.put("nombre", catalogo.getNombre());
        respuesta.put("categorias", catalogo.getElementos());
        respuesta.put("totalProductos", productos.size());

        return respuesta;
    }
}