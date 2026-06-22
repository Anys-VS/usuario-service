package com.ecommerce.usuario_service.assembler;

import com.ecommerce.usuario_service.controller.UsuarioController;
import com.ecommerce.usuario_service.dto.UsuarioResponse;
import com.ecommerce.usuario_service.model.Usuario;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class UsuarioModelAssembler
        implements RepresentationModelAssembler<Usuario, EntityModel<UsuarioResponse>> {

    @Override
    public EntityModel<UsuarioResponse> toModel(Usuario usuario) {

        UsuarioResponse dto = mapToDTO(usuario);

        return EntityModel.of(dto,
                linkTo(methodOn(UsuarioController.class)
                        .obtener(usuario.getId()))
                        .withSelfRel(),

                linkTo(methodOn(UsuarioController.class)
                        .listar())
                        .withRel("usuarios"),

                linkTo(methodOn(UsuarioController.class)
                        .obtenerPorEmail(usuario.getEmail()))
                        .withRel("buscar-por-email"));
    }

    @Override
    public CollectionModel<EntityModel<UsuarioResponse>> toCollectionModel(
            Iterable<? extends Usuario> entities) {

        CollectionModel<EntityModel<UsuarioResponse>> models =
                RepresentationModelAssembler.super.toCollectionModel(entities);

        models.add(
                linkTo(methodOn(UsuarioController.class)
                        .listar())
                        .withSelfRel());

        return models;
    }

    private UsuarioResponse mapToDTO(Usuario usuario) {
        return UsuarioResponse.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .apellido(usuario.getApellido())
                .email(usuario.getEmail())
                .rol(usuario.getRol())
                .build();
    }
}
