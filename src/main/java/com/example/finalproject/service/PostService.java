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
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class PostService {

    private final PostRepository repository;

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
        try {
            PostEntity savedPostEntity = repository.save(postEntity);
            return new PostAddResponse(savedPostEntity.getId(), savedPostEntity.getTitle(), savedPostEntity.getBody(),
                    savedPostEntity.getUserName(), "포스트 등록 완료");
        } catch (Exception e) {
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR, ErrorCode.INTERNAL_SERVER_ERROR.getMessage());
        }
    }

    public Page<PostResponse> findAllByPage(Pageable pageable) {
        Page<PostEntity> page = repository.findAll(pageable);
        List<PostResponse> content = page.getContent()
                .stream()
                .map(PostEntity::toResponse)
                .collect(Collectors.toList());
        return new PageImpl<>(content, pageable, page.getTotalElements());
    }

    @Transactional
    public PostPutResponse update(Long id, PostPutRequest postPutRequest, String userName) {
        Optional<PostEntity> optionalEntity = this.repository.findById(id);
        PostEntity entity = optionalEntity.orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND, ErrorCode.POST_NOT_FOUND.getMessage()));

        validateForDuplicateUser(entity.getUserName(), userName);

        if (postPutRequest.getBody() != null) {
            entity.setBody(postPutRequest.getBody());
        }
        if (postPutRequest.getTitle() != null) {
            entity.setTitle(postPutRequest.getTitle());
        }

        this.repository.save(entity);
        return new PostPutResponse(entity.getId(), entity.getTitle(), entity.getBody(), entity.getUserName(), "포스트 수정 완료");
    }

    @Transactional
    public PostDelResponse delete(Long id, String userName) {
        Optional<PostEntity> entity = repository.findById(id);
        if(!entity.isPresent()){
            throw new AppException(ErrorCode.POST_NOT_FOUND,ErrorCode.POST_NOT_FOUND.getMessage());
        }
        validateForDuplicateUser(entity.get().getUserName(), userName);
        repository.delete(entity.orElseThrow());
        return new PostDelResponse(id, "포스트 삭제 완료");
    }

    public Page<PostResponse> myFeed(Pageable pageable, String userName) {
        Page<PostEntity> page = repository.findPostEntitiesByUserName(userName, pageable);
        List<PostResponse> content = page.getContent()
                .stream()
                .map(PostEntity::toResponse)
                .collect(Collectors.toList());
        return new PageImpl<>(content, pageable, page.getTotalElements());
    }

    public void validateForDuplicateUser(String userName1, String userName2) {
        if (!userName1.equals(userName2)) {
            throw new AppException(ErrorCode.INVALID_PERMISSION, ErrorCode.INVALID_PERMISSION.getMessage());
        }
    }

}