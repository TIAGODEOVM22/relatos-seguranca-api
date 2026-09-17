package com.tiago.relatos_seguranca_api.controller;

import com.tiago.relatos_seguranca_api.exception.StandardError;
import com.tiago.relatos_seguranca_api.exception.ValidationError;
import com.tiago.relatos_seguranca_api.infrastructure.dto.request.RelatoCreateRequest;
import com.tiago.relatos_seguranca_api.infrastructure.dto.request.RelatoPrioridadeRequest;
import com.tiago.relatos_seguranca_api.infrastructure.dto.request.RelatoUpdateRequest;
import com.tiago.relatos_seguranca_api.infrastructure.dto.response.RelatoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(
        name = "Relatos",
        description = "Operações relacionadas aos relatos de segurança."
)
@RequestMapping("/relatos")
public interface RelatoController {

    @Operation(
            summary = "Criar relato",
            description = "Cadastra um novo relato de segurança."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Relato criado com sucesso"
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
            )
    })
    @SecurityRequirement(name = "bearer-key")
    @PostMapping
    ResponseEntity<Void> createRelato(
            @RequestBody @Valid RelatoCreateRequest request
    );


    @Operation(
            summary = "Buscar relato por ID",
            description = "Retorna um relato de segurança a partir do seu ID."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Relato encontrado",
                    content = @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = RelatoResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Relato não encontrado",
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
    ResponseEntity<RelatoResponse> getRelatoById(
            @Parameter(
                    description = "ID do relato",
                    example = "1"
            )
            @PathVariable Long id
    );


    @Operation(
            summary = "Listar todos os relatos",
            description = "Retorna todos os relatos de segurança cadastrados."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Relatos encontrados",
                    content = @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            array = @ArraySchema(
                                    schema = @Schema(implementation = RelatoResponse.class)
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
    @GetMapping
    ResponseEntity<List<RelatoResponse>> getAllRelatos();


    @Operation(
            summary = "Atualizar relato",
            description = "Atualiza os dados de um relato de segurança."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Relato atualizado com sucesso",
                    content = @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = RelatoResponse.class)
                    )
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
                    responseCode = "404",
                    description = "Relato não encontrado",
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
    @PutMapping("/{id}")
    ResponseEntity<RelatoResponse> updateRelato(
            @PathVariable Long id,
            @RequestBody @Valid RelatoUpdateRequest request
    );


    @Operation(
            summary = "Atualizar prioridade do relato",
            description = "Atualiza a prioridade de um relato de segurança."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Prioridade atualizada com sucesso",
                    content = @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = RelatoResponse.class)
                    )
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
                    responseCode = "404",
                    description = "Relato não encontrado",
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
    @PatchMapping("/{id}/prioridade")
    ResponseEntity<RelatoResponse> atualizarPrioridade(
            @PathVariable Long id,
            @RequestBody @Valid RelatoPrioridadeRequest request
    );


    @Operation(
            summary = "Excluir relato",
            description = "Remove um relato de segurança."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Relato excluído com sucesso"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Relato não encontrado",
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
    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(
            @PathVariable Long id
    );
}