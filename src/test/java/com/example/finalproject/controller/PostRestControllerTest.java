package com.example.finalproject.controller;

import com.example.finalproject.domain.dto.PostAddRequest;
import com.example.finalproject.domain.dto.PostAddResponse;
import com.example.finalproject.domain.dto.PostDto;
import com.example.finalproject.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
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
    PostService articleService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void ID조회_확인() throws Exception {
        Long id = 1l;

        given(articleService.getPostById(id))
                .willReturn(new PostDto(1l, "첫번째 글", "내용입니다.", any(), any(), any()));

        mockMvc.perform(get("/api/v1/posts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title").exists())
                .andExpect(jsonPath("$.body").exists())
                .andDo(print());

        verify(articleService).getPostById(id);
    }

    @Test
    void ADD_동작_확인() throws Exception {
        PostAddRequest dto = new PostAddRequest("제목입니다.", "내용입니다.");
        given(articleService.add(any(),any())).willReturn(new PostAddResponse(1l, dto.getTitle(), dto.getBody(), any(),"테스트"));

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
}
