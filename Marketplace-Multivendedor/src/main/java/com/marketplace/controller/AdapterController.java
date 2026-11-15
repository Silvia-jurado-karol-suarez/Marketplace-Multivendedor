
package com.marketplace.controller;

import org.springframework.web.bind.annotation.*;
import com.marketplace.adapter.PasarelaExterna;
import com.marketplace.adapter.PasarelaExternaAdapter;
import com.marketplace.adapter.ProcesadorPago;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/adapter")
public class AdapterController {

    @PostMapping("/pagar")
    public Map<String, Object> pagar(@RequestParam double monto) {

        Map<String, Object> respuesta = new LinkedHashMap<>();

        if (monto <= 0 || !Double.isFinite(monto)) {
            respuesta.put("estado", "Error");
            respuesta.put("mensaje", "Ingresa un monto válido.");
            return respuesta;
        }

        PasarelaExterna pasarela = new PasarelaExterna();

        ProcesadorPago procesador =
                new PasarelaExternaAdapter(pasarela);

        procesador.pagar(monto);

        respuesta.put("patron", "Adapter");
        respuesta.put("estado", "Procesamiento exitoso");
        respuesta.put("monto", monto);
        respuesta.put("moneda", "COP");
        respuesta.put("adaptador", "PasarelaExternaAdapter");
        respuesta.put("mensaje",
                "La solicitud del Marketplace fue adaptada "
                + "correctamente a la pasarela externa.");

        return respuesta;
    }
}
