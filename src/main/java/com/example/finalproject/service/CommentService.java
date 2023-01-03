package com.example.finalproject.service;

import com.example.finalproject.domain.CommentEntity;
import com.example.finalproject.repository.CommentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.example.finalproject.controller.CommentController.*;

@AllArgsConstructor
@Service
public class CommentService {

    /**
     * 댓글 조회, 작성 , 수정, 삭제
     */

    private final CommentRepository repository;


    public CommentResponse getCommentById(Long id) {
        Optional<CommentEntity> optComment = repository.findById(id);
        CommentResponse commentResponse = CommentEntity.of(optComment.get());
        return commentResponse;
    }

    public CommentAddResponse add(CommentAddRequest request, String userName) {
        CommentEntity commentEntity = CommentEntity.builder()
                .comment(request.getComment())
                .userName(userName)
                .build();
        CommentEntity saveComment = repository.save(commentEntity);
        return new CommentAddResponse(saveComment.getComment(), saveComment.getUserName(), saveComment.getCreatedAt());
    }
}
