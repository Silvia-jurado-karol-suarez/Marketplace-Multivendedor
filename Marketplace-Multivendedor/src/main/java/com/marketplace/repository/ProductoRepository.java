package com.marketplace.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.marketplace.model.Producto;

public interface ProductoRepository extends MongoRepository<Producto, String> {

    List<Producto> findByCategoria(String categoria);

    List<Producto> findByVendedor_Id(String vendedorId);
}