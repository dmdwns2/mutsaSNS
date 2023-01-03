package com.example.finalproject.controller;

import com.example.finalproject.domain.dto.PostDto;
import com.example.finalproject.service.PostService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;

@WebMvcTest(PostRestControllerTest.class)
class PostRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    PostService service;

    PostRestController.PostAddRequest request = new PostRestController.PostAddRequest("bye", "ya");

    // GET /posts/1 로 조회시
    //조회 성공 - id, title, body, userName 4가지 항목이 있는지 검증
    @Test
    void ID조회_확인() {
        given(service.getPostById(1l)).willReturn(new PostDto(
                1l, "hi", "hello", "me", null, null));
    }

    @Test
    void ADD_동작_확인() {
        given(service.add(request, "messi")).willReturn(new PostRestController.PostAddResponse(
                1l,"bye","ya","messi","포스트 등록 완료"));
    }

    @Test
    void 작성_인증_실패_JWT를_Bearer_Token으로_보내지_않은_경우() {

    }

    @Test
    void 작성_인증_실패_JWT가_유효하지_않은_경우() {
    }

    // 포스트 수정
    @Test
    void 수정_인증_실패() {
    }

    @Test
    void 수정_작성자_불일치() {
    }

    @Test
    void 수정_데이터베이스_에러() {
    }

    @Test
    void 수정_성공() {
    }

    // 포스트 삭제
    @Test
    void 삭제_인증_실패() {
    }

    @Test
    void 삭제_작성자_불일치() {
    }

    @Test
    void 삭제_데이터베이스_에러() {
    }

    @Test
    void 삭제_성공() {
    }

    // 포스트 리스트
    @Test
    void 조회_성공_0번이_1번보다_날짜가_최신() {
    }
}
