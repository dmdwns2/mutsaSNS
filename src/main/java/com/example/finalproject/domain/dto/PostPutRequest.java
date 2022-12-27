package com.example.finalproject.domain.dto;

import com.example.finalproject.domain.PostEntity;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
public class PostPutRequest {

    private final String title;
    private final String body;

}
