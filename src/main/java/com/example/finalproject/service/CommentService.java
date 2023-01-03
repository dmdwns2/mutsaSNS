package com.example.finalproject.service;

import com.example.finalproject.domain.CommentEntity;
import com.example.finalproject.domain.PostEntity;
import com.example.finalproject.domain.dto.request.CommentAddRequest;
import com.example.finalproject.domain.dto.response.CommentAddResponse;
import com.example.finalproject.repository.CommentRepository;
import com.example.finalproject.repository.PostRepository;
import com.example.finalproject.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
     * 댓글 조회 리스트형식
     */
//    public Page<CommentResponse> findAllByPage(Pageable pageable, Long postId) {
//        Optional<PostEntity> post = postRepository.findById(postId);
//
//        Page<CommentEntity> visits = commentRepository.findAllb(pageable);
//
//        return new PageImpl<>(visits.stream()
//                .map(CommentEntity::toResponse)
//                .collect(Collectors.toList()));
//    }

    /**
     * 댓글 작성
     * @param request
     * @param inputUserName
     * @param postId
     * @return
     */
    public CommentAddResponse add(CommentAddRequest request, String inputUserName, Long postId) {
        Optional<PostEntity> postEntity = postRepository.findById(postId);

        CommentEntity commentEntity = CommentEntity.builder()
                .post(postEntity.get())
                .comment(request.getComment())
                .userName(inputUserName)
                .build();
        CommentEntity saveComment = commentRepository.save(commentEntity);
        return new CommentAddResponse(saveComment.getComment(), saveComment.getUserName(), saveComment.getCreatedAt());
    }
    /**
     * 댓글 수정
     */
//    public CommentAddResponse update(CommentAddRequest request, String userName, Long postId) {
//        Optional<PostEntity> postEntity = postRepository.findById(postId);
//        if(postEntity.get().getUserName().equals(userName)){
//            throw new IllegalArgumentException("");
//        }
//        postEntity.get().setUserName();
//        return new CommentAddResponse(saveComment.getComment(), saveComment.getUserName(), saveComment.getCreatedAt());
//    }

}
