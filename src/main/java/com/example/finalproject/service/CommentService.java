package com.example.finalproject.service;

import com.example.finalproject.domain.CommentEntity;
import com.example.finalproject.domain.PostEntity;
import com.example.finalproject.domain.UserEntity;
import com.example.finalproject.domain.dto.request.CommentAddRequest;
import com.example.finalproject.domain.dto.request.CommentPutRequest;
import com.example.finalproject.domain.dto.response.CommentAddResponse;
import com.example.finalproject.domain.dto.response.CommentPutResponse;
import com.example.finalproject.domain.dto.response.CommentResponse;
import com.example.finalproject.exception.AppException;
import com.example.finalproject.exception.ErrorCode;
import com.example.finalproject.repository.CommentRepository;
import com.example.finalproject.repository.PostRepository;
import com.example.finalproject.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CommentService {

    /**
     * 댓글 조회, 작성 , 수정, 삭제
     */

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    /**
     * 댓글 조회 리스트형식 미완성
     * 포스트가 없을 때
     */
    public Page<CommentResponse> findAllByPage(Pageable pageable, PostEntity postId) {

        Page<CommentEntity> visits = commentRepository.findCommentEntitiesByPostId(postId, pageable);
        return new PageImpl<>(visits.stream()
                .map(CommentEntity::toResponse)
                .collect(Collectors.toList()));
    }

    /**
     * 댓글 작성
     *
     * @param request
     * @param inputUserName
     * @param postId
     * @return
     */
    public CommentAddResponse add(CommentAddRequest request, String inputUserName, Long postId) {
        Optional<PostEntity> optPost = postRepository.findById(postId);
        PostEntity postEntity = optPost.orElse(null);

        Optional<UserEntity> optUser = userRepository.findByUserName(inputUserName);
        UserEntity userEntity = optUser.orElse(null);

        CommentEntity commentEntity = CommentEntity.builder()
                .comment(request.getComment())
                .userId(userEntity)
                .postId(postEntity)
                .build();
        CommentEntity saveComment = commentRepository.save(commentEntity);
        return new CommentAddResponse(saveComment.getId(), saveComment.getComment(), saveComment.getUserId().getUserName(), saveComment.getPostId().getId(), saveComment.getCreatedAt());
    }

    /**
     * 댓글 수정
     */
    public CommentPutResponse update(CommentPutRequest request, String userName, Long postId, Long id) {
        Optional<PostEntity> optPost = postRepository.findById(postId);
        Optional<CommentEntity> optComment = commentRepository.findById(id);
        //Error 처리
        commentRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.COMMENT_NOT_FOUND, ""));
        if(!optPost.get().getId().equals(optComment.get().getPostId().getId())){
            throw new IllegalArgumentException("게시물 ID를 확인해주세요");
        }
        if(!optComment.get().getUserId().getUserName().equals(userName)){
            throw new IllegalArgumentException("본인의 댓글만 수정할 수 있습니다.");
        }
        optComment.ifPresent(t -> {
            if(request.getComment() != null) {
                t.setComment(request.getComment());
            }
            this.commentRepository.save(t);
        });

        return new CommentPutResponse(optComment.get().getId(),optComment.get().getComment(),optComment.get().getUserId().getUserName(),
                optComment.get().getPostId().getId(),optComment.get().getCreatedAt(),optComment.get().getLastModifiedAt());
    }

    public void validate(PostEntity postId) {
        postRepository.findById(postId.getId()).orElseThrow(
                () -> new AppException(ErrorCode.POST_NOT_FOUND, ""));
        ;
    }

    public void validate(Long postId) {
        postRepository.findById(postId).orElseThrow(
                () -> new AppException(ErrorCode.POST_NOT_FOUND, ""));
        ;
    }


}
