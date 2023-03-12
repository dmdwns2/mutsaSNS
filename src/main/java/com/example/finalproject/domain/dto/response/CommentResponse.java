package com.example.finalproject.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@Getter
public class CommentResponse {
    private final Long id;
    private final String comment;
    private final String userName;
    private final Long postId;
    private final LocalDateTime createdAt;
}
