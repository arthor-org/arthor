package com.arthor.core.integration.route.exception;

public class RouteOpenApiException extends RuntimeException {

    public RouteOpenApiException(Throwable cause) {
        super(cause);
    }

    public RouteOpenApiException(String message) {
        super(message);
    }
}
