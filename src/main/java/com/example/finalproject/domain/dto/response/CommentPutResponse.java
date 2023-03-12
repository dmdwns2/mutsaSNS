package com.example.finalproject.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Builder
public class CommentPutResponse {
    private final long id;
    private final String comment;
    private final String userName;
    private final long postId;
    private final LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}

