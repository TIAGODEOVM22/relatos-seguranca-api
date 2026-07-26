package com.tiago.relatos_seguranca_api.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.nio.file.Path;
import java.util.UUID;

@RestController
@RequestMapping("/relatos/{relatoId}/fotos")
public class FotoController {

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void uploadFoto( @PathVariable Long fotoId,
                           @RequestParam MultipartFile arquivo) {

     var nomeArquivo = UUID.randomUUID().toString() + "_" + arquivo.getOriginalFilename();

     var arquivoFoto = Path.of("/Users/tiago/OneDrive/Documentos", nomeArquivo);

     System.out.println(arquivoFoto);
     System.out.println(arquivo.getContentType());

     try {
         arquivo.transferTo(arquivoFoto);
     } catch (Exception e) {
         throw new RuntimeException(e);
     }





    }



}
