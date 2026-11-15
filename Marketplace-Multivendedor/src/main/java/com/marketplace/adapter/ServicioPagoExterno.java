
package com.marketplace.adapter;

public class ServicioPagoExterno {

    public String realizarTransaccion(
            double valor,
            String metodoPago) {

        return "Transacción simulada mediante "
                + metodoPago
                + " por valor de $"
                + valor
                + " COP";
    }
}
