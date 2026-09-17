package com.tiago.relatos_seguranca_api.controller;


import com.tiago.relatos_seguranca_api.exception.StandardError;
import com.tiago.relatos_seguranca_api.exception.ValidationError;
import com.tiago.relatos_seguranca_api.infrastructure.assembler.UsuarioAssembler;
import com.tiago.relatos_seguranca_api.infrastructure.dto.request.UsuarioCreateRequest;
import com.tiago.relatos_seguranca_api.infrastructure.dto.request.UsuarioProfileRequest;
import com.tiago.relatos_seguranca_api.infrastructure.dto.request.UsuarioUpdateRequest;
import com.tiago.relatos_seguranca_api.infrastructure.dto.response.UsuarioResponse;
import com.tiago.relatos_seguranca_api.infrastructure.entity.Usuario;
import com.tiago.relatos_seguranca_api.infrastructure.repository.UsuarioRepository;
import com.tiago.relatos_seguranca_api.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioControllerImpl implements UsuarioController {

    private final UsuarioAssembler usuarioAssembler;
    private final UsuarioService usuarioService;

    @Override
    public ResponseEntity<UsuarioResponse> getUsuarioById(Long id) {

        Usuario usuario = usuarioService.findById(id);

        return ResponseEntity.ok(
                usuarioAssembler.toModel(usuario)
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
    public ResponseEntity<List<UsuarioResponse>> getAllUsuarios() {

        List<UsuarioResponse> usuarios = usuarioService.findAll();

        return ResponseEntity.ok(usuarios);
    }

    @Override
    public ResponseEntity<UsuarioResponse> updateUserNameAndEmail(
            Long id,
            UsuarioUpdateRequest request) {

        Usuario usuarioAtualizado =
                usuarioService.updateUserNameAndEmail(id, request);

        return ResponseEntity.ok(
                usuarioAssembler.toModel(usuarioAtualizado)
        );
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
}
