package com.tiago.relatos_seguranca_api.infrastructure.dto.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class FotoResponse {

    private MultipartFile arquivo;

    private String nomeArquivo;

//    private String url;
//    private String contentType;
//    private Long tamanho;

}
