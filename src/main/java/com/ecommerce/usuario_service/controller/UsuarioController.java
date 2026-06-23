package com.ecommerce.usuario_service.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.usuario_service.dto.UsuarioRequest;
import com.ecommerce.usuario_service.dto.UsuarioResponse;

import com.ecommerce.usuario_service.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;



@RestController
@RequestMapping("/usuarios")
@Tag(name = "Gestion de Usuarios",description = "Microservicio encargado de operaciones CRUD y consultas de usuarios")
public class UsuarioController {


    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    /**
     * Registro de usuario (POST /usuarios/registro)
     */
    @PostMapping("/registro")
    @Operation(summary = "Registrar usuario",description = "Crea un nuevo usuario en el sistema validando email único y encriptando la contraseña")
    public ResponseEntity<String> registrar(@Valid @RequestBody UsuarioRequest request) {
        usuarioService.crearUsuario(request);
        return ResponseEntity.ok("Usuario_registrado_correctamente");
    }

    /**
     * Listar todos los usuarios
     */

    @GetMapping
    @Operation(
    summary = "Listar usuarios",
    description = "Obtiene la lista completa de usuarios registrados en el sistema"
)
    public ResponseEntity<List<UsuarioResponse>> listar() {
        return ResponseEntity.ok(usuarioService.listar());
    }

    /**
     * Obtener usuario por ID
     */
    @GetMapping("/{id}")
    @Operation(summary = "Obtener usuario por ID",description = "Retorna la información de un usuario específico según su identificador. Requiere rol ADMIN")
    //TODO: @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<UsuarioResponse> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.obtenerPorId(id));
    }

    /**
     * Actualizar usuario
     */
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar usuario por ID",
    description = "Modifica los datos de un usuario existente, incluyendo nombre, email, dirección y rol")
    public ResponseEntity<UsuarioResponse> actualizar(@PathVariable Long id, @Valid @RequestBody UsuarioRequest request) {
        return ResponseEntity.ok(usuarioService.actualizar(id, request));
    }

    /**
     * Buscar usuario por email (usado internamente por el microservicio de autenticación)
     */
    @GetMapping("/email/{email}")
    @Operation(
    summary = "Buscar usuario por email",
    description = "Obtiene la información de un usuario utilizando su correo electrónico"
)
    public ResponseEntity<UsuarioResponse> obtenerPorEmail(@PathVariable String email) {
        return ResponseEntity.ok(usuarioService.buscarPorEmail(email));
    }

    /**
     * Eliminar usuario
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar usuario",description = "Elimina un usuario del sistema por su ID. Operación irreversible")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        usuarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}
