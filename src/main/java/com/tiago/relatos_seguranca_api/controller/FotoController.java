package com.tiago.relatos_seguranca_api.controller;

import com.tiago.relatos_seguranca_api.exception.StandardError;
import com.tiago.relatos_seguranca_api.infrastructure.dto.response.FotoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(
        name = "Fotos",
        description = "Operações relacionadas às fotos dos relatos de segurança."
)
@RequestMapping("/relatos/{relatoId}/fotos")
public interface FotoController {

    @Operation(
            summary = "Enviar foto para um relato",
            description = "Realiza o upload de uma foto associada a um relato de segurança."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Foto enviada com sucesso",
                    content = @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = FotoResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Arquivo inválido",
                    content = @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = StandardError.class)
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
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<FotoResponse> upload(
            @Parameter(description = "ID do relato", example = "1")
            @PathVariable Long relatoId,

            @Parameter(
                    description = "Arquivo de imagem",
                    required = true
            )
            @RequestParam("foto") MultipartFile arquivo
    );


    @Operation(
            summary = "Listar fotos de um relato",
            description = "Retorna todas as fotos associadas a um relato de segurança."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Fotos encontradas",
                    content = @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = FotoResponse.class)
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
    @PreAuthorize("hasAnyRole('ADMIN', 'TECHNICIAN', 'CUSTOMER')")
    @GetMapping
    ResponseEntity<List<FotoResponse>> listarFotos(
            @Parameter(description = "ID do relato", example = "1")
            @PathVariable Long relatoId
    );


    @Operation(
            summary = "Excluir foto",
            description = "Exclui uma foto associada a um relato de segurança."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Foto excluída com sucesso"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Relato ou foto não encontrado",
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
    @DeleteMapping("/{fotoId}")
    ResponseEntity<Void> excluir(
            @Parameter(description = "ID do relato", example = "1")
            @PathVariable Long relatoId,

            @Parameter(description = "ID da foto", example = "8")
            @PathVariable Long fotoId
    );
}