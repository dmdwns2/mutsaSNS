package com.example.finalproject.service;

import com.example.finalproject.domain.AlarmEntity;
import com.example.finalproject.domain.dto.AlarmDto;
import com.example.finalproject.repository.AlarmRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AlarmService {

    private final AlarmRepository repository;
    public Page<AlarmDto> alarmPage(Pageable pageable) {

        Page<AlarmEntity> visits = repository.findAll(pageable);

        return new PageImpl<>(visits.stream()
                .map(AlarmEntity::toResponse)
                .collect(Collectors.toList()));
    }
}
