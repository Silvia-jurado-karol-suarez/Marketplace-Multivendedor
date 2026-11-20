
package com.marketplace.templatemethod;

public abstract class PublicacionProductoTemplate {

    public final void publicarProducto() {

        validarProducto();

        prepararProducto();

        guardarProducto();

        notificarPublicacion();
    }

    protected abstract void validarProducto();

    protected abstract void prepararProducto();

    protected abstract void guardarProducto();

    protected void notificarPublicacion() {
        System.out.println("Producto publicado correctamente.");
    }
}
