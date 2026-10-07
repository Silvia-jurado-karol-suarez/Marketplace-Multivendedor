package com.marketplace.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.marketplace.builder.PedidoBuilder;
import com.marketplace.model.Pedido;
import com.marketplace.model.Producto;
import com.marketplace.repository.PedidoRepository;
import com.marketplace.repository.ProductoRepository;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ProductoRepository productoRepository;

    public PedidoService(
            PedidoRepository pedidoRepository,
            ProductoRepository productoRepository) {

        this.pedidoRepository = pedidoRepository;
        this.productoRepository = productoRepository;
    }

    public Pedido crearPedido(
            List<String> productosIds,
            double total,
            String estado) {

        List<Producto> productos =
                productoRepository.findAllById(
                        productosIds
                );

        Pedido pedido =
                new PedidoBuilder()
                        .conProductos(productos)
                        .conTotal(total)
                        .conEstado(estado)
                        .build();

        return pedidoRepository.save(
                pedido
        );
    }

    public List<Pedido> listarPedidos() {

        return pedidoRepository.findAll();
    }
}