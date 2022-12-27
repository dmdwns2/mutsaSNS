package com.example.finalproject.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PostDelResponse {
    private final Long id;
    private final String message;
}