package com.example.finalproject.controller;

import com.example.finalproject.service.PostAddService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.isBlank;

@RestController
@AllArgsConstructor
public class PostAddController {

    private final PostAddService service;

    @AllArgsConstructor
    public static class PostAddRequest {
        private final String title;
        private final String body;
        private final String userName;
    }

    @PostMapping("/api/v1/posts")
    public void add(@RequestBody PostAddRequest request){
        validate(request);
        service.add(new PostAddService.PostAddCommand(
                request.title,request.body,request.userName));
    }

    public void validate(PostAddRequest request){
        if (isNull(request)) {
            throw new IllegalArgumentException("request is empty");
        }
        if (isBlank(request.title)) {
            throw new IllegalArgumentException("title is empty");
        }
        if (isBlank(request.body)) {
            throw new IllegalArgumentException("body is empty");
        }
        if (isBlank(request.userName)) {
            throw new IllegalArgumentException("userName is empty");
        }
    }
}
