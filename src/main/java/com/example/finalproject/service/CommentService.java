package com.example.finalproject.service;

import com.example.finalproject.domain.AlarmEntity;
import com.example.finalproject.domain.CommentEntity;
import com.example.finalproject.domain.PostEntity;
import com.example.finalproject.domain.UserEntity;
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

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AlarmRepository alarmRepository;

    @Transactional(readOnly = true)
    public Page<CommentResponse> findAllByPage(Pageable pageable, Long postId) {
        PostEntity post = postValidate(postId);
        Page<CommentEntity> comments = commentRepository.findCommentEntitiesByPostId(post, pageable);
        commentsValidate(comments);
        List<CommentResponse> commentResponses = comments.getContent().stream()
                .map(CommentEntity::toResponse)
                .collect(Collectors.toList());
        return new PageImpl<>(commentResponses, pageable, comments.getTotalElements());
    }

    public CommentAddResponse add(CommentAddRequest request, String userName, Long postId) {
        PostEntity postEntity = postValidate(postId);
        UserEntity userEntity = userValidate(userName);

        CommentEntity commentEntity = CommentEntity.builder()
                .comment(request.getComment())
                .userId(userEntity)
                .postId(postEntity)
                .build();
        CommentEntity saveComment = commentRepository.save(commentEntity);

        alarm(userEntity, postEntity, userName);

        return new CommentAddResponse(saveComment.getId(), saveComment.getComment(), saveComment.getUserId().getUserName(), saveComment.getPostId().getId(), saveComment.getCreatedAt());
    }

    @Transactional
    public CommentPutResponse update(CommentPutRequest request, String userName, Long postId, Long id) {
        postValidate(postId);
        CommentEntity commentEntity = commentValidate(id);
        userValidate(userName);
        duplicatedUserValidate(commentEntity.getUserId().getUserName(), userName);

        commentEntity.update(request.getComment());

        commentRepository.save(commentEntity);

        return new CommentPutResponse(commentEntity.getId(), commentEntity.getComment(), commentEntity.getUserId().getUserName(),
                commentEntity.getPostId().getId(), commentEntity.getCreatedAt(), commentEntity.getLastModifiedAt());
    }

    @Transactional
    public CommentDelResponse delete(Long postId, Long id, String userName) {
        postValidate(postId);
        CommentEntity commentEntity = commentValidate(id);
        userValidate(userName);
        duplicatedUserValidate(commentEntity.getUserId().getUserName(), userName);

        commentRepository.delete(commentEntity);
        return new CommentDelResponse("댓글 삭제 완료", id);
    }

    public PostEntity postValidate(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND, ErrorCode.POST_NOT_FOUND.getMessage()));
    }

    public UserEntity userValidate(String userName) {
        return userRepository.findByUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND, ErrorCode.USERNAME_NOT_FOUND.getMessage()));
    }

    public CommentEntity commentValidate(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.COMMENT_NOT_FOUND, ErrorCode.COMMENT_NOT_FOUND.getMessage()));
    }

    private void commentsValidate(Page<CommentEntity> comments) {
        if (comments.isEmpty()) {
            throw new AppException(ErrorCode.COMMENT_NOT_FOUND, ErrorCode.COMMENT_NOT_FOUND.getMessage());
        }
    }
    public void duplicatedUserValidate(String userName1, String userName2) {
        if (!userName1.equals(userName2)) {
            throw new AppException(ErrorCode.DUPLICATED_USER_NAME, ErrorCode.DUPLICATED_USER_NAME.getMessage());
        }
    }

    public void alarm(UserEntity user, PostEntity post, String userName) {
        if (user == null || post == null) {
            throw new AppException(ErrorCode.POST_OR_USER_NOT_FOUND, ErrorCode.POST_OR_USER_NOT_FOUND.getMessage());
        }

        if (!user.getUserName().equals(userName)) {
            AlarmEntity alarmEntity = AlarmEntity.builder()
                    .alarmType(AlarmType.NEW_LIKE_ON_POST)
                    .user(user)
                    .fromUserId(user.getId())
                    .targetId(post.getId())
                    .text(AlarmType.NEW_LIKE_ON_POST.getText())
                    .build();

            alarmRepository.save(alarmEntity);
        }
    }
}
