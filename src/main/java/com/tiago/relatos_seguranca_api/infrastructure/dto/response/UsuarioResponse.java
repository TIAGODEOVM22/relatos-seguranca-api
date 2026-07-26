package com.tiago.relatos_seguranca_api.infrastructure.dto.response;

import com.tiago.relatos_seguranca_api.infrastructure.enums.ProfileEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UsuarioResponse {

    private Long id;
    private String name;
    private String email;
    private Set<ProfileEnum> profiles;

}