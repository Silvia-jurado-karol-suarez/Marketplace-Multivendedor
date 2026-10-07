package com.marketplace.facade;

import java.util.List;

import org.springframework.stereotype.Service;

import com.marketplace.model.Pedido;
import com.marketplace.model.PagoRegistro;
import com.marketplace.payment.PagoFactory;
import com.marketplace.payment.PagoNequiFactory;
import com.marketplace.payment.PagoPSEFactory;
import com.marketplace.payment.PagoTarjetaFactory;
import com.marketplace.service.PagoService;
import com.marketplace.service.PedidoService;

@Service
public class MarketplaceFacade {

    private final PedidoService pedidoService;
    private final PagoService pagoService;

    public MarketplaceFacade(
            PedidoService pedidoService,
            PagoService pagoService) {

        this.pedidoService = pedidoService;
        this.pagoService = pagoService;
    }

    // ==========================================
    // FACADE - CREAR PEDIDO
    // ==========================================

    public Pedido crearPedido(
            List<String> productosIds,
            double total) {

        return pedidoService.crearPedido(
                productosIds,
                total,
                "PENDIENTE"
        );
    }

    // ==========================================
    // FACADE - PROCESAR PAGO
    // ==========================================

    public PagoRegistro procesarPago(
            String metodo,
            double monto,
            String pedidoId) {

        PagoFactory factory;

        switch (metodo.toLowerCase()) {

            case "tarjeta":

                factory =
                        new PagoTarjetaFactory();

                break;

            case "pse":

                factory =
                        new PagoPSEFactory();

                break;

            case "nequi":

                factory =
                        new PagoNequiFactory();

                break;

            default:

                throw new IllegalArgumentException(
                        "Método de pago no disponible."
                );
        }

        return pagoService.procesarPago(
                factory,
                monto,
                pedidoId
        );
    }
}