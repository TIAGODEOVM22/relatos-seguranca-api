package com.tiago.relatos_seguranca_api.controller;

import com.tiago.relatos_seguranca_api.infrastructure.assembler.RelatoAssembler;
import com.tiago.relatos_seguranca_api.infrastructure.dto.request.RelatoCreateRequest;
import com.tiago.relatos_seguranca_api.infrastructure.dto.request.RelatoPrioridadeRequest;
import com.tiago.relatos_seguranca_api.infrastructure.dto.request.RelatoUpdateRequest;
import com.tiago.relatos_seguranca_api.infrastructure.dto.response.RelatoResponse;
import com.tiago.relatos_seguranca_api.infrastructure.entity.Relato;
import com.tiago.relatos_seguranca_api.services.RelatoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class RelatoControllerImpl implements RelatoController {

    private final RelatoService relatoService;
    private final RelatoAssembler relatoAssembler;

    @Override
    public ResponseEntity<Void> createRelato(
            RelatoCreateRequest request) {

        Relato relato =
                relatoAssembler.toDomainObject(request);

        relato = relatoService.salvarRelato(
                relato,
                request.getUsuarioId()
        );

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(relato.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @Override
    public ResponseEntity<RelatoResponse> getRelatoById(Long id) {

        Relato relato = relatoService.findById(id);

        return ResponseEntity.ok(
                relatoAssembler.toModel(relato)
        );
    }

    @Override
    public ResponseEntity<List<RelatoResponse>> getAllRelatos() {

        List<RelatoResponse> relatos =
                relatoService.findAll();

        return ResponseEntity.ok(relatos);
    }

    @Override
    public ResponseEntity<RelatoResponse> updateRelato(
            Long id,
            RelatoUpdateRequest request) {

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
    public ResponseEntity<RelatoResponse> atualizarPrioridade(
            Long id,
            RelatoPrioridadeRequest request) {

        Relato relato =
                relatoService.updatePrioridadeRelato(
                        id,
                        request.getPrioridade()
                );

        return ResponseEntity.ok(
                relatoAssembler.toModel(relato)
        );
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {

        relatoService.deletarRelato(id);

        return ResponseEntity.noContent().build();
    }
}
