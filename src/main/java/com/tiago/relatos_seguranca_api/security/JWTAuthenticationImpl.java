package com.tiago.relatos_seguranca_api.security;

import com.tiago.relatos_seguranca_api.security.response.AuthenticationResponse;
import com.tiago.relatos_seguranca_api.security.request.AuthenticationRequest;
import com.tiago.relatos_seguranca_api.utils.JWTUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Log4j2
@RequiredArgsConstructor
public class JWTAuthenticationImpl {

    private final JWTUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse authenticate(final AuthenticationRequest request) {
        try {
            log.info("Authenticating user: {}", request.email());
            final var authResult = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.email(), request.password())
            );
            return buildAuthenticationResponse((UserDetailsDTO) authResult.getPrincipal());
        } catch (BadCredentialsException ex) {
            log.error("Error on authenticate user: {}", request.email());
            throw new BadCredentialsException("Email or password invalid");
        }
    }

    protected AuthenticationResponse buildAuthenticationResponse(final UserDetailsDTO detailsDTO) {
        log.info("Successfully authenticated user: {}", detailsDTO.getUsername());
        final var token = jwtUtils.generateToken(detailsDTO);
        return AuthenticationResponse.builder()
                .type("Bearer")
                .token(token)
                .build();
    }
}