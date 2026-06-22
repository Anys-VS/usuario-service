package com.ecommerce.usuario_service.service;

import com.ecommerce.usuario_service.dto.UsuarioRequest;
import com.ecommerce.usuario_service.dto.UsuarioResponse;
import com.ecommerce.usuario_service.model.Usuario;
import com.ecommerce.usuario_service.repository.UsuarioRepository;
import com.ecommerce.usuario_service.exception.UsuarioNoEncontradoException;
import com.ecommerce.usuario_service.exception.EmailYaRegistradoException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    // LISTAR USUARIOS
    @Test
    void listarDebeRetornarListaUsuarios() {

        when(usuarioRepository.findAll())
                .thenReturn(List.of(new Usuario()));

        List<UsuarioResponse> result = usuarioService.listar();

        assertFalse(result.isEmpty());
        verify(usuarioRepository).findAll();
    }

    // CREAR USUARIO OK
    @Test
    void crearUsuarioDebeGuardarUsuario() {

        UsuarioRequest request = new UsuarioRequest();
        request.setNombre("Juan");
        request.setEmail("juan@test.com");
        request.setContrasena("123456");

        when(usuarioRepository.findByEmail("juan@test.com"))
                .thenReturn(Optional.empty());

        when(usuarioRepository.save(any(Usuario.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        UsuarioResponse response = usuarioService.crearUsuario(request);

        assertEquals("Juan", response.getNombre());
        verify(usuarioRepository).save(any(Usuario.class));
    }

    // EMAIL YA EXISTE
    @Test
    void crearUsuarioDebeLanzarExcepcionSiEmailExiste() {

        UsuarioRequest request = new UsuarioRequest();
        request.setEmail("existente@test.com");

        when(usuarioRepository.findByEmail("existente@test.com"))
                .thenReturn(Optional.of(new Usuario()));

        assertThrows(EmailYaRegistradoException.class,
                () -> usuarioService.crearUsuario(request));

        verify(usuarioRepository, never()).save(any());
    }

    // BUSCAR POR ID OK
    @Test
    void obtenerPorIdDebeRetornarUsuario() {

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("test@test.com");

        when(usuarioRepository.findById(1L))
                .thenReturn(Optional.of(usuario));

        UsuarioResponse response = usuarioService.obtenerPorId(1L);

        assertEquals(1L, response.getId());
        verify(usuarioRepository).findById(1L);
    }

    // ID NO EXISTE
    @Test
    void obtenerPorIdDebeLanzarExcepcionSiNoExiste() {

        when(usuarioRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(UsuarioNoEncontradoException.class,
                () -> usuarioService.obtenerPorId(99L));
    }

    // ELIMINAR USUARIO
    @Test
    void eliminarDebeBorrarUsuario() {

        Usuario usuario = new Usuario();
        usuario.setId(1L);

        when(usuarioRepository.findById(1L))
                .thenReturn(Optional.of(usuario));

        usuarioService.eliminar(1L);

        verify(usuarioRepository).delete(usuario);
    }

    // BUSCAR POR EMAIL
    @Test
    void buscarPorEmailDebeRetornarUsuario() {

        Usuario usuario = new Usuario();
        usuario.setEmail("mail@test.com");

        when(usuarioRepository.findByEmail("mail@test.com"))
                .thenReturn(Optional.of(usuario));

        UsuarioResponse response = usuarioService.buscarPorEmail("mail@test.com");

        assertEquals("mail@test.com", response.getEmail());
    }
}