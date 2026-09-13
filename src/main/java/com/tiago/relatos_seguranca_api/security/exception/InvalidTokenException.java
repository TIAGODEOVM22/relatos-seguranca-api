package com.tiago.relatos_seguranca_api.security.exception;

public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException(String message) {

        super(message);
    }
}
