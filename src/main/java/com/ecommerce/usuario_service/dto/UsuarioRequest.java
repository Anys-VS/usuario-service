package com.ecommerce.usuario_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UsuarioRequest {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    private String apellido;

    @Email(message = "Debe ser un email valido")
    @NotBlank(message="El email es obligatorio")
    private String email;

    @Size(min=10, message="La direccion debe tener almenos 10 caracteres")
    private String direccion;

    //evaluar obligatoriedad de telefono quizas para recuperar contraseña
    private String telefono;

    private String contrasena;

}
