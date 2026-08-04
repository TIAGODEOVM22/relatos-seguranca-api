package com.tiago.relatos_seguranca_api.infrastructure.dto.request;

import com.tiago.relatos_seguranca_api.infrastructure.enums.Prioridade;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RelatoPrioridadeRequest {

    @NotNull(message = "Priority must be informed.")
    private Prioridade prioridade;

}
