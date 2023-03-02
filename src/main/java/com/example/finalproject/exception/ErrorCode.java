package com.example.finalproject.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

    @AllArgsConstructor
    @Getter
    public enum ErrorCode {
        DUPLICATED_USER_NAME(HttpStatus.CONFLICT, "UserName이 중복됩니다."),
        USERNAME_NOT_FOUND(HttpStatus.NOT_FOUND, "UserName을 찾을 수 없습니다."),
        INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "password does not match"),
        INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "잘못된 토큰입니다."),
        POST_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 포스트가 없습니다."),
        POST_OR_USER_NOT_FOUND(HttpStatus.NOT_FOUND, "유저 혹은 게시물을 찾을 수 없습니다."),
        COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 댓글이 없습니다."),
        DATABASE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "DB에러"),
        EMPTY_CONTENT(HttpStatus.NO_CONTENT, "내용을 입력해주세요"),
        INVALID_PERMISSION(HttpStatus.UNAUTHORIZED, "인증 실패"),
        INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"500 서버 에러 발생");


        private HttpStatus status;
        private String message;
    }