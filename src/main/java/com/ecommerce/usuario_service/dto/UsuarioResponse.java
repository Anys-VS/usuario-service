package com.ecommerce.usuario_service.dto;

import lombok.Builder;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Respuesta con información de usuario")

@Data
@Builder
public class UsuarioResponse {

  @Schema(example = "1")
private Long id;

@Schema(example = "Juan")
private String nombre;

@Schema(example = "Perez")
private String apellido;

@Schema(example = "juan@gmail.com")
private String email;

@Schema(example = "USUARIO")
private String rol;
}
