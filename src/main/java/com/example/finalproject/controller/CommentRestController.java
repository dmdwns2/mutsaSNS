package com.example.finalproject.controller;

import com.example.finalproject.domain.dto.request.CommentAddRequest;
import com.example.finalproject.domain.dto.request.CommentPutRequest;
import com.example.finalproject.domain.dto.response.CommentAddResponse;
import com.example.finalproject.domain.dto.response.CommentPutResponse;
import com.example.finalproject.domain.dto.response.CommentResponse;
import com.example.finalproject.domain.response.CommentDelResponse;
import com.example.finalproject.domain.response.Response;
import com.example.finalproject.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/posts")
@Api(tags = "댓글")
public class CommentRestController {

    private final CommentService service;

    @ApiOperation(value = "댓글 조회")
    @GetMapping("/{postId}/comments")
    public Response<Page<CommentResponse>> getComment(
            @PageableDefault(sort = "createdAt",
                    direction = Sort.Direction.DESC) Pageable pageable,
            @PathVariable Long postId) {
        return Response.success(service.findAllByPage(pageable, postId));
    }

    // 댓글 작성
    @PostMapping("/{postId}/comments")
    @ApiOperation(value = "댓글 달기")
    public Response<CommentAddResponse> postComment(@RequestBody CommentAddRequest request,
                                                    Authentication authentication, @PathVariable Long postId) {

        String userName = authentication.getName();
        CommentAddResponse response = service.add(request, userName, postId);
        return Response.success(response);
    }

    //댓글 수정
    @PutMapping("/{postId}/comments/{id}")
    @ApiOperation(value = "댓글 수정")
    public Response<CommentPutResponse> updateComment(@RequestBody CommentPutRequest request,
                                                      @PathVariable Long postId, @PathVariable Long id,
                                                      Authentication authentication) {

        String userName = authentication.getName();
        CommentPutResponse response = service.update(request, userName, postId, id);
        return Response.success(response);
    }

    @DeleteMapping("/{postId}/comments/{id}")
    @ApiOperation(value = "댓글 삭제")
    public Response<CommentDelResponse> deleteComment(@PathVariable Long postId, @PathVariable Long id,
                                                      Authentication authentication) {
        String userName = authentication.getName();
        CommentDelResponse response = service.delete(postId, id, userName);
        return Response.success(response);
    }

}
