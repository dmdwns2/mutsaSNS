package com.example.finalproject.service;

import com.example.finalproject.domain.CommentEntity;
import com.example.finalproject.domain.PostEntity;
import com.example.finalproject.domain.UserEntity;
import com.example.finalproject.domain.dto.AlarmDto;
import com.example.finalproject.domain.dto.request.CommentAddRequest;
import com.example.finalproject.domain.dto.request.CommentPutRequest;
import com.example.finalproject.domain.dto.response.CommentAddResponse;
import com.example.finalproject.domain.dto.response.CommentPutResponse;
import com.example.finalproject.domain.dto.response.CommentResponse;
import com.example.finalproject.domain.response.CommentDelResponse;
import com.example.finalproject.enums.AlarmType;
import com.example.finalproject.exception.AppException;
import com.example.finalproject.exception.ErrorCode;
import com.example.finalproject.repository.AlarmRepository;
import com.example.finalproject.repository.CommentRepository;
import com.example.finalproject.repository.PostRepository;
import com.example.finalproject.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final AlarmRepository alarmRepository;

    /**
     * 댓글 조회 리스트형식 미완성
     * 포스트가 없을 때
     */
    @Transactional
    public Page<CommentResponse> findAllByPage(Pageable pageable, PostEntity postId) {
        postValidate(postId);
        Page<CommentEntity> visits = commentRepository.findCommentEntitiesByPostId(postId, pageable);
        return new PageImpl<>(visits.stream()
                .map(CommentEntity::toResponse)
                .collect(Collectors.toList()));
    }

    /**
     * 댓글 작성
     *
     * @param request
     * @param userName
     * @param postId
     * @return
     */
    public CommentAddResponse add(CommentAddRequest request, String userName, Long postId) {
        PostEntity postEntity = postValidate(postId);
        UserEntity userEntity = userValidate(userName);
        // null 처리 orElseThrow로 람다처리

        CommentEntity commentEntity = CommentEntity.builder()
                .comment(request.getComment())
                .userId(userEntity)
                .postId(postEntity)
                .build();
        CommentEntity saveComment = commentRepository.save(commentEntity);

        alarm(userEntity,postEntity,userName);

        return new CommentAddResponse(saveComment.getId(), saveComment.getComment(), saveComment.getUserId().getUserName(), saveComment.getPostId().getId(), saveComment.getCreatedAt());


    }

    /**
     * 댓글 수정
     */
    @Transactional
    public CommentPutResponse update(CommentPutRequest request, String userName, Long postId, Long id) {
        postValidate(postId);
        CommentEntity commentEntity = commentValidate(id);
        userValidate(userName);
        duplicatedUserValidate(commentEntity.getUserId().getUserName(),userName);

        commentEntity.update(request.getComment());

        commentRepository.save(commentEntity);

        return new CommentPutResponse(commentEntity.getId(),commentEntity.getComment(),commentEntity.getUserId().getUserName(),
                commentEntity.getPostId().getId(),commentEntity.getCreatedAt(),commentEntity.getLastModifiedAt());
    }

    /**
     * 댓글 삭제
     * @param postId, id
     * @return
     */
    @Transactional
    public CommentDelResponse delete(Long postId, Long id, String userName){
        postValidate(postId);
        CommentEntity commentEntity = commentValidate(id);
        userValidate(userName);
        duplicatedUserValidate(commentEntity.getUserId().getUserName(),userName);

        commentRepository.delete(commentEntity);
        return new CommentDelResponse("댓글 삭제 완료", id);
    }

    public PostEntity postValidate(PostEntity postId) {
        return postRepository.findById(postId.getId()).orElseThrow(() -> {
            throw new AppException(ErrorCode.POST_NOT_FOUND, ErrorCode.POST_NOT_FOUND.getMessage());
        });
    }

    public PostEntity postValidate(Long postId) {
        return postRepository.findById(postId).orElseThrow(() -> {
            throw new AppException(ErrorCode.POST_NOT_FOUND, ErrorCode.POST_NOT_FOUND.getMessage());
        });
    }

    public UserEntity userValidate(String userName) {
        return userRepository.findByUserName(userName).orElseThrow(() -> {
            throw new AppException(ErrorCode.USERNAME_NOT_FOUND, ErrorCode.USERNAME_NOT_FOUND.getMessage());
        });
    }

    public CommentEntity commentValidate(Long id) {
        return commentRepository.findById(id).orElseThrow(() -> {
            throw new AppException(ErrorCode.COMMENT_NOT_FOUND, ErrorCode.COMMENT_NOT_FOUND.getMessage());
        });
    }

    public void duplicatedUserValidate(String userName1, String userName2){
        if(!userName1.equals(userName2)) {
            throw new AppException(ErrorCode.DUPLICATED_USER_NAME, ErrorCode.DUPLICATED_USER_NAME.getMessage());
        }
    }
    public void alarm(UserEntity user, PostEntity post, String userName) {
        if (!user.getUserName().equals(userName)) {
            alarmRepository.save(AlarmDto.toEntity(AlarmType.NEW_COMMENT_ON_POST, user, user.getId(), post.getId()));
        }
    }
}
