package com.marketplace.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.marketplace.model.Pedido;

public interface PedidoRepository extends MongoRepository<Pedido, String> {

}