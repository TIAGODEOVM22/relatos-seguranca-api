package com.tiago.relatos_seguranca_api.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty
    private List<FieldError> errors = new ArrayList<>();

//    public void addError(String fieldName, String message) {
//        errors.add(new FieldError(fieldName, message));
//    }

    public ValidationError(LocalDateTime timestamp,
                           Integer status,
                           String error,
                           String path) {
        super(timestamp, status, error, path);
    }
    @Getter
    static class FieldError {
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
