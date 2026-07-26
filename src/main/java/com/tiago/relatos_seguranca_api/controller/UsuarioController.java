package com.tiago.relatos_seguranca_api.controller;


import com.tiago.relatos_seguranca_api.infrastructure.assembler.UsuarioAssembler;
import com.tiago.relatos_seguranca_api.infrastructure.dto.request.UsuarioCreateRequest;
import com.tiago.relatos_seguranca_api.infrastructure.dto.request.UsuarioProfileRequest;
import com.tiago.relatos_seguranca_api.infrastructure.dto.request.UsuarioUpdateRequest;
import com.tiago.relatos_seguranca_api.infrastructure.dto.response.UsuarioResponse;
import com.tiago.relatos_seguranca_api.infrastructure.entity.Usuario;
import com.tiago.relatos_seguranca_api.infrastructure.repository.UsuarioRepository;
import com.tiago.relatos_seguranca_api.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioAssembler usuarioAssembler;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    //@PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")    /*Buscar usuario por id*/
    public ResponseEntity<UsuarioResponse> getUsuarioById(@PathVariable Long id) {
        Usuario usuario = usuarioService.findById(id);
        return ResponseEntity.ok(usuarioAssembler.toModel(usuario));
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @PostMapping    /*Criar usuario*/
    public ResponseEntity<Void> createUser( @RequestBody @Valid UsuarioCreateRequest usuarioCreateRequest) {
        Usuario usuario = usuarioAssembler.toDomainObject(usuarioCreateRequest);
        usuarioService.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED.value()).build();

    }

    //@PreAuthorize("hasRole('ADMIN')")
    @GetMapping /*Buscar todos usuarios*/
    public ResponseEntity<List<UsuarioResponse>> getAllUsuarios() {
        List<UsuarioResponse> usuarios = usuarioService.findAll();
        return ResponseEntity.ok(usuarios);
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}") // Atualiza Nome e Email
    public ResponseEntity<UsuarioResponse> updateUserNameAndEmail(
            @PathVariable Long id, @RequestBody @Valid UsuarioUpdateRequest usuarioUpdateRequest) {
        Usuario usuarioAtualizado = usuarioService.updateUserNameAndEmail(id, usuarioUpdateRequest);
        return ResponseEntity.ok(usuarioAssembler.toModel(usuarioAtualizado));
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")/*Deletar usuario*/
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        usuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }

   //@PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}/profiles")
    public ResponseEntity<UsuarioResponse> updateProfiles(
            @PathVariable Long id,
            @RequestBody @Valid UsuarioProfileRequest request) {

        Usuario usuario = usuarioAssembler.toDomainObject(request);
        Usuario usuarioAtualizado = usuarioService.updateProfiles(id, usuario);
        return ResponseEntity.ok(usuarioAssembler.toModel(usuarioAtualizado));
    }
}
