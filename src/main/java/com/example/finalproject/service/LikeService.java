package com.example.finalproject.service;

import com.example.finalproject.domain.AlarmEntity;
import com.example.finalproject.domain.LikeEntity;
import com.example.finalproject.domain.PostEntity;
import com.example.finalproject.domain.UserEntity;
import com.example.finalproject.enums.AlarmType;
import com.example.finalproject.exception.AppException;
import com.example.finalproject.exception.ErrorCode;
import com.example.finalproject.repository.AlarmRepository;
import com.example.finalproject.repository.LikeRepository;
import com.example.finalproject.repository.PostRepository;
import com.example.finalproject.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@AllArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final AlarmRepository alarmRepository;

    @Transactional
    public void toggleLike(Long postId, String userName) {
        PostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND, ErrorCode.POST_NOT_FOUND.getMessage()));
        UserEntity user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND, ErrorCode.USERNAME_NOT_FOUND.getMessage()));

        Optional<LikeEntity> optLike = likeRepository.findByPostAndUser(post, user);
        if (optLike.isPresent()) {
            // 좋아요 취소
            likeRepository.delete(optLike.get());
        } else {
            // 좋아요 등록
            likeRepository.save(LikeEntity.builder()
                    .user(user)
                    .post(post)
                    .build());
            alarm(user, post, userName);
        }
    }

    public Long getLike(Long postId) {
        return likeRepository.countByPostId(postId);
    }

    public void alarm(UserEntity user, PostEntity post, String userName) {
        if (user == null || post == null) {
            throw new AppException(ErrorCode.POST_OR_USER_NOT_FOUND, ErrorCode.POST_OR_USER_NOT_FOUND.getMessage());
        }

        if (!user.getUserName().equals(userName)) {
            AlarmEntity alarmEntity = AlarmEntity.builder()
                    .alarmType(AlarmType.NEW_LIKE_ON_POST)
                    .user(user)
                    .fromUserId(user.getId())
                    .targetId(post.getId())
                    .text(AlarmType.NEW_LIKE_ON_POST.getText())
                    .build();

            alarmRepository.save(alarmEntity);
        }
    }
}
