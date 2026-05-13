package com.ecommerce.usuario_service.repository;


import com.ecommerce.usuario_service.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

        java.util.Optional<Usuario> findByEmail(String email);

    
}
