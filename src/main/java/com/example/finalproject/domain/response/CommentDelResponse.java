package com.example.finalproject.domain.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CommentDelResponse {
    private String message;
    private final Long id;
}
