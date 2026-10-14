
package com.marketplace.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.marketplace.dto.CrearPedidoRequest;
import com.marketplace.dto.FinalizarCompraRequest;
import com.marketplace.facade.MarketplaceFacade;
import com.marketplace.model.Pedido;
import com.marketplace.model.PagoRegistro;

@RestController
@RequestMapping("/marketplace")
@CrossOrigin
public class MarketplaceFacadeController {

    private final MarketplaceFacade facade;

    public MarketplaceFacadeController(
            MarketplaceFacade facade) {
        this.facade = facade;
    }

    @PostMapping("/pedido")
    public ResponseEntity<Pedido> crearPedido(
            @RequestBody CrearPedidoRequest request) {
        return ResponseEntity.ok(
                facade.crearPedido(
                        request.getProductosIds(),
                        request.getTotal()));
    }

    @PostMapping("/pago")
    public ResponseEntity<PagoRegistro> procesarPago(
            @RequestBody PagoRequest request) {
        return ResponseEntity.ok(
                facade.procesarPago(
                        request.getMetodo(),
                        request.getMonto(),
                        request.getPedidoId()));
    }

    @PostMapping("/finalizar")
    public ResponseEntity<?> finalizarCompra(
            @RequestBody FinalizarCompraRequest request) {
        try {
            Map<String, Object> resultado =
                    facade.finalizarCompra(
                            request.getProductosIds(),
                            request.getSubtotal(),
                            request.getPorcentajeDescuento(),
                            request.getMetodoPago(),
                            request.getTipoEnvio(),
                            request.getPeso());

            return ResponseEntity.ok(resultado);

        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(
                    Map.of("error", ex.getMessage()));

        } catch (Exception ex) {
            return ResponseEntity.internalServerError().body(
                    Map.of("error",
                            "No se pudo completar la compra. "
                            + "Verifica el estado del pedido "
                            + "antes de reintentar."));
        }
    }
}
