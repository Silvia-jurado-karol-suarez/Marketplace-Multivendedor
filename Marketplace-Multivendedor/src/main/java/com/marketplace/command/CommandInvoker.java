package com.marketplace.command;

import java.util.Stack;

public class CommandInvoker {

    private final Stack<Command> historial = new Stack<>();

    public void ejecutarComando(Command comando) {
        comando.ejecutar();
        historial.push(comando);
    }

    public void deshacerUltimoComando() {

        if (!historial.isEmpty()) {
            Command comando = historial.pop();
            comando.deshacer();
        }
    }

    public boolean tieneComandos() {
        return !historial.isEmpty();
    }
}