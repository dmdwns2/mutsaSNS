package com.example.finalproject.controller;

import com.example.finalproject.domain.dto.PostDto;
import com.example.finalproject.domain.dto.request.PostAddRequest;
import com.example.finalproject.domain.dto.request.PostPutRequest;
import com.example.finalproject.domain.dto.response.PostAddResponse;
import com.example.finalproject.domain.dto.response.PostDelResponse;
import com.example.finalproject.domain.dto.response.PostPutResponse;
import com.example.finalproject.domain.dto.response.PostResponse;
import com.example.finalproject.domain.response.Response;
import com.example.finalproject.service.PostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/posts")
@Api(tags = "Post Api")
public class PostRestController {

    private final PostService service;

    @ApiOperation(value = "ID로 게시물 조회")
    @GetMapping("/{id}")
    public Response<PostDto> getPost(@PathVariable Long id) {
        PostDto postDto = service.getPostById(id);
        return Response.success(postDto);
    }

    @ApiOperation(value = "게시물 작성")
    @PostMapping("")
    public Response<PostAddResponse> addPost(@RequestBody PostAddRequest postAddRequest, Authentication authentication) {
        String userName = authentication.getName();
        PostAddResponse response = service.add(postAddRequest, userName);
        return Response.success(response);
    }

    @ApiOperation(value = "게시물 수정")
    @PutMapping("/{id}")
    public Response<PostPutResponse> putPost(@RequestBody PostPutRequest postPutRequest, @PathVariable Long id, Authentication authentication) {
        String userName = authentication.getName();
        PostPutResponse response = service.update(id, postPutRequest, userName);
        return Response.success(response);
    }

    @ApiOperation(value = "게시물 삭제")
    @DeleteMapping("/{id}")
    public Response<PostDelResponse> delete(@PathVariable Long id, Authentication authentication) {
        String userName = authentication.getName();
        PostDelResponse response = service.delete(id, userName);
        return Response.success(response);
    }

    @ApiOperation(value = "게시물 목록 조회")
    @GetMapping
    public Response<Page<PostResponse>> list(@PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return Response.success(service.findAllByPage(pageable));
    }

    @ApiOperation(value = "마이피드")
    @GetMapping("/my")
    public Response<Page<PostResponse>> myFeed(@PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable, Authentication authentication) {
        String userName = authentication.getName();
        Page<PostResponse> responses = service.myFeed(pageable, userName);
        return Response.success(responses);
    }
}
