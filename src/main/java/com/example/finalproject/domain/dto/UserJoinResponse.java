package com.example.finalproject.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserJoinResponse {

    private Long id;
    private String userName;

    public static UserJoinResponse fromUser(UserDto user) {
        return new UserJoinResponse();
    }
}
