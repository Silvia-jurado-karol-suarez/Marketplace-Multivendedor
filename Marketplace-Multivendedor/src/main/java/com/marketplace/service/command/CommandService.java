package com.marketplace.service.command;

import org.springframework.stereotype.Service;

import com.marketplace.command.ActualizarStockCommand;
import com.marketplace.command.CommandInvoker;
import com.marketplace.repository.ProductoRepository;

@Service
public class CommandService {

    private final ProductoRepository productoRepository;
    private final CommandInvoker invoker;

    public CommandService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
        this.invoker = new CommandInvoker();
    }

    public void actualizarStock(String productoId, int nuevoStock) {

        ActualizarStockCommand comando =
                new ActualizarStockCommand(
                        productoRepository,
                        productoId,
                        nuevoStock
                );

        invoker.ejecutarComando(comando);
    }

    public void deshacerUltimaAccion() {
        invoker.deshacerUltimoComando();
    }

    public boolean hayAccionesParaDeshacer() {
        return invoker.tieneComandos();
    }
}