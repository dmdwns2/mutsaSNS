package com.example.finalproject.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CommentPutRequest {
    private final String comment;
}
