package com.tiago.relatos_seguranca_api.infrastructure.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioPasswordRequest {

    private String currentPassword;
    private String newPassword;

}
