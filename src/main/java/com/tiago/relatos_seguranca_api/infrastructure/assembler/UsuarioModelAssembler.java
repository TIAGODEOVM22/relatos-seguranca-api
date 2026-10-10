package com.tiago.relatos_seguranca_api.infrastructure.assembler;

import com.tiago.relatos_seguranca_api.controller.UsuarioController;
import com.tiago.relatos_seguranca_api.infrastructure.dto.response.UsuarioResponse;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UsuarioModelAssembler
        implements RepresentationModelAssembler<UsuarioResponse, EntityModel<UsuarioResponse>> {

    @Override
    public EntityModel<UsuarioResponse> toModel(UsuarioResponse usuario) {

        return EntityModel.of(usuario,
                linkTo(methodOn(UsuarioController.class)
                        .getUsuarioById(usuario.getId())).withSelfRel(),

                linkTo(methodOn(UsuarioController.class).getAllUsuarios()).withRel("usuarios")
        );
    }
}