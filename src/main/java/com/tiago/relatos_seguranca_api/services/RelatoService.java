package com.tiago.relatos_seguranca_api.services;

import com.tiago.relatos_seguranca_api.infrastructure.dto.response.RelatoResponse;
import com.tiago.relatos_seguranca_api.infrastructure.entity.Relato;
import com.tiago.relatos_seguranca_api.infrastructure.entity.Usuario;
import com.tiago.relatos_seguranca_api.infrastructure.enums.Prioridade;
import com.tiago.relatos_seguranca_api.infrastructure.enums.StatusRelato;
import com.tiago.relatos_seguranca_api.infrastructure.repository.RelatoRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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
    public Relato salvarRelato(Relato relato, Long usuarioId) {

        Usuario usuario = usuarioService.findById(usuarioId);

        relato.setUsuario(usuario);
        relato.setStatus(StatusRelato.ABERTO);
        relato.setDataDoRelato(LocalDateTime.now());

        return relatoRepository.save(relato);
    }

    //@Transactional
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

//    @Transactional
//    public Relato updateRelato(Relato relato) {
//        return relatoRepository.save(relato);
//    }

    @Transactional
    public Relato updateRelato(Long id, Relato relatoAtualizado) {

        Relato relatoExistente = findById(id);

        relatoExistente.setTitulo(relatoAtualizado.getTitulo());
        relatoExistente.setDescricao(relatoAtualizado.getDescricao());
        relatoExistente.setPrioridade(relatoAtualizado.getPrioridade());
        relatoExistente.setStatus(relatoAtualizado.getStatus());

        return relatoRepository.save(relatoExistente);
    }

    /*Futuramente Se relato estiver CONCLUIDO
→    não permite alterar prioridade ou somente Admin/técnico*/
    @Transactional
    public Relato updatePrioridadeRelato(Long id, Prioridade prioridade) {
        Relato relato = findById(id);

        relato.setPrioridade(prioridade);

        return relatoRepository.save(relato);
    }

}
