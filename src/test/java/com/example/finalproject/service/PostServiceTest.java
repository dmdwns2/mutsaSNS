package com.example.finalproject.service;

import com.example.finalproject.domain.PostEntity;
import com.example.finalproject.domain.UserEntity;
import com.example.finalproject.repository.PostRepository;
import com.example.finalproject.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;

@WebMvcTest
class PostServiceTest {

    private final UserRepository userRepository = Mockito.mock(UserRepository.class);
    private final PostRepository postRepository = Mockito.mock(PostRepository.class);
    private final PostService postService = new PostService(postRepository,userRepository);


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
    //조회
    @Test
    @WithMockUser
    void 조회_성공() {
//        when(postRepository.findById(postId))
//                .thenReturn(Optional.of(post));
//
//        PostDto postDto = postService.getPostById(postId);
//        assertEquals(postDto.getUserName(), userName);
    }

    //등록
    @Test
    void 등록_성공() {
//        when(userRepository.findByUserName(userName))
//                .thenReturn(Optional.of(user));
//        when(postRepository.save(any()))
//                .thenReturn(post);
//
//        Assertions.assertDoesNotThrow(() -> postService.add(new PostAddRequest(title, body), userName));
    }

    @Test
    void 유저가_존재하지_않을_때() {

    }


    //수정
    @Test
    void 수정_실패_포스트_존재하지_않음() {
    }

    @Test
    void 수정_실패_작성자가_유저가_아닐때() {
    }

    @Test
    void 수정_실패_유저가_존재하지_않음() {
    }

    //삭제
    @Test
    void 삭제_실패_유저_존재하지_않음() {
    }

    @Test
    void 삭제_실패_포스트_존재하지_않음() {
    }


}