package com.tiago.relatos_seguranca_api.services;

import com.tiago.relatos_seguranca_api.exception.ConflictException;
import com.tiago.relatos_seguranca_api.exception.ResourceNotFoundException;
import com.tiago.relatos_seguranca_api.infrastructure.assembler.UsuarioAssembler;
import com.tiago.relatos_seguranca_api.infrastructure.dto.request.UsuarioUpdateRequest;
import com.tiago.relatos_seguranca_api.infrastructure.dto.response.UsuarioResponse;
import com.tiago.relatos_seguranca_api.infrastructure.entity.Usuario;
import com.tiago.relatos_seguranca_api.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService { /*NÃO SE USA AUTOWIRED*/

    private final UsuarioRepository usuarioRepository;
    private final UsuarioAssembler usuarioAssembler;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public Usuario findById(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Usuário não encontrado. Id: " + id
                ));
    }

    @Transactional
        public void verifyIfEmailAlreadyExists(String email, Long id) {

            Optional<Usuario> usuario = usuarioRepository.findByEmail(email);

            if (usuario.isPresent()
                    && (id == null || !usuario.get().getId().equals(id))) {

                throw new ConflictException(
                        "O e-mail informado já está cadastrado."
                );
            }
        }

        /*Busca Por Nome*/
    public List <UsuarioResponse> findByName(String name){
        return usuarioRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(usuarioAssembler::toModel)
                .toList();
    }


    @Transactional /*criptografa a senha antes de salvar*/
    public Usuario save(Usuario usuario) {

        verifyIfEmailAlreadyExists(usuario.getEmail(), null);

        usuario.setPassword(
                passwordEncoder.encode(usuario.getPassword())
        );

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
