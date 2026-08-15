package com.tiago.relatos_seguranca_api.exception;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class ValidationError extends StandardError implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private List<FieldError> errors = new ArrayList<>();

    public ValidationError(
            LocalDateTime timestamp,
            Integer status,
            String error,
            String message,
            String path) {

        super(timestamp, status, error, message, path);
    }

    @Getter
    public static class FieldError {

        private String fieldName;
        private String message;

        public FieldError() {
        }

        public FieldError(String fieldName, String message) {
            this.fieldName = fieldName;
            this.message = message;
        }
    }
}
