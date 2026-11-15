
package com.marketplace.controller;

import org.springframework.web.bind.annotation.*;

import com.marketplace.bridge.CanalNotificacion;
import com.marketplace.bridge.EmailNotificacion;
import com.marketplace.bridge.SMSNotificacion;
import com.marketplace.bridge.Notificacion;
import com.marketplace.bridge.NotificacionPedido;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/bridge")
public class BridgeController {

    @PostMapping("/notificar")
    public Map<String, Object> notificar(
            @RequestParam String canal) {

        Map<String, Object> respuesta = new LinkedHashMap<>();

        CanalNotificacion canalSeleccionado;

        if (canal.equalsIgnoreCase("email")) {
            canalSeleccionado = new EmailNotificacion();

        } else if (canal.equalsIgnoreCase("sms")) {
            canalSeleccionado = new SMSNotificacion();

        } else {
            respuesta.put("estado", "Error");
            respuesta.put("mensaje", "Canal no disponible.");
            return respuesta;
        }

        Notificacion notificacion =
                new NotificacionPedido(canalSeleccionado);

        String mensaje = notificacion.enviar(
                "El pedido P001 fue creado correctamente."
        );

        respuesta.put("patron", "Bridge");
        respuesta.put("tipo", "Notificación de pedido");
        respuesta.put("canal", canal);
        respuesta.put("estado", "Notificación enviada");
        respuesta.put("mensaje", mensaje);

        return respuesta;
    }
}
