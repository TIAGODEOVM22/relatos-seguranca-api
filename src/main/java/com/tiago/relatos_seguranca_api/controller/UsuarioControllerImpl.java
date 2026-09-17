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
public class UsuarioController {


    @Autowired
    private UsuarioAssembler usuarioAssembler;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Operation(
            summary = "Buscar usuário por ID",
            description = "Retorna os dados de um usuário a partir do seu ID."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuário encontrado",
                    content = @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UsuarioResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuário não encontrado",
                    content = @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = StandardError.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Não autenticado"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Acesso negado"
            )
    })
    @SecurityRequirement(name = "bearer-key")
    @PreAuthorize("hasAnyRole('ADMIN', 'TECHNICIAN')")
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> getUsuarioById(
            @Parameter(description = "ID do usuário", example = "1")
            @PathVariable Long id) {

        Usuario usuario = usuarioService.findById(id);
        return ResponseEntity.ok(usuarioAssembler.toModel(usuario));
    }

    @Operation(
            summary = "Buscar usuários por nome",
            description = "Retorna uma lista de usuários cujo nome contém o termo informado."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuários encontrados",
                    content = @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            array = @ArraySchema(
                                    schema = @Schema(implementation = UsuarioResponse.class)
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Não autenticado"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Acesso negado"
            )
    })
    @SecurityRequirement(name = "bearer-key")
    @PreAuthorize("hasAnyRole('ADMIN', 'TECHNICIAN')")
    @GetMapping("/search")
    public ResponseEntity<List<UsuarioResponse>> findByName(
            @Parameter(
                    description = "Nome ou parte do nome do usuário",
                    example = "Tiago"
            )
            @RequestParam String name) {

        return ResponseEntity.ok(usuarioService.findByName(name));
    }

    @Operation(
            summary = "Criar usuário",
            description = "Cadastra um novo usuário no sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Usuário criado com sucesso"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos",
                    content = @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ValidationError.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "E-mail já cadastrado",
                    content = @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = StandardError.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Não autenticado"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Acesso negado"
            )
    })
    @SecurityRequirement(name = "bearer-key")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Void> createUser (@RequestBody @Valid UsuarioCreateRequest request) {

        Usuario usuario = usuarioAssembler.toDomainObject(request);
        usuario = usuarioService.save(usuario);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(usuario.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'TECHNICIAN')")
    @GetMapping /*Buscar todos usuarios*/
    public ResponseEntity<List<UsuarioResponse>> getAllUsuarios() {
        List<UsuarioResponse> usuarios = usuarioService.findAll();
        return ResponseEntity.ok(usuarios);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}") // Atualiza Nome e Email
    public ResponseEntity<UsuarioResponse> updateUserNameAndEmail(
            @PathVariable Long id, @RequestBody @Valid UsuarioUpdateRequest usuarioUpdateRequest) {
        Usuario usuarioAtualizado = usuarioService.updateUserNameAndEmail(id, usuarioUpdateRequest);
        return ResponseEntity.ok(usuarioAssembler.toModel(usuarioAtualizado));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")/*Deletar usuario*/
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        usuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}/profiles")
    public ResponseEntity<UsuarioResponse> updateProfiles(
            @PathVariable Long id,
            @RequestBody @Valid UsuarioProfileRequest request) {

        Usuario usuario = usuarioAssembler.toDomainObject(request);
        Usuario usuarioAtualizado = usuarioService.updateProfiles(id, usuario);
        return ResponseEntity.ok(usuarioAssembler.toModel(usuarioAtualizado));
    }
}
