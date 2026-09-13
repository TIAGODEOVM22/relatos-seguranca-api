package com.tiago.relatos_seguranca_api.security.controller;

import com.tiago.relatos_seguranca_api.security.controller.AuthController;
import com.tiago.relatos_seguranca_api.security.JWTAuthenticationImpl;
import com.tiago.relatos_seguranca_api.security.request.AuthenticationRequest;
import com.tiago.relatos_seguranca_api.security.request.RefreshTokenRequest;
import com.tiago.relatos_seguranca_api.security.response.AuthenticationResponse;
import com.tiago.relatos_seguranca_api.security.response.RefreshTokenResponse;
import com.tiago.relatos_seguranca_api.security.service.RefreshTokenService;
import com.tiago.relatos_seguranca_api.utils.JWTUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {

    private final JWTUtils jwtUtils;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final RefreshTokenService refreshTokenService;

    @Override
    public ResponseEntity<AuthenticationResponse> authenticate(final AuthenticationRequest request) throws Exception {
        return ResponseEntity.ok().body(
                new JWTAuthenticationImpl(jwtUtils, authenticationConfiguration.getAuthenticationManager())
                        .authenticate(request)
                        .withRefreshToken(refreshTokenService.save(request.email()).getId())
        );
    }

    @Override
    public ResponseEntity<RefreshTokenResponse> refreshToken(RefreshTokenRequest request) {
        return ResponseEntity.ok().body(
                refreshTokenService.refreshToken(request.refreshToken())
        );
    }
}