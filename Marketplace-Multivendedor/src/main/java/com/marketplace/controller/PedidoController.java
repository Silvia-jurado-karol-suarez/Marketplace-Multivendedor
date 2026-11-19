
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

    // Crear un pedido utilizando el patrón Builder
    @PostMapping
    public ResponseEntity<Pedido> crearPedido(
            @RequestBody CrearPedidoRequest request) {

        Pedido pedido = pedidoService.crearPedido(
                request.getProductosIds(),
                request.getTotal(),
                "PENDIENTE"
        );

        return ResponseEntity.ok(pedido);
    }

    // Consultar todos los pedidos
    @GetMapping
    public ResponseEntity<List<Pedido>> listarPedidos() {

        List<Pedido> pedidos = pedidoService.listarPedidos();

        return ResponseEntity.ok(pedidos);
    }

    // Consultar un pedido por su identificador
    @GetMapping("/{id}")
    public ResponseEntity<Pedido> obtenerPedido(
            @PathVariable String id) {

        return pedidoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Actualizar el estado utilizando State y activar Observer
    @PatchMapping("/{id}/estado")
    public ResponseEntity<?> actualizarEstado(
            @PathVariable String id,
            @RequestParam String estado) {

        try {
            return pedidoService.actualizarEstado(id, estado)
                    .<ResponseEntity<?>>map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());

        } catch (IllegalArgumentException |
                 IllegalStateException e) {

            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }
}
