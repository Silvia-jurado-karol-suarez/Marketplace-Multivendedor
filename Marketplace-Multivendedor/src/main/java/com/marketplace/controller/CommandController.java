
package com.marketplace.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.marketplace.service.command.CommandService;

@RestController
@RequestMapping("/api/command")
@CrossOrigin
public class CommandController {

    private final CommandService commandService;

    public CommandController(CommandService commandService) {
        this.commandService = commandService;
    }

    // Actualizar el stock de un producto
    @PutMapping("/actualizar-stock/{id}")
    public ResponseEntity<String> actualizarStock(
            @PathVariable("id") String id,
            @RequestParam("stock") int stock) {

        try {
            if (stock < 0) {
                return ResponseEntity.badRequest()
                        .body("El stock no puede ser negativo.");
            }

            commandService.actualizarStock(id, stock);

            return ResponseEntity.ok(
                    "Stock actualizado correctamente."
            );

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body("Error al actualizar: " + e.getMessage());
        }
    }

    // Deshacer la última operación
    @PostMapping("/deshacer")
    public ResponseEntity<String> deshacer() {

        try {
            if (!commandService.hayAccionesParaDeshacer()) {
                return ResponseEntity.badRequest()
                        .body("No hay acciones para deshacer.");
            }

            commandService.deshacerUltimaAccion();

            return ResponseEntity.ok(
                    "Última actualización deshecha correctamente."
            );

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body("Error al deshacer: " + e.getMessage());
        }
    }

    // Consultar si hay acciones para deshacer
    @GetMapping("/estado")
    public ResponseEntity<Boolean> consultarEstado() {

        return ResponseEntity.ok(
                commandService.hayAccionesParaDeshacer()
        );
    }
}
