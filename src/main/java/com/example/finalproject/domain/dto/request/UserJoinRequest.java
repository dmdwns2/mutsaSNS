package com.example.finalproject.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class UserJoinRequest {
    private String userName;
    private String password;
}
