package com.example.finalproject.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;


@Builder
@AllArgsConstructor
@Getter
public class UserJoinRequest {
    @NotBlank(message = "Blank")
    private final String userName;
    @NotBlank(message = "Blank")

    private final String password;
}
