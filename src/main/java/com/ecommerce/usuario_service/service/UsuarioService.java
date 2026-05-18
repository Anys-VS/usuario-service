package com.ecommerce.usuario_service.service;

import com.ecommerce.usuario_service.dto.UsuarioRequest;
import com.ecommerce.usuario_service.dto.UsuarioResponse;
import com.ecommerce.usuario_service.model.Usuario;
import com.ecommerce.usuario_service.repository.UsuarioRepository;

import com.ecommerce.usuario_service.exception.EmailYaRegistradoException;
import com.ecommerce.usuario_service.exception.UsuarioNoEncontradoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service

public class UsuarioService {
    private static final Logger logger = LoggerFactory.getLogger(UsuarioService.class);

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // Listar todos los usuarios
    public List<UsuarioResponse> listar() {
        logger.info("Listando todos los usuarios");
        return usuarioRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

        // Crear usuario
    public UsuarioResponse crearUsuario(UsuarioRequest request) {
        logger.info("Creando usuario con email: {}", request.getEmail());

        // Regla de negocio: el email no puede estar ya registrado
        if (usuarioRepository.findByEmail(request.getEmail()).isPresent()) {
            logger.warn("Intento de registro con email ya existente: {}", request.getEmail());
            throw new EmailYaRegistradoException("El email " + request.getEmail() + " ya está registrado");
        }

        Usuario usuario = toEntity(request);
        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        logger.info("Usuario creado con id: {}", usuarioGuardado.getId());
        return toResponse(usuarioGuardado);
    }

    // Obtener usuario por ID

    public UsuarioResponse obtenerPorId(Long id) {
        logger.info("Buscando usuario con id: {}", id);
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNoEncontradoException(id));
        logger.info("Usuario encontrado: {}", usuario.getEmail());
        return toResponse(usuario);
    }

    // Actualizar usuario

    public UsuarioResponse actualizar(Long id, UsuarioRequest request) {
        logger.info("Actualizando usuario con id: {}", id);
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNoEncontradoException(id));

        usuario.setNombre(request.getNombre());
        usuario.setApellido(request.getApellido());
        usuario.setEmail(request.getEmail());
        usuario.setDireccion(request.getDireccion());
        usuario.setTelefono(request.getTelefono());
        usuario.setContrasena(request.getContrasena());
        usuario.setRol(request.getRol() != null ? request.getRol() : usuario.getRol());

        Usuario actualizado = usuarioRepository.save(usuario);
        logger.info("Usuario actualizado: {}", actualizado.getEmail());
        return toResponse(actualizado);
    }

    // Eliminar usuario

    public void eliminar(Long id) {
        logger.info("Eliminando usuario con id: {}", id);
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNoEncontradoException(id));
        usuarioRepository.delete(usuario);
        logger.info("Usuario eliminado con id: {}", id);
    }


    private Usuario toEntity(UsuarioRequest request) {
        return Usuario.builder()
                .nombre(request.getNombre())
                .apellido(request.getApellido())
                .email(request.getEmail())
                .direccion(request.getDireccion())
                .telefono(request.getTelefono())
                .contrasena(request.getContrasena())
                .rol(request.getRol() != null ? request.getRol() : "USUARIO")
                .build();
    }


    // Buscar usuario por email (usado por el microservicio de autenticación)
    public UsuarioResponse buscarPorEmail(String email) {
        logger.info("Buscando usuario por email: {}", email);
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsuarioNoEncontradoException(0L));
        return toResponse(usuario);
    }

    private UsuarioResponse toResponse(Usuario usuario) {
        return UsuarioResponse.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .apellido(usuario.getApellido())
                .email(usuario.getEmail())
                .contrasena(usuario.getContrasena())
                .rol(usuario.getRol())
                .build();
    }
}
