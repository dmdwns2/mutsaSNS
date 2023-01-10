package com.example.finalproject.domain.dto;

import com.example.finalproject.domain.AlarmEntity;
import com.example.finalproject.domain.UserEntity;
import com.example.finalproject.enums.AlarmType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@Getter
public class AlarmDto {
    private Long id;
    private AlarmType alarmType;
    private Long fromUserId;
    private Long targetId;
    private String text;
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;

    public static AlarmEntity toEntity(AlarmType alarmType, UserEntity user, Long fromUserId, Long targetId){
        return AlarmEntity.builder()
                .alarmType(alarmType)
                .user(user)
                .fromUserId(fromUserId)
                .targetId(targetId)
                .text(alarmType.getText())
                .build();
    }

}
