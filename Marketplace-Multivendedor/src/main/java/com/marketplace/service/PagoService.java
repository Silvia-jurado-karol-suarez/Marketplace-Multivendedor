package com.marketplace.service;

import org.springframework.stereotype.Service;

import com.marketplace.model.PagoRegistro;
import com.marketplace.payment.Pago;
import com.marketplace.payment.PagoFactory;
import com.marketplace.repository.PagoRepository;

@Service
public class PagoService {

    private final PagoRepository pagoRepository;

    public PagoService(PagoRepository pagoRepository) {
        this.pagoRepository = pagoRepository;
    }

    public PagoRegistro procesarPago(
            PagoFactory factory,
            double monto,
            String pedidoId) {

        Pago pago = factory.crearPago();

        pago.procesarPago(monto);

        PagoRegistro registro = new PagoRegistro(
                null,
                pedidoId,
                pago.getMetodo(),
                monto,
                "APROBADO"
        );

        return pagoRepository.save(registro);
    }
}