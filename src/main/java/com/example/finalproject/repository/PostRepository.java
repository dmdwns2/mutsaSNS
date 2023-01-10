package com.example.finalproject.repository;

import com.example.finalproject.domain.PostEntity;
import com.example.finalproject.domain.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<PostEntity, Long> {
    Page<PostEntity> findPostEntitiesByUserName(UserEntity user, Pageable pageable);
}
