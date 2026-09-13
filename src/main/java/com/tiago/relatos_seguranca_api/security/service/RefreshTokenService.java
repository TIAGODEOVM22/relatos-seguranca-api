package com.tiago.relatos_seguranca_api.security.service;

import com.tiago.relatos_seguranca_api.security.UserDetailsDTO;
import com.tiago.relatos_seguranca_api.security.exception.RefreshTokenExpired;
import com.tiago.relatos_seguranca_api.security.exception.RefreshTokenNotFoundException;
import com.tiago.relatos_seguranca_api.security.models.RefreshToken;
import com.tiago.relatos_seguranca_api.security.repository.RefreshTokenRepository;
import com.tiago.relatos_seguranca_api.security.response.RefreshTokenResponse;
import com.tiago.relatos_seguranca_api.utils.JWTUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static java.time.LocalDateTime.now;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    @Value("${jwt.expiration-sec.refresh-token}")
    private Long refreshTokenExpirationSec;

    private final RefreshTokenRepository repository;
    private final UserDetailsService userDetailsService;
    private final JWTUtils jwtUtils;

    public RefreshToken save(final String username) {
        return repository.save(
                RefreshToken.builder()
                        .id(UUID.randomUUID().toString())
                        .createdAt(now())
                        .expiresAt(now().plusSeconds(refreshTokenExpirationSec))
                        .username(username)
                        .build()
        );
    }

    public RefreshTokenResponse refreshToken(final String refreshTokenId) {

        final var refreshToken = repository.findById(refreshTokenId)
                .orElseThrow(() -> new RefreshTokenNotFoundException(
                        "Refresh token not found. Id: " + refreshTokenId
                ));

        if (refreshToken.getExpiresAt().isBefore(now())) {
            throw new RefreshTokenExpired(
                    "Refresh token expired. Id: " + refreshTokenId
            );
        }

        return new RefreshTokenResponse(
                jwtUtils.generateToken(
                        (UserDetailsDTO) userDetailsService
                                .loadUserByUsername(refreshToken.getUsername())
                )
        );
    }
}