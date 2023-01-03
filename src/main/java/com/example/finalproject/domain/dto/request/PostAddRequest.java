package com.example.finalproject.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Request, Response
 */
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
public class PostAddRequest {
    private final String title;
    private final String body;
}
