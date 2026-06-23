package com.ecommerce.usuario_service.assembler;

import com.ecommerce.usuario_service.controller.UsuarioControllerV2;
import com.ecommerce.usuario_service.dto.UsuarioResponse;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class UsuarioModelAssembler
        implements RepresentationModelAssembler<UsuarioResponse, EntityModel<UsuarioResponse>> {

@Override
public EntityModel<UsuarioResponse> toModel(UsuarioResponse usuario) {

    return EntityModel.of(usuario,
            linkTo(methodOn(UsuarioControllerV2.class)
                    .obtener(usuario.getId()))
                    .withSelfRel(),

            linkTo(methodOn(UsuarioControllerV2.class)
                    .listar())
                    .withRel("usuarios"),

            linkTo(methodOn(UsuarioControllerV2.class)
                    .obtenerPorEmail(usuario.getEmail()))
                    .withRel("buscar-por-email"));
}

@Override
public CollectionModel<EntityModel<UsuarioResponse>> toCollectionModel(
        Iterable<? extends UsuarioResponse> entities) {

        CollectionModel<EntityModel<UsuarioResponse>> models =
                RepresentationModelAssembler.super.toCollectionModel(entities);

        models.add(
                linkTo(methodOn(UsuarioControllerV2.class)
                        .listar())
                        .withSelfRel());

        return models;
    }


}
