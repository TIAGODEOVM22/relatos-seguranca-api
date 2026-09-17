package com.tiago.relatos_seguranca_api.controller;

import com.tiago.relatos_seguranca_api.exception.StandardError;
import com.tiago.relatos_seguranca_api.infrastructure.assembler.FotoAssembler;
import com.tiago.relatos_seguranca_api.infrastructure.dto.response.FotoResponse;
import com.tiago.relatos_seguranca_api.infrastructure.entity.Foto;
import com.tiago.relatos_seguranca_api.infrastructure.entity.Relato;
import com.tiago.relatos_seguranca_api.services.FotoService;
import com.tiago.relatos_seguranca_api.services.RelatoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/relatos/{relatoId}/fotos")
@RequiredArgsConstructor
public class FotosControllerImpl {

    private final FotoService fotoService;
    private final RelatoService relatoService;
    private final FotoAssembler fotoAssembler;

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
            )
    })
    @SecurityRequirement(name = "bearer-key")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FotoResponse> upload(
            @PathVariable Long relatoId,
            @RequestParam("foto") MultipartFile arquivo) {

        Relato relato = relatoService.findById(relatoId);

        Foto foto = fotoService.salvarFoto(arquivo, relato);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{fotoId}")
                .buildAndExpand(foto.getId())
                .toUri();

        return ResponseEntity
                .created(uri)
                .body(fotoAssembler.toModel(foto));
    }

    /*Busca todas as fotos de um relato
    * GET /relatos/{relatoId}/fotos*/
    @PreAuthorize("hasAnyRole('ADMIN', 'TECHNICIAN', 'CUSTOMER')")
    @GetMapping
    public ResponseEntity<List<FotoResponse>> listarFotos (@PathVariable Long relatoId) {
        List<Foto> fotos = fotoService.listarPorRelato(relatoId);
        return ResponseEntity.ok(fotoAssembler.toCollectionModel(fotos));
    }

    /*DELETE /relatos/1/fotos/8*/
    @PreAuthorize("hasAnyRole('ADMIN', 'TECHNICIAN')")
    @DeleteMapping("/{fotoId}")
    public ResponseEntity<Void> excluir(
            @PathVariable Long relatoId,
            @PathVariable Long fotoId) {

        fotoService.excluirFoto(relatoId, fotoId);

        return ResponseEntity.noContent().build();
    }

}