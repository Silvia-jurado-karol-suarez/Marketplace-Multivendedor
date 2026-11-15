
package com.marketplace.iterator;

import com.marketplace.model.Producto;
import com.marketplace.repository.ProductoRepository;
import org.springframework.stereotype.Service;

@Service
public class CatalogoProductosService {

    private final ProductoRepository productoRepository;

    public CatalogoProductosService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public CatalogoProductos obtenerCatalogo() {

        return new CatalogoProductos(
                productoRepository.findAll()
        );
    }
}
