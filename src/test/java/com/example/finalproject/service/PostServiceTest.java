package com.example.finalproject.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
@WebMvcTest
class PostServiceTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    @MockBean
    PostService postService;

    @Autowired
    ObjectMapper objectMapper;

    //조회
    @Test
    void 조회_성공() {
    }

    //등록
    @Test
    void 등록_성공() {
    }

    @Test
    void 유저가_존재하지_않을_때() {
    }


    //수정
    @Test
    void 수정_실패_포스트_존재하지_않음() {
    }

    @Test
    void 수정_실패_작성자가_유저가_아닐때() {
    }

    @Test
    void 수정_실패_유저가_존재하지_않음() {
    }

    //삭제
    @Test
    void 삭제_실패_유저_존재하지_않음() {
    }

    @Test
    void 삭제_실패_포스트_존재하지_않음() {
    }


}