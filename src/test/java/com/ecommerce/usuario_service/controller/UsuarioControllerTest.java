package com.ecommerce.usuario_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.ecommerce.usuario_service.dto.UsuarioRequest;
import com.ecommerce.usuario_service.dto.UsuarioResponse;
import com.ecommerce.usuario_service.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(UsuarioController.class)
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private UsuarioService usuarioService;

    //Listar usuarios
    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("GET /usuarios debe retornar 200 y lista de usuarios")
    void listarDebeRetornarOkYLista() throws Exception {

        UsuarioResponse usuario = UsuarioResponse.builder()
                .id(1L)
                .nombre("Juan")
                .apellido("Perez")
                .email("juan@correo.cl")
                .rol("USUARIO")
                .build();

        when(usuarioService.listar()).thenReturn(List.of(usuario));

        mockMvc.perform(get("/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].nombre").value("Juan"));

        verify(usuarioService).listar();
    }

    //Buscar a usuario por id 
    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("GET /usuarios/{id} debe retornar 200")
    void buscarPorIdDebeRetornarOk() throws Exception {

        UsuarioResponse usuario = UsuarioResponse.builder()
                .id(99L)
                .nombre("Pedro")
                .apellido("Rojas")
                .email("pedro@test.com")
                .rol("USUARIO")
                .build();

        when(usuarioService.obtenerPorId(99L)).thenReturn(usuario);

        mockMvc.perform(get("/usuarios/99"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Pedro"));

        verify(usuarioService).obtenerPorId(99L);
    }
  
  @Test
    @DisplayName("POST /usuarios/registro debe crear usuario")
    void guardarDebeRetornarOk() throws Exception {

    UsuarioRequest request = new UsuarioRequest();
    request.setNombre("Pedro");
    request.setApellido("Rojas");
    request.setEmail("pedro@test.com");
    request.setContrasena("123456");
    request.setDireccion("Av Principal 123");
    request.setTelefono("123456789");
    request.setRol("USUARIO");

    UsuarioResponse response = UsuarioResponse.builder()
            .id(1L)
            .nombre("Pedro")
            .apellido("Rojas")
            .email("pedro@test.com")
            .rol("USUARIO")
            .build();

    when(usuarioService.crearUsuario(any())).thenReturn(response);

    mockMvc.perform(post("/usuarios/registro")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(content().string("Usuario_registrado_correctamente"));
    verify(usuarioService).crearUsuario(any());
}

    @Test
    void listarDebeRetornarListaVacia() throws Exception {

        when(usuarioService.listar()).thenReturn(List.of());

        mockMvc.perform(get("/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        verify(usuarioService).listar();
    }

    @Test
    void eliminarDebeRetornarNoContent() throws Exception {

        doNothing().when(usuarioService).eliminar(1L);

        mockMvc.perform(delete("/usuarios/1"))
                .andExpect(status().isNoContent());

        verify(usuarioService).eliminar(1L);
    }

    @Test
    void buscarPorEmailDebeRetornarUsuario() throws Exception {

        UsuarioResponse response = UsuarioResponse.builder()
                .id(1L)
                .email("test@test.com")
                .build();

        when(usuarioService.buscarPorEmail("test@test.com"))
                .thenReturn(response);

        mockMvc.perform(get("/usuarios/email/test@test.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@test.com"));

        verify(usuarioService).buscarPorEmail("test@test.com");
    }

    @Test
    void crearUsuarioConEmailInvalidoDebeRetornar400() throws Exception {

        UsuarioRequest request = new UsuarioRequest();
        request.setEmail("email_invalido");

        mockMvc.perform(post("/usuarios/registro")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void crearUsuarioSinNombreDebeRetornar400() throws Exception {

        UsuarioRequest request = new UsuarioRequest();

        mockMvc.perform(post("/usuarios/registro")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
    
}