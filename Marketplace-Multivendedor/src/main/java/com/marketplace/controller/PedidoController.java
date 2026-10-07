package com.marketplace.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.marketplace.dto.CrearPedidoRequest;
import com.marketplace.model.Pedido;
import com.marketplace.service.PedidoService;

@RestController
@RequestMapping("/pedidos")
@CrossOrigin
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    public ResponseEntity<Pedido> crearPedido(
            @RequestBody CrearPedidoRequest request) {

        Pedido pedido =
                pedidoService.crearPedido(
                        request.getProductosIds(),
                        request.getTotal(),
                        "PENDIENTE"
                );

        return ResponseEntity.ok(pedido);
    }

    @GetMapping
    public List<Pedido> listarPedidos() {

        return pedidoService.listarPedidos();
    }
}