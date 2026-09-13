package com.tiago.relatos_seguranca_api.security.exception;

public class RefreshTokenExpired extends RuntimeException {
    public RefreshTokenExpired(String message) {
        super(message);
    }
}