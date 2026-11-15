
package com.marketplace.memento;

import java.util.ArrayDeque;
import java.util.Deque;

public class HistorialPedido {

    private final Deque<MementoPedido> estados = new ArrayDeque<>();

    // Guarda un estado del pedido
    public void guardarEstado(MementoPedido memento) {
        if (memento == null) {
            throw new IllegalArgumentException(
                "No se puede guardar un estado nulo."
            );
        }

        estados.push(memento);
    }

    // Recupera el último estado guardado
    public MementoPedido deshacer() {
        if (estados.isEmpty()) {
            return null;
        }

        return estados.pop();
    }

    // Comprueba si existen estados guardados
    public boolean tieneEstadosGuardados() {
        return !estados.isEmpty();
    }
}
