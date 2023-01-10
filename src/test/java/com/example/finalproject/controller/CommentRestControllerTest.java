package com.example.finalproject.controller;

import com.example.finalproject.domain.dto.request.CommentAddRequest;
import com.example.finalproject.domain.dto.response.CommentAddResponse;
import com.example.finalproject.service.CommentService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CommentRestController.class)
class CommentRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    CommentService service;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @WithMockUser
    void 댓글_작성_성공() throws Exception{
        //given
        CommentAddRequest request = new CommentAddRequest("테스트");
        //when
        when(service.add(any(),any(),any())).thenReturn(CommentAddResponse.builder().postId(1L).build());
        //then
        mockMvc.perform(post("/api/v1/comments")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(jsonPath("$.result.comment").exists())
                .andExpect(status().isOk())
                .andDo(print());
    }

}