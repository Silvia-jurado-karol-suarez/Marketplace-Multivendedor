
package com.marketplace.templatemethod;

public class PublicacionProductoFisico extends PublicacionProductoTemplate {

    @Override
    protected void validarProducto() {
        System.out.println("Validando producto físico...");
        System.out.println("Verificando nombre, precio y stock.");
    }

    @Override
    protected void prepararProducto() {
        System.out.println("Preparando información del producto físico.");
        System.out.println("Calculando disponibilidad para envío.");
    }

    @Override
    protected void guardarProducto() {
        System.out.println("Guardando producto físico en el Marketplace.");
    }

    @Override
    protected void notificarPublicacion() {
        System.out.println("Producto físico publicado correctamente.");
    }
}
