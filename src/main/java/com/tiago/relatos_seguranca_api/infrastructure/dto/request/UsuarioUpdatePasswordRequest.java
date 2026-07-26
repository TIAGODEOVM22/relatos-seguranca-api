package com.tiago.relatos_seguranca_api.infrastructure.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioUpdatePasswordRequest {

    private String password;
    private String newPassword;

}
