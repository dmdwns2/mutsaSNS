package com.example.finalproject.repository;

import com.example.finalproject.domain.dto.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaPostRepository extends JpaRepository<PostEntity, Long> {
}
