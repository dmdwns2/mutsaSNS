package com.example.finalproject.service;

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
//        UserEntity user = mock(UserEntity.class);
//        PostEntity post = mock(PostEntity.class);
//        Pageable pageable = mock(Pageable.class);
//
//        //test데이터
//        CommentEntity commentEntity = CommentEntity.builder()
//                .postId(post)
//                .comment("댓글")
//                .userId(user)
//                .id(1l)
//                .build();
//
//        Page<CommentEntity> visits = commentRepository.findCommentEntitiesByPostId(post, pageable);
//        when(commentRepository.findCommentEntitiesByPostId(post, pageable)).thenReturn(visits);
//        System.out.println(visits);
//        Assertions.assertEquals(commentEntity.getComment(), visits.get);
    }
    @Test
    void add() {
    }
}