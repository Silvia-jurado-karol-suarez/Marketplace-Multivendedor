
package com.marketplace.templatemethod;

public class PublicacionProductoDigital extends PublicacionProductoTemplate {

    @Override
    protected void validarProducto() {
        System.out.println("Validando producto digital...");
        System.out.println("Verificando nombre, precio y archivo digital.");
    }

    @Override
    protected void prepararProducto() {
        System.out.println("Preparando información del producto digital.");
        System.out.println("Configurando acceso para el comprador.");
    }

    @Override
    protected void guardarProducto() {
        System.out.println("Guardando producto digital en el Marketplace.");
    }

    @Override
    protected void notificarPublicacion() {
        System.out.println("Producto digital publicado correctamente.");
    }
}
