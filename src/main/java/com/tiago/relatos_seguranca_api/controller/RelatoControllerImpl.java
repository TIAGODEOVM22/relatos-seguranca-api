package com.tiago.relatos_seguranca_api.controller;

import com.tiago.relatos_seguranca_api.infrastructure.assembler.RelatoAssembler;
import com.tiago.relatos_seguranca_api.infrastructure.assembler.RelatoModelAssembler;
import com.tiago.relatos_seguranca_api.infrastructure.dto.request.RelatoCreateRequest;
import com.tiago.relatos_seguranca_api.infrastructure.dto.request.RelatoPrioridadeRequest;
import com.tiago.relatos_seguranca_api.infrastructure.dto.request.RelatoUpdateRequest;
import com.tiago.relatos_seguranca_api.infrastructure.dto.response.RelatoResponse;
import com.tiago.relatos_seguranca_api.infrastructure.entity.Relato;
import com.tiago.relatos_seguranca_api.security.UserDetailsDTO;
import com.tiago.relatos_seguranca_api.services.FotoService;
import com.tiago.relatos_seguranca_api.services.PdfService;
import com.tiago.relatos_seguranca_api.services.RelatoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
public class RelatoControllerImpl implements RelatoController {

    private final RelatoModelAssembler relatoModelAssembler;
    private final FotoService fotoService;
    private final RelatoService relatoService;
    private final RelatoAssembler relatoAssembler;
    private final PdfService pdfService;


    @Override
    public ResponseEntity<Void> createRelato(
            @RequestPart("request") @Valid RelatoCreateRequest request,
            @RequestPart("foto") MultipartFile foto,
            Authentication authentication) {

        UserDetailsDTO userDetails =
                (UserDetailsDTO) authentication.getPrincipal();

        Long usuarioId = Long.valueOf(userDetails.getId());

        Relato relato = relatoAssembler.toDomainObject(request);

        relato = relatoService.salvarRelato(relato, usuarioId);

        fotoService.salvarFoto(foto, relato);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(relato.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @Override
    public ResponseEntity<EntityModel<RelatoResponse>> getRelatoById(Long id) {
        Relato relato = relatoService.findById(id);
        RelatoResponse relatoResponse = relatoAssembler.toModel(relato);
        return ResponseEntity.ok(
                relatoModelAssembler.toModel(relatoResponse)
        );
    }

    @Override
    public ResponseEntity<CollectionModel<EntityModel<RelatoResponse>>> getAllRelatos() {
        List<RelatoResponse> relatos = relatoService.findAll();
        CollectionModel<EntityModel<RelatoResponse>> collection = relatoModelAssembler.toCollectionModel(relatos);
        collection.add(linkTo(methodOn(RelatoController.class)
                        .getAllRelatos())
                        .withSelfRel()
        );

        return ResponseEntity.ok(collection);
    }

    @Override
    public ResponseEntity<RelatoResponse> updateRelato(Long id, RelatoUpdateRequest request) {
        Relato relato = relatoService.findById(id);
        relatoAssembler.copyToDomainObject(
                request,
                relato
        );

        Relato relatoAtualizado =
                relatoService.updateRelato(id, relato);

        return ResponseEntity.ok(
                relatoAssembler.toModel(relatoAtualizado)
        );
    }

    @Override
    public ResponseEntity<RelatoResponse> atualizarPrioridade(Long id, RelatoPrioridadeRequest request) {
        Relato relato = relatoService.updatePrioridadeRelato(id, request.getPrioridade());
        return ResponseEntity.ok(
                relatoAssembler.toModel(relato)
        );
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        relatoService.deletarRelato(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Resource> gerarPdfRelatos() {

        Resource pdf = pdfService.gerarPdfRelatos();

        return ResponseEntity.ok().header(HttpHeaders
                        .CONTENT_DISPOSITION, "attachment; filename=relatos-seguranca.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}
