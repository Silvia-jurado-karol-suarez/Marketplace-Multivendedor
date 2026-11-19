package com.marketplace.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marketplace.repository.ProductoRepository;
import com.marketplace.repository.PedidoRepository;
import com.marketplace.repository.VendedorRepository;
import com.marketplace.visitor.VisitanteReporte;
import com.marketplace.visitor.VisitorService;

@RestController
@RequestMapping("/api/visitor")
public class VisitorController {

    private final VisitorService visitorService;
    private final ProductoRepository productoRepository;
    private final PedidoRepository pedidoRepository;
    private final VendedorRepository vendedorRepository;

    public VisitorController(
            VisitorService visitorService,
            ProductoRepository productoRepository,
            PedidoRepository pedidoRepository,
            VendedorRepository vendedorRepository) {

        this.visitorService = visitorService;
        this.productoRepository = productoRepository;
        this.pedidoRepository = pedidoRepository;
        this.vendedorRepository = vendedorRepository;
    }

    // Generar reporte general del Marketplace
    @GetMapping("/reporte")
    public VisitanteReporte generarReporte() {

        return visitorService.generarReporte(
                productoRepository.findAll(),
                pedidoRepository.findAll(),
                vendedorRepository.findAll()
        );
    }
}