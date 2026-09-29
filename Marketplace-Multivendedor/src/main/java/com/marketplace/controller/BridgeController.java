package com.marketplace.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.marketplace.bridge.CanalNotificacion;
import com.marketplace.bridge.EmailNotificacion;
import com.marketplace.bridge.SMSNotificacion;
import com.marketplace.bridge.Notificacion;
import com.marketplace.bridge.NotificacionPedido;

@RestController
@RequestMapping("/bridge")
public class BridgeController {

    @GetMapping("/notificar")
    public String notificar(
            @RequestParam String canal) {

        CanalNotificacion canalSeleccionado;


        if (canal.equalsIgnoreCase("email")) {

            canalSeleccionado =
                    new EmailNotificacion();

        } else if (canal.equalsIgnoreCase("sms")) {

            canalSeleccionado =
                    new SMSNotificacion();

        } else {

            return "Canal no disponible.";
        }


        Notificacion notificacion =
                new NotificacionPedido(
                        canalSeleccionado
                );


        notificacion.enviar(
                "El pedido P001 fue creado correctamente."
        );


        return """
                <strong>PATRÓN BRIDGE</strong>
                <br><br>
                Tipo: Notificación de pedido
                <br>
                Canal: """ + canal +
                "<br>Estado: Notificación enviada.";
    }
}