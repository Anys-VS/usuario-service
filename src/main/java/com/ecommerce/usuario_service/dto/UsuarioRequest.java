package com.ecommerce.usuario_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
public class UsuarioRequest {

  @Schema(
    description = "Nombre del usuario",
    example = "Ana"
)
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @Schema(
    description = "Apellido del usuario",
    example = "Perez"
)
    private String apellido;

    @Schema(
    description = "Correo electrónico",
    example = "ejemplo@gmail.com"
)
    @Email(message = "Debe ser un email valido")
    @NotBlank(message="El email es obligatorio")
    private String email;

    @Schema(
    description = "Dirección del usuario",
    example = "Av. Siempre Viva 1234, Valparaiso"
)
    @Size(min=10, message="La direccion debe tener almenos 10 caracteres")
    private String direccion;

    @Schema(
    description = "Teléfono de contacto",
    example = "912345678"
    )
    //evaluar obligatoriedad de telefono quizas para recuperar contraseña
    private String telefono;

    @Schema(
    description = "Contraseña del usuario",
    example = "Password123"
)
    @NotBlank(message = "La contrasena es obligatoria")
    private String contrasena;

    @Schema(
    description = "Rol del usuario",
    example = "USUARIO"
)
    private String rol;

}
