package com.arthor.core.registry.exception;

public class RegistryOpenApiException extends RuntimeException {

    public RegistryOpenApiException(Throwable cause) {
        super(cause);
    }

    public RegistryOpenApiException(String message) {
        super(message);
    }
}
