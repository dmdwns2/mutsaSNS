package com.example.finalproject.service;

import com.example.finalproject.domain.CommentEntity;
import com.example.finalproject.domain.PostEntity;
import com.example.finalproject.domain.UserEntity;
import com.example.finalproject.repository.AlarmRepository;
import com.example.finalproject.repository.CommentRepository;
import com.example.finalproject.repository.PostRepository;
import com.example.finalproject.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithMockUser;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CommentServiceTest {

    CommentService service;

    CommentRepository commentRepository = mock(CommentRepository.class);
    PostRepository postRepository = mock(PostRepository.class);
    UserRepository userRepository = mock(UserRepository.class);
    AlarmRepository alarmRepository = mock(AlarmRepository.class);
    UserEntity user = mock(UserEntity.class);
    PostEntity post = mock(PostEntity.class);
    Pageable pageable = mock(Pageable.class);

    @BeforeEach
    void beforeEach_설정() {
        service = new CommentService(commentRepository, postRepository, userRepository, alarmRepository);
    }

    @Test
    @DisplayName("조회 성공")
    @WithMockUser
    void postGet_success() throws Exception {

        //test데이터
        CommentEntity commentEntity = CommentEntity.builder()
                .postId(post)
                .comment("댓글")
                .userId(user)
                .id(1L)
                .build();

        Page<CommentEntity> visits = commentRepository.findCommentEntitiesByPostId(commentEntity.getPostId(), pageable);
        when(commentRepository.findCommentEntitiesByPostId(any(), any())).thenReturn(visits);
        Assertions.assertEquals(post.getComments(), visits.getContent());
    }

    @Test
    void add() {
    }
}