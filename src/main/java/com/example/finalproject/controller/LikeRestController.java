package com.example.finalproject.controller;

import com.example.finalproject.domain.response.Response;
import com.example.finalproject.service.LikeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/posts")
@AllArgsConstructor
@Api(tags = "좋아요")
public class LikeRestController {

    private final LikeService service;

    @ApiOperation(value = "좋아요 누르기")
    @PostMapping("/{postId}/likes")
    public Response<String> like_on(@PathVariable Long postId, Authentication authentication){
        String userName = authentication.getName();
        service.toggleLike(postId, userName);
        return Response.success("좋아요를 눌렀습니다.");
    }

    @ApiOperation(value = "좋아요 개수 확인")
    @GetMapping("/{postId}/likes")
    public Response<Long> getLike(@PathVariable Long postId) {
        Long numOfLike = service.getLike(postId);
        return Response.success(numOfLike);
    }
}
