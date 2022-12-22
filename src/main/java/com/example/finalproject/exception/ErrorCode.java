package com.example.finalproject.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

    @AllArgsConstructor
    @Getter
    public enum ErrorCode {
        DUPLICATED_USER_NAME(HttpStatus.CONFLICT, "UserName is duplicated."),
        NOT_FOUND(HttpStatus.NOT_FOUND, "UserName Not Found."),
        INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "");

        private HttpStatus status;
        private String message;
    }