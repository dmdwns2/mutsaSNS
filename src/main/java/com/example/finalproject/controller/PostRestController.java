package com.example.finalproject.controller;

import com.example.finalproject.domain.dto.PostAddRequest;
import com.example.finalproject.domain.dto.PostAddResponse;
import com.example.finalproject.domain.dto.PostDto;
import com.example.finalproject.domain.dto.PostResponse;
import com.example.finalproject.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/posts")
public class PostRestController {

    private final PostService service;

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPost(@PathVariable Long id) {
        PostDto postDto = service.getPostById(id);
        return ResponseEntity.ok().body(postDto);
    }

    @PostMapping("")
    public ResponseEntity<PostAddResponse> addPost(@RequestBody PostAddRequest postAddRequest, Authentication authentication) {
        String userName = authentication.getName();
        PostAddResponse response = service.add(postAddRequest, userName);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> list(Pageable pageable) {
        return ResponseEntity.ok().body(service.findAllByPage(pageable));
    }
}
