package com.example.finalproject.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PostAddResponse {
    private final Long id;
    private final String title;
    private final String body;
}
