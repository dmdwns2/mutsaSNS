package com.example.finalproject.domain.dto;

import com.example.finalproject.domain.User;
import com.example.finalproject.domain.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Getter
@Builder
@AllArgsConstructor
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final Long id;

    private final String title;

    private final String body;

    private final String userName;

}