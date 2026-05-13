package com.ecommerce.usuario_service.exception;

public class UsuarioNoEncontradoException extends RuntimeException {
    public UsuarioNoEncontradoException(Long id) {
        super("Usuario con id " + id + " no encontrado");
    }
}
