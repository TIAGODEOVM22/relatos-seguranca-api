package com.tiago.relatos_seguranca_api.infrastructure.dto.request;

import com.tiago.relatos_seguranca_api.infrastructure.enums.ProfileEnum;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UsuarioProfileRequest {

    @NotEmpty(message = "At least one profile must be informed.")
    private Set<ProfileEnum> profiles;

}
