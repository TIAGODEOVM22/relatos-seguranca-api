package com.tiago.relatos_seguranca_api.controller;


import com.tiago.relatos_seguranca_api.infrastructure.assembler.UsuarioAssembler;
import com.tiago.relatos_seguranca_api.infrastructure.assembler.UsuarioModelAssembler;
import com.tiago.relatos_seguranca_api.infrastructure.dto.request.UsuarioCreateRequest;
import com.tiago.relatos_seguranca_api.infrastructure.dto.request.UsuarioProfileRequest;
import com.tiago.relatos_seguranca_api.infrastructure.dto.request.UsuarioUpdateRequest;
import com.tiago.relatos_seguranca_api.infrastructure.dto.response.UsuarioResponse;
import com.tiago.relatos_seguranca_api.infrastructure.entity.Usuario;
import com.tiago.relatos_seguranca_api.services.PdfService;
import com.tiago.relatos_seguranca_api.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioControllerImpl implements UsuarioController {

    private final UsuarioAssembler usuarioAssembler;
    private final UsuarioService usuarioService;
    private final PdfService pdfService;
    private final UsuarioModelAssembler usuarioModelAssembler;


    @Override
    public ResponseEntity<EntityModel<UsuarioResponse>> getUsuarioById(Long id) {

        Usuario usuario = usuarioService.findById(id);

        UsuarioResponse usuarioResponse = usuarioAssembler.toModel(usuario);

        return ResponseEntity.ok(
                usuarioModelAssembler.toModel(usuarioResponse)
        );
    }

    @Override
    public ResponseEntity<List<UsuarioResponse>> findByName(String name) {

        return ResponseEntity.ok(
                usuarioService.findByName(name)
        );
    }

    @Override
    public ResponseEntity<Void> createUser(UsuarioCreateRequest request) {

        Usuario usuario = usuarioAssembler.toDomainObject(request);
        usuario = usuarioService.save(usuario);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(usuario.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @Override
    public ResponseEntity<CollectionModel<EntityModel<UsuarioResponse>>> getAllUsuarios() {

        List<UsuarioResponse> usuarios = usuarioService.findAll();

        CollectionModel<EntityModel<UsuarioResponse>> collection =
                usuarioModelAssembler.toCollectionModel(usuarios);

        collection.add(
                linkTo(methodOn(UsuarioController.class)
                        .getAllUsuarios())
                        .withSelfRel()
        );

        return ResponseEntity.ok(collection);
    }

    @Override
    public ResponseEntity<EntityModel<UsuarioResponse>> updateUserNameAndEmail(Long id, UsuarioUpdateRequest request) {
        Usuario usuarioAtualizado = usuarioService.updateUserNameAndEmail(id, request);
        UsuarioResponse response = usuarioAssembler.toModel(usuarioAtualizado);
        EntityModel<UsuarioResponse> resource = usuarioModelAssembler.toModel(response);
        return ResponseEntity.ok(resource);
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {

        usuarioService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<UsuarioResponse> updateProfiles(
            Long id,
            UsuarioProfileRequest request) {

        Usuario usuario = usuarioAssembler.toDomainObject(request);

        Usuario usuarioAtualizado =
                usuarioService.updateProfiles(id, usuario);

        return ResponseEntity.ok(
                usuarioAssembler.toModel(usuarioAtualizado)
        );
    }

    @Override
    public ResponseEntity<Resource> gerarPdfUsuarios() {

        Resource pdf = pdfService.gerarPdfUsuarios();

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=usuarios.pdf"
                )
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }

}
