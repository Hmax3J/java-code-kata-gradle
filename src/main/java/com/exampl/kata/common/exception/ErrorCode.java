package com.exampl.kata.common.exception;

import com.exampl.kata.common.support.ui.ApiStatus;

public enum ErrorCode {
    // auth, user
    USERNAME_NOT_FOUND("", ApiStatus.NOT_FOUND),

    // fish-shaped bread (fishbread)
    FISHBREAD_QUERY_MAPPING_ERROR(
            "붕어빵 조회 후 매핑에 이상이 생겼습니다.",
            ApiStatus.INTERNAL_SERVER_ERROR
    ),

    // order
    ;

    private final String message;
    private final ApiStatus status;

    ErrorCode(String message, ApiStatus status) {
        this.message = message;
        this.status = status;
    }

    public String defaultMessage() {
        return message;
    }

    public ApiStatus defaultApiStatus() {
        return status;
    }
}
