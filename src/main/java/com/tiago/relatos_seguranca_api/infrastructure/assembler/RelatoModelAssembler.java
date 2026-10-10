package com.tiago.relatos_seguranca_api.infrastructure.assembler;

import com.tiago.relatos_seguranca_api.controller.RelatoController;
import com.tiago.relatos_seguranca_api.infrastructure.dto.response.RelatoResponse;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class RelatoModelAssembler
        implements RepresentationModelAssembler<RelatoResponse, EntityModel<RelatoResponse>> {

    @Override
    public EntityModel<RelatoResponse> toModel(RelatoResponse relato) {

        return EntityModel.of(
                relato,
                linkTo(
                        methodOn(RelatoController.class)
                                .getRelatoById(relato.getId())
                ).withSelfRel(),

                linkTo(
                        methodOn(RelatoController.class)
                                .getAllRelatos()
                ).withRel("relatos")
        );
    }
}