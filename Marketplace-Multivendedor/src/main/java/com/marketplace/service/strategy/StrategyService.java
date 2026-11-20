
package com.marketplace.service.strategy;

import org.springframework.stereotype.Service;

import com.marketplace.strategy.DescuentoContexto;
import com.marketplace.strategy.DescuentoFijo;
import com.marketplace.strategy.DescuentoPorcentaje;
import com.marketplace.strategy.EstrategiaDescuento;
import com.marketplace.strategy.ResultadoDescuento;
import com.marketplace.strategy.SinDescuento;

@Service
public class StrategyService {

    public ResultadoDescuento calcularDescuento(
            String tipo, double subtotal, double valor) {

        EstrategiaDescuento estrategia;

        switch (tipo.toLowerCase()) {
            case "porcentaje":
                estrategia = new DescuentoPorcentaje(valor);
                break;

            case "fijo":
                estrategia = new DescuentoFijo(valor);
                break;

            case "ninguno":
                estrategia = new SinDescuento();
                break;

            default:
                throw new IllegalArgumentException(
                        "Tipo de descuento no válido."
                );
        }

        DescuentoContexto contexto =
                new DescuentoContexto(estrategia);

        double descuento = contexto.calcular(subtotal);
        double total = contexto.calcularTotal(subtotal);

        return new ResultadoDescuento(
                subtotal,
                descuento,
                total,
                tipo
        );
    }
}
