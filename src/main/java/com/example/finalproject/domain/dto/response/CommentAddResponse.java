package com.example.finalproject.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class CommentAddResponse {
    private final String comment;
    private final String userName;
    private final LocalDateTime createdAt;
}
