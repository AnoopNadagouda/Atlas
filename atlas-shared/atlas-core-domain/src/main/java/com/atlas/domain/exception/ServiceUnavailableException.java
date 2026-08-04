package com.atlas.domain.exception;

public class ServiceUnavailableException extends AtlasException {

    public ServiceUnavailableException(String serviceName, String reason) {
        super("SERVICE_UNAVAILABLE", String.format("Service %s is unavailable: %s", serviceName, reason));
    }
}
