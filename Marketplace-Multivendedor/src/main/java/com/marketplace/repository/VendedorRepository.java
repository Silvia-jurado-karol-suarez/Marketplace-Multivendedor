package com.marketplace.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.marketplace.model.Vendedor;

public interface VendedorRepository
        extends MongoRepository<Vendedor, String> {
}