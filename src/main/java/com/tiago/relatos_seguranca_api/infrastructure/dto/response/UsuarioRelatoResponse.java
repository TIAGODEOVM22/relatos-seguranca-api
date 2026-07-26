package com.tiago.relatos_seguranca_api.infrastructure.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioRelatoResponse {/*Classe para retornar o relato e o seu criador*/

    private Long id;
    private String name;
    private String email;



}
