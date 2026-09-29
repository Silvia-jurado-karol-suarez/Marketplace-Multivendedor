package com.marketplace.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.marketplace.model.Usuario;
import com.marketplace.repository.UsuarioRepository;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    private final UsuarioRepository usuarioRepository;

    public AuthController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }


    // REGISTRO
    @PostMapping("/registro")
    public ResponseEntity<String> registrar(
            @RequestBody Usuario usuario) {

        Optional<Usuario> usuarioExistente =
                usuarioRepository.findByCorreo(
                        usuario.getCorreo()
                );

        if (usuarioExistente.isPresent()) {

            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("El correo ya está registrado.");
        }


        usuarioRepository.save(usuario);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Usuario registrado correctamente.");
    }


    // INICIO DE SESIÓN
    @PostMapping("/login")
    public ResponseEntity<String> iniciarSesion(
            @RequestBody Usuario usuario) {

        Optional<Usuario> usuarioEncontrado =
                usuarioRepository.findByCorreo(
                        usuario.getCorreo()
                );


        if (usuarioEncontrado.isEmpty()) {

            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Correo o contraseña incorrectos.");
        }


        Usuario usuarioBD =
                usuarioEncontrado.get();


        if (!usuarioBD.getPassword()
                .equals(usuario.getPassword())) {

            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Correo o contraseña incorrectos.");
        }


        return ResponseEntity
                .ok("Inicio de sesión correcto.");
    }
}