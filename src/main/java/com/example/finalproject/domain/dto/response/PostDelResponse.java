package com.example.finalproject.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PostDelResponse {
    private final Long postId;
    private final String message;
}
