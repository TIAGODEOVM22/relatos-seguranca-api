package com.tiago.relatos_seguranca_api.controller;

import com.tiago.relatos_seguranca_api.exception.StandardError;
import com.tiago.relatos_seguranca_api.exception.ValidationError;
import com.tiago.relatos_seguranca_api.infrastructure.dto.request.UsuarioCreateRequest;
import com.tiago.relatos_seguranca_api.infrastructure.dto.request.UsuarioProfileRequest;
import com.tiago.relatos_seguranca_api.infrastructure.dto.request.UsuarioUpdateRequest;
import com.tiago.relatos_seguranca_api.infrastructure.dto.response.UsuarioResponse;
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
        name = "Usuários",
        description = "Operações relacionadas ao gerenciamento de usuários."
)
@RequestMapping("/usuarios")
public interface UsuarioController {

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
    @GetMapping("/{id}")
    @ResponseBody
    @PreAuthorize("hasAnyRole('ADMIN', 'TECHNICIAN')")
    ResponseEntity<UsuarioResponse> getUsuarioById(
            @Parameter(
                    description = "ID do usuário",
                    example = "1"
            )
            @PathVariable Long id
    );


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
                            array = @ArraySchema (schema = @Schema(implementation = UsuarioResponse.class)
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
    @GetMapping("/search")
    @ResponseBody
    @PreAuthorize("hasAnyRole('ADMIN', 'TECHNICIAN')")
    ResponseEntity<List<UsuarioResponse>> findByName(
            @Parameter(
                    description = "Nome ou parte do nome do usuário",
                    example = "Tiago"
            )
            @RequestParam String name
    );


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
    @PostMapping
    @ResponseBody
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<Void> createUser(
            @RequestBody @Valid UsuarioCreateRequest request
    );


    @Operation(
            summary = "Listar todos os usuários",
            description = "Retorna todos os usuários cadastrados no sistema."
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
    @GetMapping
    @ResponseBody
    @PreAuthorize("hasAnyRole('ADMIN', 'TECHNICIAN')")
    ResponseEntity<List<UsuarioResponse>> getAllUsuarios();

    @Operation(
            summary = "Atualizar usuário",
            description = "Atualiza o nome e o e-mail de um usuário."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuário atualizado com sucesso",
                    content = @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UsuarioResponse.class)
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
    @PutMapping("/{id}")
    @ResponseBody
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<UsuarioResponse> updateUserNameAndEmail(
            @PathVariable Long id,
            @RequestBody @Valid UsuarioUpdateRequest request
    );


    @Operation(
            summary = "Excluir usuário",
            description = "Remove um usuário do sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Usuário excluído com sucesso"
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
    @DeleteMapping("/{id}")
    @ResponseBody
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<Void> delete(
            @PathVariable Long id
    );


    @Operation(
            summary = "Atualizar perfis do usuário",
            description = "Atualiza os perfis de acesso de um usuário."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Perfis atualizados com sucesso",
                    content = @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UsuarioResponse.class)
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
    @PatchMapping("/{id}/profiles")
    @ResponseBody
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<UsuarioResponse> updateProfiles(
            @PathVariable Long id,
            @RequestBody @Valid UsuarioProfileRequest request
    );
}