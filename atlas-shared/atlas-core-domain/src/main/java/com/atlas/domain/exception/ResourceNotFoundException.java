package com.atlas.domain.exception;

public class ResourceNotFoundException extends AtlasException {

    public ResourceNotFoundException(String resourceName, String identifier) {
        super("RESOURCE_NOT_FOUND", String.format("%s not found with identifier: %s", resourceName, identifier));
    }
}
