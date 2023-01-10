package com.example.finalproject.service;

import com.example.finalproject.domain.LikeEntity;
import com.example.finalproject.domain.PostEntity;
import com.example.finalproject.domain.UserEntity;
import com.example.finalproject.repository.LikeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LikeService {

    private final LikeRepository repository;

    private final UserEntity user;
    private final PostEntity post;
    public void like_on(Long postId, String userName) {

        repository.save(LikeEntity.builder()
                .user(user)
                .post(post)
                .build()
        );
    }

    public Long getLike(Long postId) {

        return repository.countByPostId(postId);
    }
}
