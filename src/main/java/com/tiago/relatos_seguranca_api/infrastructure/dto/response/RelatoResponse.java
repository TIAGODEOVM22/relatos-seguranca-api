package com.tiago.relatos_seguranca_api.infrastructure.dto.response;

import com.tiago.relatos_seguranca_api.infrastructure.enums.Prioridade;
import com.tiago.relatos_seguranca_api.infrastructure.enums.StatusRelato;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class RelatoResponse {

    private Long id;

    private String titulo;

    private String descricao;

    private StatusRelato status;

    private Prioridade prioridade;

    private LocalDateTime dataDoRelato;

    private UsuarioRelatoResponse usuario;

}
