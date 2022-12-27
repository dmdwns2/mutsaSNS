package com.example.finalproject.controller;

import com.example.finalproject.domain.PostEntity;
import com.example.finalproject.domain.dto.PostAddRequest;
import com.example.finalproject.domain.dto.PostAddResponse;
import com.example.finalproject.domain.dto.PostDto;
import com.example.finalproject.domain.dto.PostResponse;
import com.example.finalproject.domain.response.Response;
import com.example.finalproject.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

//    @PutMapping("/{id}")
//    public ResponseEntity<PostAddResponse> putPost(@RequestBody PostAddRequest postAddRequest, @PathVariable Long id, Authentication authentication) {
////        PostDto postDto = service.getPostById(id);
//        Optional<PostEntity> postDto = this.service.update(id, postAddRequest);
//
//        return new ResponseEntity(postDto, HttpStatus.OK);
//    }

    @GetMapping
    public Response<List<PostResponse>> list(Pageable pageable) {
        return Response.success(service.findAllByPage(pageable));
    }
}
