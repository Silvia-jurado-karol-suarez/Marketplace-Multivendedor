
package com.marketplace.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.marketplace.builder.PedidoBuilder;
import com.marketplace.model.Pedido;
import com.marketplace.model.Producto;
import com.marketplace.repository.PedidoRepository;
import com.marketplace.repository.ProductoRepository;
import com.marketplace.observer.ObserverService;
import com.marketplace.state.PedidoState;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ProductoRepository productoRepository;
    private final ObserverService observerService;

    // Constructor
    public PedidoService(
            PedidoRepository pedidoRepository,
            ProductoRepository productoRepository,
            ObserverService observerService) {

        this.pedidoRepository = pedidoRepository;
        this.productoRepository = productoRepository;
        this.observerService = observerService;
    }

    // Crear pedido utilizando el patrón Builder
    public Pedido crearPedido(
            List<String> productosIds,
            double total,
            String estado) {

        List<Producto> productos =
                productoRepository.findAllById(productosIds);

        Pedido pedido = new PedidoBuilder()
                .conProductos(productos)
                .conTotal(total)
                .conEstado(estado)
                .build();

        return pedidoRepository.save(pedido);
    }

    // Consultar todos los pedidos
    public List<Pedido> listarPedidos() {
        return pedidoRepository.findAll();
    }

    // Buscar un pedido por su identificador
    public Optional<Pedido> buscarPorId(String id) {
        return pedidoRepository.findById(id);
    }

    // Actualizar estado utilizando State y notificar mediante Observer
    public Optional<Pedido> actualizarEstado(
            String idPedido,
            String nuevoEstado) {

        Optional<Pedido> resultado =
                pedidoRepository.findById(idPedido);

        if (resultado.isEmpty()) {
            return Optional.empty();
        }

        Pedido pedido = resultado.get();

        // Crear el contexto State con el estado actual
        PedidoState contexto =
                new PedidoState(pedido.getEstado());

        // Validar la transición del estado
        contexto.cambiarEstado(nuevoEstado);

        String estadoActual =
                contexto.obtenerEstadoActual();

        // Evitar notificaciones si no hubo cambio
        if (pedido.getEstado().equalsIgnoreCase(estadoActual)) {
            return Optional.of(pedido);
        }

        // Actualizar el estado del pedido
        pedido.setEstado(estadoActual);

        // Guardar los cambios en MongoDB
        Pedido pedidoActualizado =
                pedidoRepository.save(pedido);

        // Notificar al cliente y al vendedor mediante Observer
        observerService.notificarCambioEstado(
                pedidoActualizado.getId(),
                pedidoActualizado.getEstado()
        );

        return Optional.of(pedidoActualizado);
    }
}
