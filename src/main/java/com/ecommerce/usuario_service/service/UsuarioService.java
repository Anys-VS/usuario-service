package com.ecommerce.usuario_service.service;
import com.ecommerce.usuario_service.dto.UsuarioRequest;
import com.ecommerce.usuario_service.dto.UsuarioResponse;
import com.ecommerce.usuario_service.model.Usuario;
import com.ecommerce.usuario_service.repository.UsuarioRepository;
import com.ecommerce.usuario_service.exception.EmailYaRegistradoException;
import com.ecommerce.usuario_service.exception.UsuarioNoEncontradoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class UsuarioService {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioService.class);
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;


    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    //** LISTAR TODOS LOS USUARIOS (ADMIN) */
    public List<UsuarioResponse> listar() {
        logger.info("Listando todos los usuarios");
        return usuarioRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    //** CREA AL USUARIO */
    public UsuarioResponse crearUsuario(UsuarioRequest request) {
        logger.info("Creando usuario con email: {}", request.getEmail());
        if (usuarioRepository.findByEmail(request.getEmail()).isPresent()) {
            logger.warn("Intento de registro con email ya existente: {}", request.getEmail());
            throw new EmailYaRegistradoException("El email " + request.getEmail() + " ya está registrado");
        }

        Usuario usuario = toEntity(request);
        usuario.setContrasena(passwordEncoder.encode(request.getContrasena()));
        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        logger.info("Usuario creado con id: {}", usuarioGuardado.getId());
        return toResponse(usuarioGuardado);
    }

    //** OBTIENE USUARIO POR ID (ADMIN) */
    public UsuarioResponse obtenerPorId(Long id) {
        logger.info("Buscando usuario con id: {}", id);
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNoEncontradoException(id));
        logger.info("Usuario encontrado: {}", usuario.getEmail());
        return toResponse(usuario);
    }

    //** ACTUALIZAR USUARIO POR ID (ADMIN) */
    public UsuarioResponse actualizar(Long id, UsuarioRequest request) {
        logger.info("Actualizando usuario con id: {}", id);
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNoEncontradoException(id));
        usuario.setNombre(request.getNombre());
        usuario.setApellido(request.getApellido());
        usuario.setEmail(request.getEmail());
        usuario.setDireccion(request.getDireccion());
        usuario.setTelefono(request.getTelefono());
        usuario.setRol(request.getRol() != null ? request.getRol() : usuario.getRol());
        if (request.getContrasena() != null && !request.getContrasena().isBlank()) {
            usuario.setContrasena(passwordEncoder.encode(request.getContrasena()));
        }
        Usuario actualizado = usuarioRepository.save(usuario);
        logger.info("Usuario actualizado: {}", actualizado.getEmail());
        return toResponse(actualizado);
    }

    //** ELIMINA USUARIO POR ID (ADMIN) */
    public void eliminar(Long id) {
        logger.info("Eliminando usuario con id: {}", id);
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNoEncontradoException(id));
        usuarioRepository.delete(usuario);
        logger.info("Usuario eliminado con id: {}", id);
    }

    //** BUSCA USUARIO POR EMAIL (ADMIN) */
    public UsuarioResponse buscarPorEmail(String email) {
        logger.info("Buscando usuario por email: {}", email);
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsuarioNoEncontradoException(0L));
        return toResponse(usuario);
    }

    //** MAPEA EL USUARIO PARA DTO, convierte un usuario(base de datos)en un UsuarioResponse lo que se manda por API */
    private UsuarioResponse toResponse(Usuario usuario) {
        return UsuarioResponse.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .apellido(usuario.getApellido())
                .email(usuario.getEmail())
                .rol(usuario.getRol())
                .build();
    }

        //** TRAE LOS DATOS DE LA REQUEST */
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

}
