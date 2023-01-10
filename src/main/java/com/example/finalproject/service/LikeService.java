package com.example.finalproject.service;

import com.example.finalproject.domain.LikeEntity;
import com.example.finalproject.domain.PostEntity;
import com.example.finalproject.domain.UserEntity;
import com.example.finalproject.repository.LikeRepository;
import com.example.finalproject.repository.PostRepository;
import com.example.finalproject.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@AllArgsConstructor
public class LikeService {

    private final LikeRepository repository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public void like_on(Long postId, String userName) {

        Optional<PostEntity> optPost = postRepository.findById(postId);
        PostEntity post = optPost.orElse(null);
        Optional<UserEntity> optUser = userRepository.findByUserName(userName);
        UserEntity user = optUser.orElse(null);

        post.setId(postId);
        user.setUserName(userName);

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
