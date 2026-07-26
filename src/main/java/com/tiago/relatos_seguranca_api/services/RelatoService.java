package com.tiago.relatos_seguranca_api.services;

import com.tiago.relatos_seguranca_api.infrastructure.dto.response.RelatoResponse;
import com.tiago.relatos_seguranca_api.infrastructure.entity.Relato;
import com.tiago.relatos_seguranca_api.infrastructure.entity.Usuario;
import com.tiago.relatos_seguranca_api.infrastructure.enums.StatusRelato;
import com.tiago.relatos_seguranca_api.infrastructure.repository.RelatoRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class RelatoService {
    @Autowired
    private final ModelMapper modelMapper;

    @Autowired
    private final RelatoRepository relatoRepository;

    @Autowired
    private final UsuarioService usuarioService;

    @Transactional
    public Relato salvarRelato(Relato relato) {

        Usuario usuario = usuarioService.findById(3L);

        relato.setUsuario(usuario);
        relato.setStatus(StatusRelato.ABERTO);
        relato.setDataDoRelato(LocalDateTime.now());

        // Quando implementar o Spring Security
        // Usuario usuarioLogado = usuarioAutenticado();
        // relato.setUsuario(usuarioLogado);

        return relatoRepository.save(relato);
    }

    @Transactional
    public Relato findById (Long id) {
        return relatoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Object not found. Id: " + id + ", Type: " + Relato.class.getSimpleName()
                ));
    }

    @Transactional
    public List<RelatoResponse> findAll() {
       List<Relato> relatos = relatoRepository.findAll();
       return relatos.stream()
               .map(relato -> modelMapper.map(relato, RelatoResponse.class))
               .collect(Collectors.toList());

    }

    @Transactional
    public void deletarRelato(Long id) {
        Relato relato = findById(id);
        relatoRepository.deleteById(id);
    }

    @Transactional
    public Relato updateRelato(Long id, Relato relatoAtualizado) {

        Relato relatoExistente = findById(id);

        relatoExistente.setTitulo(relatoAtualizado.getTitulo());
        relatoExistente.setDescricao(relatoAtualizado.getDescricao());
        relatoExistente.setStatus(relatoAtualizado.getStatus());
        relatoExistente.setPrioridade(relatoAtualizado.getPrioridade());

        return relatoRepository.save(relatoExistente);
    }
}
