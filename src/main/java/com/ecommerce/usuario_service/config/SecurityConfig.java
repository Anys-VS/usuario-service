package com.ecommerce.usuario_service.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig{

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                    // Swagger
            .requestMatchers(
                "/v3/api-docs/**",
                "/swagger-ui/**",
                "/swagger-ui.html",
                "/docs/**",
                "/auth/**"
            ).permitAll()

            .requestMatchers(HttpMethod.POST, "/usuarios/registro").permitAll()
            .requestMatchers(HttpMethod.GET, "/usuarios").hasRole("ADMIN")
            .requestMatchers(HttpMethod.GET, "/usuarios/*").hasRole("ADMIN")
            .requestMatchers(HttpMethod.PUT, "/usuarios/*").hasRole("ADMIN")
            .requestMatchers(HttpMethod.GET, "/usuarios/email/**").permitAll()
            .requestMatchers(HttpMethod.GET, "/usuarios/mis-datos").hasAnyRole("USUARIO","ADMIN")
            .requestMatchers(HttpMethod.PUT, "/usuarios/mis-datos").hasAnyRole("USUARIO","ADMIN")
            //* PENDIENTE ELIMINACION POR USUARIO */
            .requestMatchers(HttpMethod.DELETE, "/usuarios/mis-datos").hasAnyRole("USUARIO","ADMIN")
            .anyRequest().authenticated()
            );
        return http.build();
    }
}