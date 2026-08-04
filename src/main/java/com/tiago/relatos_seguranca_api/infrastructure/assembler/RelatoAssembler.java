package com.tiago.relatos_seguranca_api.infrastructure.assembler;

import com.tiago.relatos_seguranca_api.infrastructure.dto.request.RelatoCreateRequest;
import com.tiago.relatos_seguranca_api.infrastructure.dto.request.RelatoPrioridadeRequest;
import com.tiago.relatos_seguranca_api.infrastructure.dto.request.RelatoUpdateRequest;
import com.tiago.relatos_seguranca_api.infrastructure.dto.response.RelatoResponse;
import com.tiago.relatos_seguranca_api.infrastructure.entity.Relato;
import com.tiago.relatos_seguranca_api.infrastructure.enums.Prioridade;
import com.tiago.relatos_seguranca_api.infrastructure.enums.StatusRelato;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RelatoAssembler {

    @Autowired
    private final ModelMapper modelMapper;

    public Relato toDomainObject(RelatoCreateRequest request) {
        Relato relato = new Relato();

        relato.setTitulo(request.getTitulo());
        relato.setDescricao(request.getDescricao());

        return relato;
    }

    private Relato toDomainObject(RelatoUpdateRequest relatoUpdateRequest, Relato relato) {
        modelMapper.map(relatoUpdateRequest, relato);
        return relato;
    }

    private Relato toDomainObject(RelatoPrioridadeRequest relatoPrioridadeRequest) {
        return modelMapper.map(relatoPrioridadeRequest, Relato.class);
    }

    public RelatoResponse toModel (Relato relato) {
        return modelMapper.map(relato, RelatoResponse.class);
    }

    public List<RelatoResponse> toCollectionModel(List<Relato> relatos) {
        return relatos.stream()
                .map(this::toModel)
                .toList();
    }

    public void copyToDomainObject(RelatoUpdateRequest request, Relato relato) {

        relato.setTitulo(request.getTitulo());
        relato.setDescricao(request.getDescricao());

        relato.setPrioridade(
                Prioridade.valueOf(request.getPrioridade().toUpperCase())
        );

        relato.setStatus(
                StatusRelato.valueOf(request.getStatus().toUpperCase())
        );
    }

    public void copyPrioridadeToDomainObject(RelatoPrioridadeRequest request, Relato relato) {

        relato.setPrioridade(request.getPrioridade());
    }


}
