package com.ecommerce.usuario_service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsuarioResponse {

    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private String contrasena;
    private String rol;
}
