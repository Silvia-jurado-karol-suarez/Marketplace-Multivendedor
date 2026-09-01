package com.marketplace.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.marketplace.model.Producto;

public interface ProductoRepository extends MongoRepository<Producto, String> {

}