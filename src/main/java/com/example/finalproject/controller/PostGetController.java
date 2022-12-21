//package com.example.finalproject.controller;
//
//import com.example.finalproject.domain.Post;
//import com.example.finalproject.service.PostGetService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api/v1/posts")
//@RequiredArgsConstructor
//public class PostGetController {
//
//    private final PostGetService service;
//
//    @GetMapping("/{id}")
//    public Post get(@PathVariable long id) {
//        return service.find(id);
//    }
//}
