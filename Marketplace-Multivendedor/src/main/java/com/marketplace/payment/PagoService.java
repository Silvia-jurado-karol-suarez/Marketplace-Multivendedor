package com.marketplace.payment;

import org.springframework.stereotype.Service;



@Service
public class PagoService {

    public void procesarPago(PagoFactory factory, double monto) {

        factory.procesar(monto);
    }
}