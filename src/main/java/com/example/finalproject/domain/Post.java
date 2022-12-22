package com.example.finalproject.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Post {
    private final long id;
    private final String title;
    private final String body;
    private final String userName;
}