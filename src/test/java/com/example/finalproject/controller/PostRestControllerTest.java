package com.example.finalproject.controller;

import com.example.finalproject.domain.dto.PostAddRequest;
import com.example.finalproject.domain.dto.PostAddResponse;
import com.example.finalproject.domain.dto.PostDto;
import com.example.finalproject.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostRestController.class)
class PostRestControllerTest {


    @Autowired
    MockMvc mockMvc;

    @MockBean
    PostService postService;

    @Autowired
    ObjectMapper objectMapper;

    private final String USERNAME = "messi";



    // GET /posts/1 로 조회시
    //조회 성공 - id, title, body, userName 4가지 항목이 있는지 검증
    @Test
    void ID조회_확인() throws Exception {
        Long id = 1l;

        given(postService.getPostById(id))
                .willReturn(new PostDto(1l, "첫번째 글", "내용입니다.", USERNAME, any(), any()));

        mockMvc.perform(get("/api/v1/posts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title").exists())
                .andExpect(jsonPath("$.body").exists())
                .andDo(print());

        verify(postService).getPostById(id);
    }

    @Test
    void ADD_동작_확인() throws Exception {
        PostAddRequest dto = new PostAddRequest("제목입니다.", "내용입니다.");
        given(postService.add(any(), any())).willReturn(new PostAddResponse(1l, dto.getTitle(), dto.getBody(), any(), "테스트"));

        mockMvc.perform(post("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(dto))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title").exists())
                .andExpect(jsonPath("$.title").value("제목입니다."))
                .andExpect(jsonPath("$.body").exists())
                .andDo(print());
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
