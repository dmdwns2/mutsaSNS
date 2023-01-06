package com.example.finalproject.controller;

import com.example.finalproject.domain.PostEntity;
import com.example.finalproject.domain.dto.request.CommentAddRequest;
import com.example.finalproject.domain.dto.request.CommentPutRequest;
import com.example.finalproject.domain.dto.response.CommentAddResponse;
import com.example.finalproject.domain.dto.response.CommentPutResponse;
import com.example.finalproject.domain.dto.response.CommentResponse;
import com.example.finalproject.domain.response.Response;
import com.example.finalproject.service.CommentService;
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
public class CommentRestController {


    private final CommentService service;

    /**
     * 댓글 조회, 작성 , 수정, 삭제
     */
    @GetMapping("/{postId}/comments")
    public Response<Page<CommentResponse>> getComment(
            @PageableDefault(sort = "createdAt",
                    direction = Sort.Direction.DESC) Pageable pageable,
            @PathVariable PostEntity postId) {
        return Response.success(service.findAllByPage(pageable,postId));
    }

    // 댓글 작성
    @PostMapping("/{postId}/comments")
    public Response<CommentAddResponse> postComment(@RequestBody CommentAddRequest request,
                                                    Authentication authentication, @PathVariable Long postId) {

        String userName = authentication.getName();
        CommentAddResponse response = service.add(request, userName, postId);
        return Response.success(response);
    }

    //댓글 수정
    @PutMapping("/{postId}/comments/{id}")
    public Response<CommentPutResponse> updateComment(@RequestBody CommentPutRequest request,
                                                      @PathVariable Long postId, @PathVariable Long id,
                                                      Authentication authentication) {

        String userName = authentication.getName();
        CommentPutResponse response = service.update(request, userName, postId, id);
        return Response.success(response);
    }

}
