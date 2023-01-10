package com.example.finalproject.service;

import com.example.finalproject.domain.AlarmEntity;
import com.example.finalproject.domain.UserEntity;
import com.example.finalproject.domain.dto.AlarmDto;
import com.example.finalproject.repository.AlarmRepository;
import com.example.finalproject.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AlarmService {

    private final AlarmRepository repository;
    private final UserRepository userRepository;
    public Page<AlarmDto> alarmPage(Pageable pageable, String userName) {

        Optional<UserEntity> optUser =  userRepository.findByUserName(userName);
        UserEntity user = optUser.orElse(null);
        Page<AlarmEntity> visits = repository.findAlarmEntitiesByUser(user, pageable);

        return new PageImpl<>(visits.stream()
                .map(AlarmEntity::toResponse)
                .collect(Collectors.toList()));
    }
}
