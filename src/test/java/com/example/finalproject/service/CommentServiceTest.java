package com.example.finalproject.service;

import com.example.finalproject.domain.CommentEntity;
import com.example.finalproject.domain.PostEntity;
import com.example.finalproject.domain.UserEntity;
import com.example.finalproject.repository.CommentRepository;
import com.example.finalproject.repository.PostRepository;
import com.example.finalproject.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;

class CommentServiceTest {

    CommentService service;

    CommentRepository commentRepository = mock(CommentRepository.class);
    PostRepository postRepository = mock(PostRepository.class);
    UserRepository userRepository = mock(UserRepository.class);

    @BeforeEach
    void beforeEach_설정() {
        service = new CommentService(commentRepository,postRepository,userRepository);
    }

    @Test
    @DisplayName("조회 성공")
    void postGet_success() {
        UserEntity user = mock(UserEntity.class);
        PostEntity post = mock(PostEntity.class);

        //test데이터
        CommentEntity commentEntity = CommentEntity.builder()
                .postId(post)
                .comment("댓글")
                .userId(user)
                .id(1l)
                .build();

//        when(commentRepository.findById(post.getId())).thenReturn(Optional.of(commentEntity));
//        CommentResponse commentResponse = service.();
//
//        Assertions.assertEquals(commentResponse.getUserName(), post.getUser().getUserName());
    }
    @Test
    void add() {
    }
}