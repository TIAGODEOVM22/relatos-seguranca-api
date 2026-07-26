package com.tiago.relatos_seguranca_api.infrastructure.dto.request;

import com.tiago.relatos_seguranca_api.infrastructure.enums.Prioridade;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RelatoPrioridadeRequest {

    @NotEmpty(message = "At least one priority must be informed.")
    private Prioridade prioridades;

}
