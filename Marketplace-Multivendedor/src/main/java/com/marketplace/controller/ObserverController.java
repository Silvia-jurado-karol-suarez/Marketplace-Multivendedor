
package com.marketplace.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marketplace.observer.ObserverService;
import com.marketplace.observer.RegistroNotificaciones;

@RestController
@RequestMapping("/api/observer")
@CrossOrigin
public class ObserverController {

    private final ObserverService observerService;

    public ObserverController(ObserverService observerService) {
        this.observerService = observerService;
    }

    @GetMapping("/observadores")
    public ResponseEntity<List<String>> obtenerObservadores() {
        return ResponseEntity.ok(
                observerService.obtenerObservadores()
        );
    }

    @GetMapping("/cantidad")
    public ResponseEntity<Integer> obtenerCantidad() {
        return ResponseEntity.ok(
                observerService.obtenerCantidadObservadores()
        );
    }

    @GetMapping("/eventos")
    public ResponseEntity<List<RegistroNotificaciones.NotificacionEvento>>
            obtenerEventos() {
        return ResponseEntity.ok(
                observerService.obtenerEventos()
        );
    }

    @DeleteMapping("/eventos")
    public ResponseEntity<String> limpiarEventos() {
        observerService.limpiarEventos();

        return ResponseEntity.ok(
                "Historial de notificaciones limpiado correctamente."
        );
    }
}
