package com.example.finalproject.repository;

import com.example.finalproject.domain.AlarmEntity;
import com.example.finalproject.domain.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmRepository extends JpaRepository<AlarmEntity,Long> {
    Page<AlarmEntity> findAlarmEntitiesByUser(UserEntity userName, Pageable pageable);
}
