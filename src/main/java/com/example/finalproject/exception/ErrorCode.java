package com.example.finalproject.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

    @AllArgsConstructor
    @Getter
    public enum ErrorCode {
        DUPLICATED_USER_NAME(HttpStatus.CONFLICT, "User name is duplicated."),
        NOT_FOUND(HttpStatus.NOT_FOUND, "Hospital Not Found."),
        NOT_FOUND_HOSPITAL(HttpStatus.NOT_FOUND, "Hospital Not Found."),
        NOT_FOUND_USER(HttpStatus.NOT_FOUND, "User Not Found."),
        INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "");

        private HttpStatus status;
        private String message;
    }