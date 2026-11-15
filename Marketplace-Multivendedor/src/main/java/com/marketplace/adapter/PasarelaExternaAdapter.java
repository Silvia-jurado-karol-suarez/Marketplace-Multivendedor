
package com.marketplace.adapter;

public class PasarelaExternaAdapter implements ProcesadorPago {

    private PasarelaExterna pasarelaExterna;

    public PasarelaExternaAdapter(PasarelaExterna pasarelaExterna) {
        this.pasarelaExterna = pasarelaExterna;
    }

    @Override
    public void pagar(double monto) {

        System.out.println("Adapter: convirtiendo solicitud de pago...");

        pasarelaExterna.realizarTransaccion(monto);
    }
}
