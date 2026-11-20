
package com.marketplace.service.templatemethod;

import org.springframework.stereotype.Service;

import com.marketplace.templatemethod.PublicacionProductoTemplate;
import com.marketplace.templatemethod.PublicacionProductoFisico;
import com.marketplace.templatemethod.PublicacionProductoDigital;

@Service
public class TemplateMethodService {

    public String publicarProducto(String tipo) {

        PublicacionProductoTemplate publicacion;

        switch (tipo.toLowerCase()) {

            case "fisico":
                publicacion = new PublicacionProductoFisico();
                break;

            case "digital":
                publicacion = new PublicacionProductoDigital();
                break;

            default:
                throw new IllegalArgumentException(
                    "Tipo de producto no válido. Use fisico o digital."
                );
        }

        publicacion.publicarProducto();

        return "Proceso de publicación finalizado para producto " + tipo;
    }
}
