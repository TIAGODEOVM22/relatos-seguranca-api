package com.tiago.relatos_seguranca_api.services;

import com.tiago.relatos_seguranca_api.infrastructure.assembler.UsuarioAssembler;
import com.tiago.relatos_seguranca_api.infrastructure.dto.request.UsuarioUpdateRequest;
import com.tiago.relatos_seguranca_api.infrastructure.dto.response.UsuarioResponse;
import com.tiago.relatos_seguranca_api.infrastructure.entity.Usuario;
import com.tiago.relatos_seguranca_api.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService { /*NÃO SE USA AUTOWIRED*/

    private final UsuarioRepository usuarioRepository;
    private final UsuarioAssembler usuarioAssembler;

    @Transactional(readOnly = true)
    public Usuario findById(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Object not found. Id: " + id +
                                ", Type: " + Usuario.class.getSimpleName()));
    }

    private void verifyIfEmailAlreadyExists(String email) {
        usuarioRepository.findByEmail(email)
                .ifPresent(user -> {
                    throw new DataIntegrityViolationException(
                            "Email [" + email + "] already exists");
                });
    }

    @Transactional
    public Usuario save(Usuario usuario) {
        verifyIfEmailAlreadyExists(usuario.getEmail());
        return usuarioRepository.save(usuario);
    }

    @Transactional(readOnly = true)
    public List<UsuarioResponse> findAll() {
        return usuarioAssembler.toCollectionModel(usuarioRepository.findAll());
    }

    @Transactional
    public Usuario updateUserNameAndEmail(Long id, UsuarioUpdateRequest request) {

        Usuario usuario = findById(id);

        usuarioAssembler.copyToDomainObject(request, usuario);

        return usuarioRepository.save(usuario);
    }

    @Transactional
    public Usuario updateProfiles(Long id, Usuario usuarioAtualizado) {

        Usuario usuario = findById(id);

        usuario.setProfiles(usuarioAtualizado.getProfiles());

        return usuarioRepository.save(usuario);
    }

    @Transactional
    public void delete(Long id) {
        Usuario usuario = findById(id);
        usuarioRepository.delete(usuario);
    }
}
