package com.ecommerce.usuario_service.exception;

// Esto es un tipo de error que puede ocurrir en el servicio. 
public class UsuarioNoEncontradoException extends RuntimeException {
    public UsuarioNoEncontradoException(Long id) {
        super("Usuario con id " + id + " no encontrado");
    }
}
