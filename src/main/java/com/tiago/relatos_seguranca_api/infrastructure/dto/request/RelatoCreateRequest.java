package com.tiago.relatos_seguranca_api.infrastructure.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RelatoCreateRequest {

    @NotBlank(message = "Title cannot be empty.")
    @Size(min = 5, max = 100, message = "Title must contain between 5 and 100 characters.")
    private String titulo;

    @NotBlank(message = "Description cannot be empty.")
    @Size(min = 10, max = 1000, message = "Description must contain between 10 and 1000 characters.")
    private String descricao;

}