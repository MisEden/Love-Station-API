package org.eden.lovestation.exception;

import lombok.Data;

@Data
public class APIValidationError extends APISubError {
    private String field;
    private String message;

    public APIValidationError(String field, String message) {
        this.field = field;
        this.message = message;
    }
}
