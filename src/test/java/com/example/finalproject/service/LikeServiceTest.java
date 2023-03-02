package com.example.finalproject.service;

import com.example.finalproject.domain.LikeEntity;
import com.example.finalproject.domain.PostEntity;
import com.example.finalproject.domain.UserEntity;
import com.example.finalproject.exception.AppException;
import com.example.finalproject.exception.ErrorCode;
import com.example.finalproject.repository.AlarmRepository;
import com.example.finalproject.repository.LikeRepository;
import com.example.finalproject.repository.PostRepository;
import com.example.finalproject.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@Transactional
class LikeServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private LikeRepository likeRepository;

    @Mock
    private AlarmRepository alarmRepository;

    @InjectMocks
    private LikeService likeService;

    private final Long postId = 1L;
    private final Long userId = 1L;
    private final String title = "title";
    private final String body = "body";
    private final String password = "123";
    private final String userName = "messi";

    private final UserEntity user = UserEntity.builder()
            .id(userId)
            .userName(userName)
            .password(password)
            .build();

    private final PostEntity post = PostEntity.builder()
            .id(postId)
            .title(title)
            .body(body)
            .userName(userName)
            .build();

    private final LikeEntity like = LikeEntity.builder()
            .user(user)
            .post(post)
            .build();


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void 좋아요버튼_눌렀을_때() {
        // given
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(userRepository.findByUserName(userName)).thenReturn(Optional.of(user));
        when(likeRepository.findByPostAndUser(post, user)).thenReturn(Optional.empty());

        // when
        likeService.toggleLike(postId, userName);

        // then
        verify(likeRepository, times(1)).save(any(LikeEntity.class));
    }

    @Test
    void 좋아요버튼_두번째_눌렀을_때() {

        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(userRepository.findByUserName(userName)).thenReturn(Optional.of(user));
        when(likeRepository.findByPostAndUser(post, user)).thenReturn(Optional.of(like));

        // when
        likeService.toggleLike(postId, userName);

        // then
        verify(likeRepository, times(1)).delete(like);
    }

    @Test
    void 좋아요를눌렀지만_게시물을찾을수없을때() {

        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        // when
        AppException appException = assertThrows(AppException.class, () -> likeService.toggleLike(postId, userName));

        // then
        assertEquals(ErrorCode.POST_NOT_FOUND.getMessage(), appException.getMessage());
    }

    @Test
    void 좋아요를눌렀지만_유저를찾을수없을때() {

        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(userRepository.findByUserName(userName)).thenReturn(Optional.empty());

        // when
        Throwable thrown = assertThrows(AppException.class, () -> likeService.toggleLike(postId, userName));

        // then
        assertEquals(ErrorCode.USERNAME_NOT_FOUND.getMessage(), thrown.getMessage());
    }
}