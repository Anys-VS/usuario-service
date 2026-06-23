package com.ecommerce.usuario_service.controller;

import java.util.List;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ecommerce.usuario_service.assembler.UsuarioModelAssembler;
import com.ecommerce.usuario_service.dto.UsuarioRequest;
import com.ecommerce.usuario_service.dto.UsuarioResponse;
import com.ecommerce.usuario_service.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/v2/usuarios")
@Tag(
    name = "Usuarios V2",
    description = "API de gestión de usuarios con soporte HATEOAS"
)
public class UsuarioControllerV2 {

    private final UsuarioService usuarioService;
    private final UsuarioModelAssembler assembler;

    public UsuarioControllerV2(
            UsuarioService usuarioService,
            UsuarioModelAssembler assembler) {

        this.usuarioService = usuarioService;
        this.assembler = assembler;
    }

    @PostMapping("/registro")
    @Operation(
        summary = "Registrar usuario",
        description = "Crea un nuevo usuario y retorna el recurso con enlaces HATEOAS."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario registrado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "409", description = "El correo ya existe")
    })
    public ResponseEntity<EntityModel<UsuarioResponse>> registrar(
            @Valid @RequestBody UsuarioRequest request) {

        usuarioService.crearUsuario(request);

        UsuarioResponse usuario =
                usuarioService.buscarPorEmail(request.getEmail());

        return ResponseEntity.ok(assembler.toModel(usuario));
    }

    @GetMapping
    @Operation(
        summary = "Listar usuarios",
        description = "Obtiene todos los usuarios registrados incluyendo enlaces HATEOAS."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente")
    })
    public ResponseEntity<CollectionModel<EntityModel<UsuarioResponse>>> listar() {

        List<EntityModel<UsuarioResponse>> usuarios =
                usuarioService.listar()
                        .stream()
                        .map(assembler::toModel)
                        .toList();

        return ResponseEntity.ok(CollectionModel.of(usuarios));
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Obtener usuario por ID",
        description = "Retorna un usuario específico junto con sus enlaces HATEOAS."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<EntityModel<UsuarioResponse>> obtener(
            @Parameter(description = "ID del usuario", required = true)
            @PathVariable Long id) {

        return ResponseEntity.ok(
                assembler.toModel(usuarioService.obtenerPorId(id)));
    }

    @GetMapping("/email/{email}")
    @Operation(
        summary = "Buscar usuario por email",
        description = "Busca un usuario utilizando su correo electrónico y retorna enlaces HATEOAS."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<EntityModel<UsuarioResponse>> obtenerPorEmail(
            @Parameter(description = "Correo electrónico del usuario", required = true)
            @PathVariable String email) {

        return ResponseEntity.ok(
                assembler.toModel(usuarioService.buscarPorEmail(email)));
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Actualizar usuario",
        description = "Actualiza la información de un usuario existente y retorna el recurso actualizado con enlaces HATEOAS."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario actualizado"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<EntityModel<UsuarioResponse>> actualizar(
            @Parameter(description = "ID del usuario", required = true)
            @PathVariable Long id,
            @Valid @RequestBody UsuarioRequest request) {

        return ResponseEntity.ok(
                assembler.toModel(usuarioService.actualizar(id, request)));
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Eliminar usuario",
        description = "Elimina un usuario del sistema por su identificador."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Usuario eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del usuario", required = true)
            @PathVariable Long id) {

        usuarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}