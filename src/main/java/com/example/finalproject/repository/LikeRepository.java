package com.example.finalproject.repository;

import com.example.finalproject.domain.LikeEntity;
import com.example.finalproject.domain.PostEntity;
import com.example.finalproject.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<LikeEntity, Long> {
    Long countByPostId(Long postId);
    Optional<LikeEntity> findByPostAndUser(PostEntity post, UserEntity user);
}
