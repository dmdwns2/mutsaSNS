package com.example.finalproject.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * Request, Response
 */
@Builder
@AllArgsConstructor
@Getter
public class CommentResponse {

    private final Long commentId;
    private final String comment;
    private final String userName;
    private final LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;

}
