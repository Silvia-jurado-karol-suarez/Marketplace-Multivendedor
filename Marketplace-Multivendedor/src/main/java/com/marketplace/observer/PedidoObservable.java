
package com.marketplace.observer;

import java.util.ArrayList;
import java.util.List;

public class PedidoObservable {

    private final List<ObservadorPedido> observadores = new ArrayList<>();

    // Registrar un observador
    public synchronized void registrarObservador(ObservadorPedido observador) {

        if (observador != null && !observadores.contains(observador)) {
            observadores.add(observador);
            System.out.println("Observador registrado: "
                    + observador.getClass().getSimpleName());
        }
    }

    // Eliminar la suscripción de un observador
    public synchronized void eliminarObservador(ObservadorPedido observador) {

        if (observadores.remove(observador)) {
            System.out.println("Observador eliminado: "
                    + observador.getClass().getSimpleName());
        }
    }

    // Notificar a todos los observadores registrados
    public void notificarObservadores(String idPedido, String estado) {

        List<ObservadorPedido> copia;

        synchronized (this) {
            copia = new ArrayList<>(observadores);
        }

        for (ObservadorPedido observador : copia) {
            observador.actualizar(idPedido, estado);
        }
    }

    // Consultar la cantidad de observadores registrados
    public synchronized int obtenerCantidadObservadores() {

        return observadores.size();
    }

    // Comprobar si un observador está registrado
    public synchronized boolean estaRegistrado(ObservadorPedido observador) {

        return observadores.contains(observador);
    }

    // Obtener los nombres de los observadores registrados
    public synchronized List<String> obtenerNombresObservadores() {

        List<String> nombres = new ArrayList<>();

        for (ObservadorPedido observador : observadores) {
            nombres.add(observador.getClass().getSimpleName());
        }

        return nombres;
    }
}
