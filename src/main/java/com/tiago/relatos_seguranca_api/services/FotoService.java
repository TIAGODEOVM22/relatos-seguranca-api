package com.tiago.relatos_seguranca_api.services;

import com.tiago.relatos_seguranca_api.exception.ConflictException;
import com.tiago.relatos_seguranca_api.exception.FileStorageException;
import com.tiago.relatos_seguranca_api.exception.ResourceNotFoundException;
import com.tiago.relatos_seguranca_api.infrastructure.entity.Foto;
import com.tiago.relatos_seguranca_api.infrastructure.entity.Relato;
import com.tiago.relatos_seguranca_api.infrastructure.repository.FotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FotoService {

    private final FotoRepository fotoRepository;

    private final RelatoService relatoService;

    /*Variavel de ambiente definida no properties*/
    @Value("${app.storage.fotos}")
    private String diretorioFotos;

    @Transactional
    public Foto salvarFoto(MultipartFile arquivo, Relato relato) {

        long quantidadeFotos = fotoRepository.countByRelatoId(relato.getId());

        if (quantidadeFotos >= 2) {
            throw new ConflictException(
                    "O relato já possui o limite máximo de 2 fotos."
            );
        }

        try {

            Path diretorio = Paths.get(diretorioFotos);

            Files.createDirectories(diretorio);

            String nomeArquivo =
                    UUID.randomUUID() + "_" + arquivo.getOriginalFilename();

            Path caminhoArquivo = diretorio.resolve(nomeArquivo);

            Files.copy(
                    arquivo.getInputStream(),
                    caminhoArquivo,
                    StandardCopyOption.REPLACE_EXISTING
            );

            Foto foto = new Foto();

            foto.setNomeArquivo(arquivo.getOriginalFilename());
            foto.setCaminhoArquivo(caminhoArquivo.toString());
            foto.setContentType(arquivo.getContentType());
            foto.setTamanho(arquivo.getSize());
            foto.setRelato(relato);

            return fotoRepository.save(foto);

        } catch (IOException e) {

            throw new RuntimeException(
                    "Não foi possível salvar a foto.",
                    e
            );
        }
    }

    @Transactional(readOnly = true)
    public List<Foto> listarPorRelato(Long relatoId) {

        Relato relato = relatoService.findById(relatoId);

        return fotoRepository.findByRelato(relato);
    }

    @Transactional /*Exclui foto do BD e do diretório de armazenamento*/
    public void excluirFoto(Long relatoId, Long fotoId) {

        Relato relato = relatoService.findById(relatoId);

        Foto foto = fotoRepository.findById(fotoId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Foto não encontrada. Id: " + fotoId
                ));

        if (!foto.getRelato().getId().equals(relato.getId())) {
            throw new ResourceNotFoundException(
                    "A foto não pertence ao relato informado. Id: " + fotoId
            );
        }

        try {
            Path caminho = Paths.get(foto.getCaminhoArquivo());
            Files.deleteIfExists(caminho);

        } catch (IOException e) {
            throw new FileStorageException(
                    "Não foi possível excluir o arquivo da foto. Id: " + fotoId,
                    e
            );
        }
//    @Transactional exclui apenas do banco de dados, mas não do diretório de armazenamento
//    public void excluirFoto(Long relatoId, Long fotoId) {
//        Relato relato = relatoService.findById(relatoId);
//        Foto foto = fotoRepository.findById(fotoId)
//                .orElseThrow(() -> new RuntimeException("Foto não encontrada. Id: " + fotoId));
//
//        if (!foto.getRelato().getId().equals(relato.getId())) {
//            throw new RuntimeException(
//                    "A foto não pertence ao relato informado."
//            );
//        }
//
//        fotoRepository.delete(foto);
//    }

//    @Transactional
//    public Foto salvarFoto(MultipartFile arquivo, Relato relato) {
//
//        try {
//
//            Path diretorio = Paths.get(diretorioFotos);
//
//            Files.createDirectories(diretorio);
//
//            String nomeArquivo =
//                    UUID.randomUUID() + "_" + arquivo.getOriginalFilename();
//
//            Path caminhoArquivo = diretorio.resolve(nomeArquivo);
//
//            Files.copy(
//                    arquivo.getInputStream(),
//                    caminhoArquivo,
//                    StandardCopyOption.REPLACE_EXISTING
//            );
//
//            Foto foto = new Foto();
//
//            foto.setNomeArquivo(arquivo.getOriginalFilename());
//            foto.setCaminhoArquivo(caminhoArquivo.toString());
//            foto.setContentType(arquivo.getContentType());
//            foto.setTamanho(arquivo.getSize());
//            foto.setRelato(relato);
//
//            return fotoRepository.save(foto);
//
//        } catch (IOException e) {
//
//            throw new RuntimeException(
//                    "Não foi possível salvar a foto.",
//                    e
//            );
//        }
//    }
    }
}