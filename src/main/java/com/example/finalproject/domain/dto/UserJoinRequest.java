package com.example.finalproject.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserJoinRequest {
    private final String userName;
    private final String password;
}
