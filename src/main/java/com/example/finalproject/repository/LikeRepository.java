package com.example.finalproject.repository;

import com.example.finalproject.domain.LikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<LikeEntity, Long> {
    Long countByPostId(Long postId);
}
