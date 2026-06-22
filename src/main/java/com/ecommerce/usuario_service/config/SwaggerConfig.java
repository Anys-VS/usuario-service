package com.ecommerce.usuario_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("Proyecto Libreria Ecommerce 2026")
                        .version("1.0")
                        .description("API para la gestión de usuarios. Permite crear, listar, actualizar y eliminar usuarios. Requiere autenticación mediante JWT con roles USUARIO o ADMIN."));
    }  
}