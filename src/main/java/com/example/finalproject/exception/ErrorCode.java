package com.example.finalproject.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

    @AllArgsConstructor
    @Getter
    public enum ErrorCode {
        DUPLICATED_USER_NAME(HttpStatus.CONFLICT, "UserName이 중복됩니다."),
        USERNAME_NOT_FOUND(HttpStatus.NOT_FOUND, "Not founded"),
        INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "password does not match"),
        INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "잘못된 토큰입니다."),
        POST_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 포스트가 없습니다."),
        COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 댓글이 없습니다."),
        DATABASE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "DB에러");


        private HttpStatus status;
        private String message;
    }