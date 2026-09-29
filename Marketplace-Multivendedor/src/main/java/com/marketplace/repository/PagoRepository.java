package com.marketplace.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.marketplace.model.PagoRegistro;

public interface PagoRepository
        extends MongoRepository<PagoRegistro, String> {
}