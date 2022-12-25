package com.example.finalproject.domain.dto;

import com.example.finalproject.domain.UserEntity;
import com.example.finalproject.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class UserDto {
    private final Long id;
    private final String userName;
    private final String password;
    private final UserRole userRole;

    public static UserDto fromEntity(UserEntity entity){
        return new UserDto(entity.getId(),
                entity.getUserName(),
                entity.getPassword(),
                entity.getRole());
    }

}