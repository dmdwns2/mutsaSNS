package com.example.finalproject.service;

import com.example.finalproject.domain.PostEntity;
import com.example.finalproject.domain.dto.PostAddRequest;
import com.example.finalproject.domain.dto.PostAddResponse;
import com.example.finalproject.domain.dto.PostDto;
import com.example.finalproject.domain.dto.PostResponse;
import com.example.finalproject.repository.PostRepository;
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
        return new PostAddResponse(savePost.getId(), savePost.getTitle(), savePost.getBody(),
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

    public Optional<PostEntity> update(Long id, PostAddRequest postAddRequest) {

    }
}