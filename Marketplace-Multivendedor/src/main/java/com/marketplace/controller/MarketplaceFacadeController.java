package com.marketplace.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.marketplace.dto.CrearPedidoRequest;
import com.marketplace.facade.MarketplaceFacade;
import com.marketplace.model.Pedido;
import com.marketplace.model.PagoRegistro;

@RestController
@RequestMapping("/marketplace")
@CrossOrigin
public class MarketplaceFacadeController {

    private final MarketplaceFacade facade;

    public MarketplaceFacadeController(MarketplaceFacade facade) {

        this.facade = facade;
    }

    @PostMapping("/pedido")
    public ResponseEntity<Pedido> crearPedido(@RequestBody CrearPedidoRequest request) {

        Pedido pedido =
                facade.crearPedido(request.getProductosIds(), request.getTotal());

        return ResponseEntity.ok(pedido);
    }

    @PostMapping("/pago")
    public ResponseEntity<PagoRegistro> procesarPago(
            @RequestBody PagoRequest request) {

        PagoRegistro pago =
                facade.procesarPago(
                        request.getMetodo(),
                        request.getMonto(),
                        request.getPedidoId()
                );

        return ResponseEntity.ok(pago);
    }
}