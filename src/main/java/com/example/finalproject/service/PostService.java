package com.example.finalproject.service;

import com.example.finalproject.domain.PostEntity;
import com.example.finalproject.domain.dto.*;
import com.example.finalproject.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository repository;

    public PostService(PostRepository repository) {
        this.repository = repository;
    }

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
                "게시물 등록에 성공 했습니다.");
    }


        // 페이징
    public List<PostResponse> findAllByPage(Pageable pageable) {
        Page<PostEntity> visits = repository.findAll(pageable);

        // Visits --> VisitResponse
        return visits.stream()
                .map(PostEntity::toResponse)
                .collect(Collectors.toList());
    }

    public PostPutResponse update(Long id, PostPutRequest postPutRequest, String userName) {
        Optional<PostEntity> entity = this.repository.findById(id);
        // 인증에서 받은 현재 로그인한 아이디와, id값에 존재하는 userName을 비교해야함.
        if(entity.get().getUserName() != userName){
            throw new IllegalArgumentException("본인의 게시물만 수정할 수 있습니다.");
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
                "게시물 수정에 성공했습니다.");
    }

}