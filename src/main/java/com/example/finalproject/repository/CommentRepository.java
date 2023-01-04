package com.example.finalproject.repository;

import com.example.finalproject.domain.CommentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    Page<CommentEntity> findCommentEntitiesByPostId(Long postId, Pageable pageable);
}
