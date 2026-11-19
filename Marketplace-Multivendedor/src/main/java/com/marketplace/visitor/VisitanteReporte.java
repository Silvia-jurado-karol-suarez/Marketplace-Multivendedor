package com.marketplace.visitor;

import java.util.ArrayList;
import java.util.List;

import com.marketplace.model.Producto;
import com.marketplace.model.Pedido;
import com.marketplace.model.Vendedor;

public class VisitanteReporte implements Visitor {

    private final List<String> resultados = new ArrayList<>();

    private int totalProductos;
    private int totalPedidos;
    private int totalVendedores;

    @Override
    public void visitarProducto(Producto producto) {

        totalProductos++;

        resultados.add(
            "PRODUCTO | ID: " + producto.getId()
            + " | Nombre: " + producto.getNombre()
            + " | Precio: $" + producto.getPrecio()
            + " | Stock: " + producto.getStock()
            + " | Categoría: " + producto.getCategoria()
        );
    }

    @Override
    public void visitarPedido(Pedido pedido) {

        totalPedidos++;

        resultados.add(
            "PEDIDO | ID: " + pedido.getId()
            + " | Total: $" + pedido.getTotal()
            + " | Estado: " + pedido.getEstado()
            + " | Cantidad de productos: "
            + (pedido.getProductos() == null
                ? 0 : pedido.getProductos().size())
        );
    }

    @Override
    public void visitarVendedor(Vendedor vendedor) {

        totalVendedores++;

        resultados.add(
            "VENDEDOR | ID: " + vendedor.getId()
            + " | Nombre: " + vendedor.getNombre()
            + " | Correo: " + vendedor.getCorreo()
        );
    }

    public List<String> getResultados() {
        return resultados;
    }

    public int getTotalProductos() {
        return totalProductos;
    }

    public int getTotalPedidos() {
        return totalPedidos;
    }

    public int getTotalVendedores() {
        return totalVendedores;
    }

    public int getTotalElementos() {
        return totalProductos + totalPedidos + totalVendedores;
    }
}