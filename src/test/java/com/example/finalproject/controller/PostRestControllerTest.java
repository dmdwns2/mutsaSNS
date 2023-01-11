package com.example.finalproject.controller;

import com.example.finalproject.domain.dto.PostDto;
import com.example.finalproject.domain.dto.request.PostAddRequest;
import com.example.finalproject.domain.dto.response.PostAddResponse;
import com.example.finalproject.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostRestController.class)
class PostRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    PostService service;

    @Autowired
    ObjectMapper objectMapper;

    // GET /posts/1 로 조회시
    //조회 성공 - id, title, body, userName 4가지 항목이 있는지 검증
    @Test
    @WithMockUser
    void ID조회_확인() throws Exception {

        //given
        PostDto dto = PostDto.builder()
                .id(1L)
                .title("hi")
                .body("hello")
                .userName("messi")
                .build();

        //when
        when(service.getPostById(any())).thenReturn(dto);

        //then
        mockMvc.perform(get("/api/v1/posts/1")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(dto)))
                .andExpect(jsonPath("$.result.id").exists())
                .andExpect(jsonPath("$.result.title").exists())
                .andExpect(jsonPath("$.result.body").exists())
                .andExpect(jsonPath("$.result.userName").exists())
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @WithMockUser
    void ADD_동작_확인() throws Exception{

        //given
        PostAddRequest request = new PostAddRequest("hi","hello");
        //when
        when(service.add(any(),any())).thenReturn(PostAddResponse.builder().postId(1L).build());
        //then
        mockMvc.perform(post("/api/v1/posts")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(jsonPath("$.result.title").exists())
                .andExpect(jsonPath("$.result.body").exists())
                .andExpect(status().isOk())
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

    @Test
    void 마이피드_조회_성공() {

    }

    @Test
    void 마이피드_조회_실패_로그인_하지_않은경우() {

    }
}
