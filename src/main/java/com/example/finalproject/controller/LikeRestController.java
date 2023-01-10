package com.example.finalproject.controller;

import com.example.finalproject.domain.response.Response;
import com.example.finalproject.service.LikeService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/posts")
@AllArgsConstructor
public class LikeRestController {

    private final LikeService service;

    @PostMapping("/{postId}/likes")
    public Response<String> like_on(@PathVariable Long postId, Authentication authentication){
        String userName = authentication.getName();
        service.like_on(postId, userName);
        return Response.success("좋아요를 눌렀습니다.");
    }

    @GetMapping("/{postId}/likes")
    public Response<Long> getLike(@PathVariable Long postId) {
        Long numOfLike = service.getLike(postId);
        return Response.success(numOfLike);
    }
}
