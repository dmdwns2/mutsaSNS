package com.example.finalproject.controller;

import com.example.finalproject.domain.dto.request.CommentAddRequest;
import com.example.finalproject.domain.dto.response.CommentAddResponse;
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
            @PageableDefault(size = 10, sort = "createdAt",
                    direction = Sort.Direction.DESC) Pageable pageable,
            @PathVariable Long postId) {

        return Response.success(service.findAllByPage(pageable,postId));
    }

    // 댓글 작성
    @PostMapping("/{id}/comments")
    public Response<CommentAddResponse> postComment(
            @RequestBody CommentAddRequest request,
            Authentication authentication, @PathVariable Long id) {

        String userName = authentication.getName();
        CommentAddResponse response = service.add(request, userName, id);
        return Response.success(response);
    }

}
