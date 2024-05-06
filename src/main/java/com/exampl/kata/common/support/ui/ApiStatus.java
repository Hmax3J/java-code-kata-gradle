package com.exampl.kata.common.support.ui;

public enum ApiStatus {
    OK(200),
    CREATED(201),
    BAD_REQUEST(400),
    UNAUTHORIZED(401),
    FORBIDDEN(403),
    NOT_FOUND(404),
    CONFLICT(409),
    GONE(410),
    INTERNAL_SERVER_ERROR(500),
    BAD_GATEWAY(503),
    GATEWAY_TIMEOUT(504);

    private final int status;

    ApiStatus(int status) {
        this.status = status;
    }
}
