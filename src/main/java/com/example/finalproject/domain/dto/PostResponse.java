package com.example.finalproject.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class PostResponse {
    private final Long id;
    private final String title;
    private final String body;
    private final String userName;
}
