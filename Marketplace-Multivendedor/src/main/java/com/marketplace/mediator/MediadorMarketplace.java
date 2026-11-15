
package com.marketplace.mediator;

import java.util.ArrayList;
import java.util.List;

public class MediadorMarketplace implements MediadorCompra {

    private CarritoComponente carrito;
    private PedidoComponente pedido;
    private PagoComponente pago;
    private EnvioComponente envio;

    private List<String> eventos;

    public MediadorMarketplace() {
        eventos = new ArrayList<>();

        carrito = new CarritoComponente(this);
        pedido = new PedidoComponente(this);
        pago = new PagoComponente(this);
        envio = new EnvioComponente(this);
    }

    @Override
    public void notificar(ComponenteCompra componente, String evento) {

        eventos.add(evento);

        switch (evento) {
            case "CARRITO_CONFIRMADO":
                eventos.add("El mediador solicita crear el pedido.");
                pedido.crearPedido();
                break;

            case "PEDIDO_CREADO":
                eventos.add("El mediador solicita procesar el pago.");
                pago.procesarPago();
                break;

            case "PAGO_PROCESADO":
                eventos.add("El mediador solicita preparar el envío.");
                envio.prepararEnvio();
                break;

            case "ENVIO_PREPARADO":
                eventos.add("La compra ha finalizado.");
                break;

            default:
                eventos.add("Evento no reconocido: " + evento);
        }
    }

    public void iniciarCompra() {
        eventos.clear();
        carrito.confirmarCarrito();
    }

    public List<String> obtenerEventos() {
        return eventos;
    }
}
