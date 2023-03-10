package com.example.finalproject.service;

import com.example.finalproject.domain.PostEntity;
import com.example.finalproject.domain.UserEntity;
import com.example.finalproject.domain.dto.PostDto;
import com.example.finalproject.domain.dto.request.PostAddRequest;
import com.example.finalproject.domain.dto.request.PostPutRequest;
import com.example.finalproject.exception.AppException;
import com.example.finalproject.exception.ErrorCode;
import com.example.finalproject.repository.PostRepository;
import com.example.finalproject.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Transactional
class PostServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostService postService;

    private final Long userId = 1L;
    private final String userName = "userName";
    private final String password = "password";
    private final UserEntity user = UserEntity.builder()
            .id(userId)
            .userName(userName)
            .password(password)
            .build();
    private final Long anotherUserId = 2L;
    private final String anotherUserName = "userName2";
    private final String anotherPassword = "password2";
    private final UserEntity anotherUser = UserEntity.builder()
            .id(anotherUserId)
            .userName(anotherUserName)
            .password(anotherPassword)
            .build();
    private final Long postId = 1L;
    private final String title = "title";
    private final String body = "body";
    private final PostEntity post = PostEntity.builder()
            .id(postId)
            .title(title)
            .body(body)
            .userName(user.getUserName())
            .build();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    //??????
    @Test
    @WithMockUser
    void ??????_??????() {
        when(postRepository.findById(postId))
                .thenReturn(Optional.of(post));

        PostDto postDto = postService.getPostById(postId);
        assertEquals(postDto.getUserName(), userName);
    }

    //??????
    @Test
    @WithMockUser
    void ??????_??????() {
        when(userRepository.findByUserName(userName))
                .thenReturn(Optional.of(user));
        when(postRepository.save(any()))
                .thenReturn(post);

        assertDoesNotThrow(() -> postService.add(new PostAddRequest(title, body), userName));
    }

    //??????
    @Test
    void ??????_??????_?????????_????????????_??????() {
        when(postRepository.findById(postId))
                .thenReturn(Optional.empty());

        AppException exception = assertThrows(AppException.class, () -> postService.update(postId, new PostPutRequest(title, body), userName));
        assertEquals(ErrorCode.POST_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    void ??????_??????_????????????_?????????_?????????() {
        when(postRepository.findById(postId))
                .thenReturn(Optional.of(post));
        when(userRepository.findByUserName(userName))
                .thenReturn(Optional.of(user));
        when(userRepository.findByUserName(anotherUserName))
                .thenReturn(Optional.of(anotherUser));

        AppException exception = assertThrows(
                AppException.class, () -> postService.update(postId, new PostPutRequest(title, body), anotherUserName));
        assertEquals(ErrorCode.INVALID_PERMISSION, exception.getErrorCode());
    }

    @Test
    void ??????_??????_?????????_????????????_??????() {
        when(postRepository.findById(postId))
                .thenReturn(Optional.empty());

        AppException exception = assertThrows(AppException.class, () -> postService.delete(postId, userName));
        assertEquals(ErrorCode.POST_NOT_FOUND, exception.getErrorCode());
    }
}