package com.example.finalproject.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PostPutResponse {
    private final Long postId;
    private final String title;
    private final String body;
    private final String userName;
    private final String message;
}
