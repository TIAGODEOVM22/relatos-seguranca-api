package com.tiago.relatos_seguranca_api.security.service;

import com.tiago.relatos_seguranca_api.security.UserDetailsDTO;
import com.tiago.relatos_seguranca_api.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        final var entity = usuarioRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found: " + email
                        ));

        return UserDetailsDTO.builder()
                .id(String.valueOf(entity.getId()))
                .name(entity.getName())
                .userName(entity.getEmail())
                .password(entity.getPassword())
                .authorities(
                        entity.getProfiles().stream()
                                .map(x -> new SimpleGrantedAuthority(x.getDescription()))
                                .collect(Collectors.toSet())
                )
                .build();
    }
}
