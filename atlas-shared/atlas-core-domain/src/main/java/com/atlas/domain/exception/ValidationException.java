package com.atlas.domain.exception;

public class ValidationException extends AtlasException {

    public ValidationException(String message) {
        super("INVALID_INPUT", message);
    }
}
