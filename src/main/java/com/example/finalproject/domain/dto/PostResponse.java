package com.example.finalproject.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@Getter
public class PostResponse {


    private final Long id;
    private final String title;
    private final String body;
    private final String userName;
    private final LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;


}
