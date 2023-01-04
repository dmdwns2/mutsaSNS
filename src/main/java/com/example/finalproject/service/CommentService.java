package com.example.finalproject.service;

import com.example.finalproject.domain.CommentEntity;
import com.example.finalproject.domain.PostEntity;
import com.example.finalproject.domain.UserEntity;
import com.example.finalproject.domain.dto.request.CommentAddRequest;
import com.example.finalproject.domain.dto.response.CommentAddResponse;
import com.example.finalproject.domain.dto.response.CommentResponse;
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
     */
    public Page<CommentResponse> findAllByPage(Pageable pageable, Long postId) {
        Page<CommentEntity> visits = commentRepository.findByPostId(postId, pageable);

        return new PageImpl<>(visits.stream()
                .map(CommentEntity::toResponse)
                .collect(Collectors.toList()));
    }

    /**
     * 댓글 작성
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
//    public CommentAddResponse update(CommentAddRequest request, String userName, Long postId) {
//        Optional<PostEntity> optPost = postRepository.findById(postId);
//
//        if(!optPost.get().getUserName().equals(userName)){
//            throw new IllegalArgumentException("본인의 댓글만 수정할 수 있습니다.");
//        }
//        optPost.ifPresent(t ->{
//            // 내용이 널이 아니라면 엔티티의 객체를 바꿔준다.
//            if(request.getComment() != null) {
//                t.getComments().get().setComment(request.getComment());
//            }
//        postEntity..setUserName();
//        return new CommentAddResponse(saveComment.getComment(), saveComment.getUserName(), saveComment.getCreatedAt());
//    }

}
