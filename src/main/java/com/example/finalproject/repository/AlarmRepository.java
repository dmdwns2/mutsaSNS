package com.example.finalproject.repository;

import com.example.finalproject.domain.AlarmEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmRepository extends JpaRepository<AlarmEntity,Long> {
}
