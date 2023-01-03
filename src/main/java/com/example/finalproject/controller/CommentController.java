package com.example.finalproject.controller;

import com.example.finalproject.domain.response.Response;
import com.example.finalproject.service.CommentService;
import lombok.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/posts")
public class CommentController {


    private final CommentService service;

    /**
     * 댓글 조회, 작성 , 수정, 삭제
     */
    @GetMapping("/{id}/comments")
    public Response<CommentResponse> getComment(@PathVariable Long id) {
        CommentResponse commentResponse = service.getCommentById(id);
        return Response.success(commentResponse);
    }

    // 댓글 작성
    @PostMapping("/{id}/comments")
    public Response<CommentAddResponse> postComment(@PathVariable Long id, @RequestBody CommentAddRequest request, Authentication authentication) {
        String userName = authentication.getName();
        CommentAddResponse response = service.add(request, userName);
        return Response.success(response);
    }

    /**
     * Request, Response
     */
    @Builder
    @AllArgsConstructor
    @Getter
    public static class CommentResponse {

        private final Long commentId;
        private final String comment;
        private final String userName;
        private final LocalDateTime createdAt;
        private LocalDateTime lastModifiedAt;

    }
    @Getter
    @Setter
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class CommentAddRequest {
        private final String comment;
    }
    @AllArgsConstructor
    @Getter
    public static class CommentAddResponse {
        private final String comment;
        private final String userName;
        private final LocalDateTime createdAt;
    }

}
