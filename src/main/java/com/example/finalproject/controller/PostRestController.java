package com.example.finalproject.controller;

import com.example.finalproject.domain.dto.*;
import com.example.finalproject.domain.response.Response;
import com.example.finalproject.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/posts")
public class PostRestController {

    private final PostService service;

    @GetMapping("/{id}")
    public Response<PostDto> getPost(@PathVariable Long id) {
        PostDto postDto = service.getPostById(id);
        return Response.success(postDto);
    }

    @PostMapping("")
    public Response<PostAddResponse> addPost(@RequestBody PostAddRequest postAddRequest, Authentication authentication) {
        String userName = authentication.getName();
        PostAddResponse response = service.add(postAddRequest, userName);
        return Response.success(response);
    }

    @PutMapping("/{id}")
    public Response<PostPutResponse> putPost(@RequestBody PostPutRequest postPutRequest, @PathVariable Long id, Authentication authentication) {
        String userName = authentication.getName();
        PostPutResponse response = service.update(id, postPutRequest, userName);
        return Response.success(response);
    }

    @DeleteMapping ("/{id}")
    public Response<PostDelResponse> delete(@PathVariable Long id, Authentication authentication) {
        String userName = authentication.getName();
        PostDelResponse response = service.delete(id, userName);
        return Response.success(response);
    }

    @GetMapping
    public Response<List<PostResponse>> list(Pageable pageable) {
        return Response.success(service.findAllByPage(pageable));
    }
}
