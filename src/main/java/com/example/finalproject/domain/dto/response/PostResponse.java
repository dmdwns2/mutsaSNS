package com.example.finalproject.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@Getter
public class PostResponse {
    private final Long postId;
    private final String title;
    private final String body;
    private final String userName;
    private final LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;
}
