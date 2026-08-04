package com.tiago.relatos_seguranca_api.exception;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException (String message) {

        super(message);
    }
}
