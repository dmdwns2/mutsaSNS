package com.example.finalproject.service;

import com.example.finalproject.domain.AlarmEntity;
import com.example.finalproject.domain.UserEntity;
import com.example.finalproject.domain.dto.AlarmDto;
import com.example.finalproject.exception.AppException;
import com.example.finalproject.exception.ErrorCode;
import com.example.finalproject.repository.AlarmRepository;
import com.example.finalproject.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class AlarmService {
    private final AlarmRepository alarmRepository;
    private final UserRepository userRepository;

    public Page<AlarmDto> alarmPage(Pageable pageable, String userName) {
        UserEntity user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND, ErrorCode.USERNAME_NOT_FOUND.getMessage()));
        Page<AlarmEntity> alarms = alarmRepository.findAlarmEntitiesByUser(user, pageable);

        List<AlarmDto> alarmDtoList = new ArrayList<>();
        for (AlarmEntity alarm : alarms) {
            AlarmDto alarmDto = new AlarmDto();
            alarmDto.setId(alarm.getId());
            alarmDto.setAlarmType(alarm.getAlarmType());
            alarmDto.setFromUserId(alarm.getFromUserId());
            alarmDto.setTargetId(alarm.getTargetId());
            alarmDto.setText(alarm.getText());
            alarmDto.setCreatedAt(alarm.getCreatedAt());
            alarmDto.setLastModifiedAt(alarm.getLastModifiedAt());
            alarmDtoList.add(alarmDto);
        }

        return new PageImpl<>(alarmDtoList, pageable, alarms.getTotalElements());
    }

}