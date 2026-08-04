package com.atlas.domain.exception;

public class AtlasException extends RuntimeException {

    private final String errorCode;

    public AtlasException(String message) {
        super(message);
        this.errorCode = "ATLAS_INTERNAL_ERROR";
    }

    public AtlasException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public AtlasException(String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
