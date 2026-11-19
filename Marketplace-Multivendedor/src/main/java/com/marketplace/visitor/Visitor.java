package com.marketplace.visitor;

import com.marketplace.model.Producto;
import com.marketplace.model.Pedido;
import com.marketplace.model.Vendedor;

public interface Visitor {

    void visitarProducto(Producto producto);

    void visitarPedido(Pedido pedido);

    void visitarVendedor(Vendedor vendedor);
}
