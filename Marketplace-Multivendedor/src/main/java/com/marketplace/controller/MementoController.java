
package com.marketplace.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marketplace.model.Pedido;
import com.marketplace.memento.HistorialPedido;
import com.marketplace.memento.MementoPedido;
import com.marketplace.memento.PedidoOriginator;

@RestController
@RequestMapping("/api/memento")
public class MementoController {

    @PostMapping("/ejecutar")
    public Map<String, Object> ejecutarDemostracion() {

        // 1. Crear un pedido de prueba
        Pedido pedido = new Pedido(
            "PED-001",
            new ArrayList<>(),
            150000,
            "Pendiente"
        );

        PedidoOriginator originator = new PedidoOriginator(pedido);
        HistorialPedido historial = new HistorialPedido();

        // 2. Guardar el estado original
        MementoPedido estadoOriginal = originator.crearMemento();
        historial.guardarEstado(estadoOriginal);

        Map<String, Object> original = obtenerEstado(pedido);

        // 3. Modificar el pedido
        pedido.setEstado("En preparación");
        pedido.setTotal(175000);

        Map<String, Object> modificado = obtenerEstado(pedido);

        // 4. Restaurar el estado anterior
        MementoPedido estadoGuardado = historial.deshacer();
        originator.restaurarMemento(estadoGuardado);

        Map<String, Object> restaurado = obtenerEstado(pedido);

        // 5. Preparar respuesta para la página web
        Map<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put("patron", "Memento");
        respuesta.put("original", original);
        respuesta.put("modificado", modificado);
        respuesta.put("restaurado", restaurado);
        respuesta.put("mensaje",
            "El pedido recuperó correctamente su estado anterior.");

        return respuesta;
    }

    private Map<String, Object> obtenerEstado(Pedido pedido) {

        Map<String, Object> estado = new LinkedHashMap<>();

        estado.put("id", pedido.getId());
        estado.put("total", pedido.getTotal());
        estado.put("estado", pedido.getEstado());
        estado.put("cantidadProductos",
            pedido.getProductos() == null ? 0 : pedido.getProductos().size());

        return estado;
    }
}
