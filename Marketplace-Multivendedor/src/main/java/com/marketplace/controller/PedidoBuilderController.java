package com.marketplace.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marketplace.builder.PedidoBuilder;
import com.marketplace.model.Pedido;
import com.marketplace.model.Producto;

@RestController
@RequestMapping("/pedidos")
public class PedidoBuilderController {

    @GetMapping("/builder/{total}/{estado}")
    public String crearPedido(
            @PathVariable double total,
            @PathVariable String estado) {


        List<Producto> productos =
                new ArrayList<>();


        Producto producto =
                new Producto(
                        "P001",
                        "Laptop empresarial",
                        "Laptop para trabajo",
                        total,
                        1,
                        "Tecnología",
                        null
                );


        productos.add(producto);


        Pedido pedido =
                new PedidoBuilder()
                        .conProductos(productos)
                        .conTotal(total)
                        .conEstado(estado)
                        .build();


        return """
                <strong>PATRÓN BUILDER</strong>
                <br><br>
                PEDIDO CREADO
                <br><br>
                Estado: """ + pedido.getEstado() +
                "<br>Total: $" +
                String.format(
                        "%,.0f",
                        pedido.getTotal()
                ) +
                "<br>Productos: " +
                pedido.getProductos().size();
    }
}