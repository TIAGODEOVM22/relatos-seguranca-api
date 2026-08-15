package com.tiago.relatos_seguranca_api.exception;

//Classe para a exceção de armazenamento de arquivos,
public class FileStorageException extends RuntimeException {

    public FileStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}