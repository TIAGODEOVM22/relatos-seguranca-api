package com.tiago.relatos_seguranca_api.controller;

import com.tiago.relatos_seguranca_api.infrastructure.assembler.FotoAssembler;
import com.tiago.relatos_seguranca_api.infrastructure.dto.response.FotoResponse;
import com.tiago.relatos_seguranca_api.infrastructure.entity.Foto;
import com.tiago.relatos_seguranca_api.infrastructure.entity.Relato;
import com.tiago.relatos_seguranca_api.services.FotoService;
import com.tiago.relatos_seguranca_api.services.RelatoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/relatos/{relatoId}/fotos")
@RequiredArgsConstructor
public class FotoController {

    private final FotoService fotoService;
    private final RelatoService relatoService;
    private final FotoAssembler fotoAssembler;

    //    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public void uploadFoto( @PathVariable Long fotoId,
//                           @RequestParam MultipartFile arquivo) {
//
//     var nomeArquivo = UUID.randomUUID().toString() + "_" + arquivo.getOriginalFilename();
//
//     var arquivoFoto = Path.of("/Users/tiago/OneDrive/Documentos", nomeArquivo);
//
//     System.out.println(arquivoFoto);
//     System.out.println(arquivo.getContentType());
//
//     try {
//         arquivo.transferTo(arquivoFoto);
//     } catch (Exception e) {
//         throw new RuntimeException(e);
//     }
//
//    }
    //  CRIAR FOTO SEM RETORNAR A URI
//    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<FotoResponse> upload(
//            @PathVariable Long relatoId,
//            @RequestParam("foto") MultipartFile arquivo) {
//
//        Relato relato = relatoService.findById(relatoId);
//
//        Foto foto = fotoService.salvarFoto(arquivo, relato);
//
//        return ResponseEntity
//                .status(HttpStatus.CREATED)
//                .body(fotoAssembler.toModel(foto));
//    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FotoResponse> upload(
            @PathVariable Long relatoId,
            @RequestParam("foto") MultipartFile arquivo) {

        Relato relato = relatoService.findById(relatoId);

        Foto foto = fotoService.salvarFoto(arquivo, relato);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{fotoId}")
                .buildAndExpand(foto.getId())
                .toUri();

        return ResponseEntity
                .created(uri)
                .body(fotoAssembler.toModel(foto));
    }

    /*Busca todas as fotos de um relato
    * GET /relatos/{relatoId}/fotos*/
    @GetMapping
    public ResponseEntity<List<FotoResponse>> listarFotos(
            @PathVariable Long relatoId) {

        List<Foto> fotos = fotoService.listarPorRelato(relatoId);

        return ResponseEntity.ok(fotoAssembler.toCollectionModel(fotos));
    }

    @DeleteMapping("/{fotoId}") /*DELETE /relatos/1/fotos/8*/
    public ResponseEntity<Void> excluir(
            @PathVariable Long relatoId,
            @PathVariable Long fotoId) {

        fotoService.excluirFoto(relatoId, fotoId);

        return ResponseEntity.noContent().build();
    }



//  EXCLUI FOTO APENAS DO BANCO DE DADOS, SEM EXCLUIR O ARQUIVO FISICO
//    @DeleteMapping("/{fotoId}")
//    public ResponseEntity<Void> excluir(
//            @PathVariable Long relatoId,
//            @PathVariable Long fotoId) {
//
//        fotoService.excluirFoto(relatoId, fotoId);
//
//        return ResponseEntity.noContent().build();
//    }
}