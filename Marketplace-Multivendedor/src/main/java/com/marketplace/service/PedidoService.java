package com.marketplace.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.marketplace.builder.PedidoBuilder;
import com.marketplace.model.Pedido;
import com.marketplace.model.Producto;
import com.marketplace.repository.PedidoRepository;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public Pedido crearPedido(
            List<Producto> productos,
            double total,
            String estado) {

        Pedido pedido = new PedidoBuilder()
                .conProductos(productos)
                .conTotal(total)
                .conEstado(estado)
                .build();

        return pedidoRepository.save(pedido);
    }
}