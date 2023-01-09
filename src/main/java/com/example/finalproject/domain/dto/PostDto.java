package com.example.finalproject.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class PostDto{
    @Id
    @GeneratedValue
    private final Long id;
    private final String title;
    private final String body;
    private final String userName;
    private final LocalDateTime createdAt;
    private final LocalDateTime lastModifiedAt;
}