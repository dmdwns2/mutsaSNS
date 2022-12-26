package com.example.finalproject.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

    @AllArgsConstructor
    @Getter
    public enum ErrorCode {
        DUPLICATED_USER_NAME(HttpStatus.CONFLICT, "User name is duplicated"),
        INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error"),
        USER_NOT_FOUNDED(HttpStatus.NOT_FOUND, ""),
        NOT_FOUNDED(HttpStatus.NOT_FOUND, "plz check your userName"),
        INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "password does not match");



        private HttpStatus status;
        private String message;
    }