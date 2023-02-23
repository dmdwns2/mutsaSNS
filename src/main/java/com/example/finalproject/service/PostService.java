package com.example.finalproject.service;

import com.example.finalproject.domain.PostEntity;
import com.example.finalproject.domain.dto.PostDto;
import com.example.finalproject.domain.dto.request.PostAddRequest;
import com.example.finalproject.domain.dto.request.PostPutRequest;
import com.example.finalproject.domain.dto.response.PostAddResponse;
import com.example.finalproject.domain.dto.response.PostDelResponse;
import com.example.finalproject.domain.dto.response.PostPutResponse;
import com.example.finalproject.domain.dto.response.PostResponse;
import com.example.finalproject.exception.AppException;
import com.example.finalproject.exception.ErrorCode;
import com.example.finalproject.repository.PostRepository;
import com.example.finalproject.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class PostService {

    private final PostRepository repository;
    private final UserRepository userRepository;

    public PostDto getPostById(Long id) {
        Optional<PostEntity> optPost = repository.findById(id);
        PostDto postDto = PostEntity.of(optPost.get());
        return postDto;
    }

    public PostAddResponse add(PostAddRequest postAddRequest, String userName) {
        PostEntity postEntity = PostEntity.builder()
                .title(postAddRequest.getTitle())
                .body(postAddRequest.getBody())
                .userName(userName)
                .build();
        PostEntity savePost = repository.save(postEntity);
        return new PostAddResponse(savePost.getId(), savePost.getTitle(), savePost.getBody(), savePost.getUserName(),
                "포스트 등록 완료");
    }


        // 페이징
    public Page<PostResponse> findAllByPage(Pageable pageable) {
        Page<PostEntity> visits = repository.findAll(pageable);

        return new PageImpl<>(visits.stream()
                .map(PostEntity::toResponse)
                .collect(Collectors.toList()));
    }

    @Transactional
    public PostPutResponse update(Long id, PostPutRequest postPutRequest, String userName) {
        Optional<PostEntity> entity = this.repository.findById(id);
        // 인증에서 받은 현재 로그인한 아이디와, id값에 존재하는 userName을 비교해야함.
        if(!entity.get().getUserName().equals(userName)){
            throw new AppException(ErrorCode.INVALID_PERMISSION, ErrorCode.INVALID_PERMISSION.getMessage());
        }
        entity.ifPresent(t ->{
            // 내용이 널이 아니라면 엔티티의 객체를 바꿔준다.
            if(postPutRequest.getBody() != null) {
                t.setBody(postPutRequest.getBody());
            }
            if(postPutRequest.getTitle() != null) {
                t.setTitle(postPutRequest.getTitle());
            }
            // 이걸 실행하면 idx 때문에 update가 실행됩니다.
            this.repository.save(t);
        });
        return new PostPutResponse(entity.get().getId(),entity.get().getTitle(), entity.get().getBody(),entity.get().getUserName(),
                "포스트 수정 완료");
    }

    @Transactional
    public PostDelResponse delete(Long id, String userName) {
        Optional<PostEntity> entity = repository.findById(id);
        if(!entity.get().getUserName().equals(userName)){
            throw new AppException(ErrorCode.INVALID_PERMISSION, ErrorCode.INVALID_PERMISSION.getMessage());
        }
        repository.delete(entity.orElseThrow());
        return new PostDelResponse(id, "포스트 삭제 완료");
    }

    public Page<PostResponse> myFeed(Pageable pageable, String userName) {

        Page<PostEntity> visits = repository.findPostEntitiesByUserName(userName, pageable);

        return new PageImpl<>(visits.stream()
                .map(PostEntity::toResponse)
                .collect(Collectors.toList()));
    }

}