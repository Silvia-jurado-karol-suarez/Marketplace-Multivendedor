package com.marketplace.visitor;

import java.util.List;

import org.springframework.stereotype.Service;

import com.marketplace.model.Producto;
import com.marketplace.model.Pedido;
import com.marketplace.model.Vendedor;

@Service
public class VisitorService {

    public VisitanteReporte generarReporte(
            List<Producto> productos,
            List<Pedido> pedidos,
            List<Vendedor> vendedores) {

        VisitanteReporte visitante = new VisitanteReporte();

        if (productos != null) {
            for (Producto producto : productos) {
                producto.accept(visitante);
            }
        }

        if (pedidos != null) {
            for (Pedido pedido : pedidos) {
                pedido.accept(visitante);
            }
        }

        if (vendedores != null) {
            for (Vendedor vendedor : vendedores) {
                vendedor.accept(visitante);
            }
        }

        return visitante;
    }
}