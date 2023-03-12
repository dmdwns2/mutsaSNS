package com.example.finalproject.service;

import com.example.finalproject.domain.CommentEntity;
import com.example.finalproject.domain.PostEntity;
import com.example.finalproject.domain.UserEntity;
import com.example.finalproject.domain.dto.request.CommentAddRequest;
import com.example.finalproject.domain.dto.request.CommentPutRequest;
import com.example.finalproject.domain.dto.response.CommentAddResponse;
import com.example.finalproject.domain.dto.response.CommentPutResponse;
import com.example.finalproject.domain.dto.response.CommentResponse;
import com.example.finalproject.domain.response.CommentDelResponse;
import com.example.finalproject.repository.CommentRepository;
import com.example.finalproject.repository.PostRepository;
import com.example.finalproject.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class CommentServiceTest {


    @Mock
    private CommentRepository commentRepository;
    @Mock
    private PostRepository postRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private Pageable pageable;

    @InjectMocks
    CommentService commentService;

    private final Long userId = 1L;
    private final String userName = "userName";
    private final String password = "password";
    private final UserEntity user = UserEntity.builder()
            .id(userId)
            .userName(userName)
            .password(password)
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

    private final Long commentId = 1L;
    private final String text = "댓글";
    private final CommentEntity comment = CommentEntity.builder()
            .postId(post)
            .comment(text)
            .userId(user)
            .id(commentId)
            .build();


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @WithMockUser
    void 조회성공() {
        // given
        when(postRepository.findById(anyLong())).thenReturn(Optional.ofNullable(post));
        List<CommentEntity> comments = new ArrayList<>();
        comments.add(comment);
        Page<CommentEntity> page = new PageImpl<>(comments);
        when(commentRepository.findCommentEntitiesByPostId(any(PostEntity.class), any(Pageable.class))).thenReturn(page);

        // when
        Page<CommentResponse> commentResponses = commentService.findAllByPage(pageable, postId);

        // then
        assertThat(commentResponses.getTotalElements()).isEqualTo(1L);
        assertThat(commentResponses.getContent().get(0).getComment()).isEqualTo("댓글");
    }

    @Test
    void 댓글등록() {
        // given
        CommentAddRequest request = new CommentAddRequest("댓글");

        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(userRepository.findByUserName(userName)).thenReturn(Optional.of(user));
        when(commentRepository.save(any(CommentEntity.class))).thenReturn(comment);

        // when
        CommentAddResponse response = commentService.add(request, userName, postId);

        // then
        assertEquals(comment.getId(), response.getId());
        assertEquals(comment.getComment(), response.getComment());
        assertEquals(comment.getUserId().getUserName(), response.getUserName());
        assertEquals(comment.getPostId().getId(), response.getPostId());
        assertEquals(comment.getCreatedAt(), response.getCreatedAt());

        verify(postRepository, times(1)).findById(postId);
        verify(userRepository, times(1)).findByUserName(userName);
        verify(commentRepository, times(1)).save(any(CommentEntity.class));
    }


    @Test
    void 댓글수정() {
        // given
        CommentPutRequest request = new CommentPutRequest("댓글");
        when(postRepository.findById(postId)).thenReturn(Optional.ofNullable(post));
        when(userRepository.findByUserName(userName)).thenReturn(Optional.ofNullable(user));
        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));
        // when
        CommentPutResponse response = commentService.update(request, userName, postId, commentId);

        // then
        assertThat(response.getId()).isEqualTo(commentId);
        assertThat(response.getComment()).isEqualTo(request.getComment());
        assertThat(response.getUserName()).isEqualTo(userName);
        assertThat(response.getPostId()).isEqualTo(postId);
        assertThat(response.getCreatedAt()).isEqualTo(comment.getCreatedAt());
    }

    @Test
    @WithMockUser
    void 댓글삭제() {
        when(postRepository.findById(postId)).thenReturn(Optional.ofNullable(post));
        when(userRepository.findByUserName(userName)).thenReturn(Optional.ofNullable(user));
        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));
        CommentDelResponse response = commentService.delete(postId, commentId, userName);
        Assertions.assertEquals("댓글 삭제 완료", response.getMessage());
    }
}