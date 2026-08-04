package com.tiago.relatos_seguranca_api.infrastructure.assembler;

import com.tiago.relatos_seguranca_api.infrastructure.dto.request.UsuarioCreateRequest;
import com.tiago.relatos_seguranca_api.infrastructure.dto.request.UsuarioProfileRequest;
import com.tiago.relatos_seguranca_api.infrastructure.dto.request.UsuarioUpdateRequest;
import com.tiago.relatos_seguranca_api.infrastructure.dto.response.UsuarioResponse;
import com.tiago.relatos_seguranca_api.infrastructure.entity.Usuario;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UsuarioAssembler {

    private final ModelMapper modelMapper;

    /*Entity para Response*/
    public UsuarioResponse toModel(Usuario usuario) {
        return modelMapper.map(usuario, UsuarioResponse.class);
    }

    public List<UsuarioResponse> toCollectionModel(List<Usuario> usuarios) {
        return usuarios.stream()
                .map(this::toModel)
                .toList();
    }

    /*Request para Entity*/

    public Usuario toDomainObject(UsuarioCreateRequest request) {

        return modelMapper.map(request, Usuario.class);
    }

    public Usuario toDomainObject(UsuarioUpdateRequest request) {
        return modelMapper.map(request, Usuario.class);
    }

    public Usuario toDomainObject(UsuarioProfileRequest request) {
        return modelMapper.map(request, Usuario.class);
    }

    /*Copia de forma inteligente*/

    public void copyToDomainObject(UsuarioCreateRequest request, Usuario usuario) {
        modelMapper.map(request, usuario);
    }

    public void copyToDomainObject(UsuarioUpdateRequest request, Usuario usuario) {
        modelMapper.map(request, usuario);
    }

    public void copyToDomainObject(UsuarioProfileRequest request, Usuario usuario) {
        modelMapper.map(request, usuario);
    }

}
