package com.example.finalproject.domain.dto;

import com.example.finalproject.domain.PostEntity;
import com.example.finalproject.domain.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class CommentDto{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final Long id;
    private final String comment;
    private final LocalDateTime createdAt;
    private final LocalDateTime lastModifiedAt;
    private final UserEntity user;
    private final PostEntity post;
}
