
package com.marketplace.facade;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.marketplace.decorator.DescuentoDecorator;
import com.marketplace.decorator.PrecioBase;
import com.marketplace.decorator.PrecioComponent;
import com.marketplace.model.Pedido;
import com.marketplace.model.PagoRegistro;
import com.marketplace.payment.PagoFactory;
import com.marketplace.payment.PagoNequiFactory;
import com.marketplace.payment.PagoPSEFactory;
import com.marketplace.payment.PagoTarjetaFactory;
import com.marketplace.service.EnvioService;
import com.marketplace.service.PagoService;
import com.marketplace.service.PedidoService;
import com.marketplace.shipping.EnvioFactory;
import com.marketplace.shipping.EnvioInternacionalFactory;
import com.marketplace.shipping.EnvioNacionalFactory;

@Service
public class MarketplaceFacade {

    private final PedidoService pedidoService;
    private final PagoService pagoService;
    private final EnvioService envioService;

    public MarketplaceFacade(
            PedidoService pedidoService,
            PagoService pagoService,
            EnvioService envioService) {
        this.pedidoService = pedidoService;
        this.pagoService = pagoService;
        this.envioService = envioService;
    }

    public Pedido crearPedido(
            List<String> productosIds,
            double total) {
        return pedidoService.crearPedido(
                productosIds, total, "PENDIENTE");
    }

    public PagoRegistro procesarPago(
            String metodo,
            double monto,
            String pedidoId) {
        PagoFactory factory = crearPagoFactory(metodo);
        return pagoService.procesarPago(
                factory, monto, pedidoId);
    }

    public Map<String, Object> finalizarCompra(
            List<String> productosIds,
            double subtotal,
            double porcentajeDescuento,
            String metodoPago,
            String tipoEnvio,
            double peso) {

        if (productosIds == null || productosIds.isEmpty()) {
            throw new IllegalArgumentException(
                    "El carrito está vacío.");
        }

        if (!Double.isFinite(subtotal) || subtotal < 0) {
            throw new IllegalArgumentException(
                    "El subtotal no es válido.");
        }

        if (!Double.isFinite(porcentajeDescuento)
                || porcentajeDescuento < 0
                || porcentajeDescuento > 100) {
            throw new IllegalArgumentException(
                    "El descuento debe estar entre 0 y 100.");
        }

        if (!Double.isFinite(peso) || peso <= 0) {
            throw new IllegalArgumentException(
                    "El peso debe ser mayor que cero.");
        }

        
        crearPagoFactory(metodoPago);
        validarTipoEnvio(tipoEnvio);

        
        PrecioComponent precioBase = new PrecioBase(subtotal);

        DescuentoDecorator decorador =
                new DescuentoDecorator(
                        precioBase, porcentajeDescuento);

        double total = decorador.calcularPrecio();
        double descuento = decorador.calcularDescuento();

       
        Pedido pedido = pedidoService.crearPedido(
                productosIds, total, "PENDIENTE");

        PagoRegistro pago = procesarPago(
                metodoPago, total, pedido.getId());

        String resultadoEnvio = procesarEnvio(
                tipoEnvio, peso, pedido.getId());

        return Map.of(
                "subtotal", subtotal,
                "porcentajeDescuento", porcentajeDescuento,
                "descuento", descuento,
                "total", total,
                "pedido", pedido,
                "pago", pago,
                "envio", resultadoEnvio
        );
    }

    private PagoFactory crearPagoFactory(String metodo) {
        if (metodo == null) {
            throw new IllegalArgumentException(
                    "Selecciona un método de pago.");
        }

        switch (metodo.toLowerCase(Locale.ROOT)) {
            case "tarjeta":
                return new PagoTarjetaFactory();
            case "pse":
                return new PagoPSEFactory();
            case "nequi":
                return new PagoNequiFactory();
            default:
                throw new IllegalArgumentException(
                        "Método de pago no disponible.");
        }
    }

    private void validarTipoEnvio(String tipo) {
        if (tipo == null
                || !(tipo.equalsIgnoreCase("nacional")
                || tipo.equalsIgnoreCase("internacional"))) {
            throw new IllegalArgumentException(
                    "Tipo de envío no disponible.");
        }
    }

    private String procesarEnvio(
            String tipo, double peso, String pedidoId) {
        validarTipoEnvio(tipo);

        EnvioFactory factory =
                tipo.equalsIgnoreCase("nacional")
                ? new EnvioNacionalFactory()
                : new EnvioInternacionalFactory();

        return envioService.procesarEnvio(
                factory, peso, pedidoId);
    }
}
