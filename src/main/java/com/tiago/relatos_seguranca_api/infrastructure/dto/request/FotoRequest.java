package com.tiago.relatos_seguranca_api.infrastructure.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FotoRequest {

    @NotBlank(message = "O nome do arquivo é obrigatório.")
    @Size(max = 255)
    private String nomeArquivo;

    @NotBlank(message = "A URL é obrigatória.")
    @Size(max = 500)
    private String url;
}