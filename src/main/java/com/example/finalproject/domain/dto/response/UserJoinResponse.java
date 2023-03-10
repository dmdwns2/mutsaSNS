package com.example.finalproject.domain.dto.response;

import com.example.finalproject.controller.UserRestController;
import com.example.finalproject.domain.dto.UserDto;
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
        return new UserJoinResponse(user.getId(), user.getUserName());
    }
}
