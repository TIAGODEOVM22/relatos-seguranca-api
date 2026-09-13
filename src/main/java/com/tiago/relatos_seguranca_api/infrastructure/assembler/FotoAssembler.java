package com.tiago.relatos_seguranca_api.infrastructure.assembler;

import com.tiago.relatos_seguranca_api.infrastructure.dto.response.FotoResponse;
import com.tiago.relatos_seguranca_api.infrastructure.entity.Foto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FotoAssembler {

    private final ModelMapper modelMapper;

    public FotoResponse toModel(Foto foto) {

        return modelMapper.map(foto, FotoResponse.class);
    }

    public List<FotoResponse> toCollectionModel(List<Foto> fotos) {
        return fotos.stream()
                .map(this::toModel)
                .toList();
    }
}
