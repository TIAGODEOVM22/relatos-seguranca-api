package com.tiago.relatos_seguranca_api.controller;

import com.tiago.relatos_seguranca_api.infrastructure.assembler.RelatoAssembler;
import com.tiago.relatos_seguranca_api.infrastructure.dto.request.RelatoCreateRequest;
import com.tiago.relatos_seguranca_api.infrastructure.dto.request.RelatoPrioridadeRequest;
import com.tiago.relatos_seguranca_api.infrastructure.dto.request.RelatoUpdateRequest;
import com.tiago.relatos_seguranca_api.infrastructure.dto.response.RelatoResponse;
import com.tiago.relatos_seguranca_api.infrastructure.entity.Relato;
import com.tiago.relatos_seguranca_api.infrastructure.entity.Usuario;
import com.tiago.relatos_seguranca_api.services.RelatoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/relatos")
public class RelatoController {

    @Autowired
    private final RelatoService relatoService;

    @Autowired
    private final RelatoAssembler relatoAssembler;

//    @PostMapping /*SALAVA RELATO SEM RETORNAR A URI*/
//    public ResponseEntity<Void> createRelato(
//            @RequestBody @Valid RelatoCreateRequest relatoCreateRequest) {
//
//        Relato relato = relatoAssembler.toDomainObject(relatoCreateRequest);
//
//        relatoService.salvarRelato(
//                relato,
//                relatoCreateRequest.getUsuarioId()
//        );
//
//        return ResponseEntity.status(HttpStatus.CREATED).build();
//    }

    @PostMapping /*SALVA RETORNANDO A URI*/
    public ResponseEntity<Void> createRelato(@RequestBody @Valid RelatoCreateRequest relatoCreateRequest) {

        Relato relato = relatoAssembler.toDomainObject(relatoCreateRequest);

        relato = relatoService.salvarRelato(
                relato,
                relatoCreateRequest.getUsuarioId()
        );

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(relato.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RelatoResponse> getRelatoById(@PathVariable Long id) {
        Relato relato = relatoService.findById(id);
        return ResponseEntity.ok(relatoAssembler.toModel(relato));
    }

    @GetMapping
    public ResponseEntity<List<RelatoResponse>> getAllRelatos() {
        List<RelatoResponse> relatos = relatoService.findAll();
        return ResponseEntity.ok(relatos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RelatoResponse> updateRelato(
            @PathVariable Long id,
            @RequestBody @Valid RelatoUpdateRequest request) {

        Relato relato = relatoService.findById(id);

        relatoAssembler.copyToDomainObject(request, relato);

        Relato relatoAtualizado = relatoService.updateRelato(id, relato);

        return ResponseEntity.ok(
                relatoAssembler.toModel(relatoAtualizado)
        );
    }

    @PatchMapping("/{id}/prioridade")
    public ResponseEntity<RelatoResponse> atualizarPrioridade( @PathVariable Long id,
                                                               @RequestBody @Valid RelatoPrioridadeRequest request) {

        Relato relato = relatoService.updatePrioridadeRelato(
                id,
                request.getPrioridade()
        );

        return ResponseEntity.ok(
                relatoAssembler.toModel(relato)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        relatoService.deletarRelato(id);
        return ResponseEntity.noContent().build();

    }

}
