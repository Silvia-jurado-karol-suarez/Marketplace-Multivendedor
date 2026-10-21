
package com.marketplace.flyweight;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

@Component
public class CategoriaFlyweightFactory {

    private final Map<String, CategoriaFlyweight> categorias =
            new ConcurrentHashMap<>();

    public CategoriaFlyweight obtenerCategoria(String nombre) {
    	String clave = nombre == null
    	        ? "general"
    	        : java.text.Normalizer
    	            .normalize(nombre.trim(), java.text.Normalizer.Form.NFD)
    	            .replaceAll("\\p{M}", "")
    	            .toLowerCase(java.util.Locale.ROOT);

        return categorias.computeIfAbsent(clave, this::crearCategoria);
    }

    private CategoriaFlyweight crearCategoria(String nombre) {
        switch (nombre) {
            case "tecnologia":
                return new CategoriaFlyweight(
                        "Tecnología", "💻", "#2196F3");

            case "ropa":
                return new CategoriaFlyweight(
                        "Ropa", "👕", "#E91E63");

            case "hogar":
                return new CategoriaFlyweight(
                        "Hogar", "🏠", "#4CAF50");

            case "deportes":
                return new CategoriaFlyweight(
                        "Deportes", "⚽", "#FF9800");

            default:
                return new CategoriaFlyweight(
                        "General", "📦", "#9E9E9E");
        }
    }
}
