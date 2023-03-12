package com.example.finalproject.domain.dto;

import com.example.finalproject.enums.AlarmType;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AlarmDto {
    private Long id;
    private AlarmType alarmType;
    private Long fromUserId;
    private Long targetId;
    private String text;
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;
}
