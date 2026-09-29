package com.marketplace.adapter;

public class PasarelaExternaAdapter implements ProcesadorPago {

    private PasarelaExterna pasarelaExterna;

    public PasarelaExternaAdapter(PasarelaExterna pasarelaExterna) {

        this.pasarelaExterna = pasarelaExterna;
    }

    @Override
    public void pagar(double monto) {

        pasarelaExterna.realizarTransaccion(monto);
    }
}