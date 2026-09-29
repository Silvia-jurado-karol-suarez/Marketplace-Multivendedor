package com.marketplace.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.marketplace.model.Usuario;

public interface UsuarioRepository extends MongoRepository<Usuario, String> {

	 Optional<Usuario> findByCorreo(String correo);
}
