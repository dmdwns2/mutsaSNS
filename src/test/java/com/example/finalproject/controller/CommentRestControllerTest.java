package com.example.finalproject.controller;

import com.example.finalproject.domain.PostEntity;
import com.example.finalproject.domain.UserEntity;
import com.example.finalproject.domain.dto.request.CommentAddRequest;
import com.example.finalproject.domain.dto.request.CommentPutRequest;
import com.example.finalproject.domain.dto.response.CommentAddResponse;
import com.example.finalproject.domain.dto.response.CommentPutResponse;
import com.example.finalproject.domain.dto.response.CommentResponse;
import com.example.finalproject.domain.response.CommentDelResponse;
import com.example.finalproject.service.CommentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CommentRestController.class)
class CommentRestControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    CommentService service;

    private final Long userId = 1L;
    private final String userName = "userName";
    private final String password = "password";
    private final UserEntity user = UserEntity.builder()
            .id(userId)
            .userName(userName)
            .password(password)
            .build();
    private final Long postId = 1L;
    private final String title = "title";
    private final String body = "body";
    private final PostEntity post = PostEntity.builder()
            .id(postId)
            .title(title)
            .body(body)
            .userName(user.getUserName())
            .build();

    @Test
    @WithMockUser
    void 댓글_조회_성공() throws Exception {
        //given
        Page<CommentResponse> responses = new PageImpl<>(Arrays.asList(
                CommentResponse.builder().id(1L).comment("1번댓글").build(),
                CommentResponse.builder().id(2L).comment("2번댓글").build()
        ));

        //when
        when(service.findAllByPage(any(), any())).thenReturn(responses);

        //then
        mockMvc.perform(get("/api/v1/posts/1/comments")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.content").isArray())
                .andExpect(jsonPath("$.result.content", hasSize(2)))
                .andExpect(jsonPath("$.result.content[0].id").value(1L))
                .andExpect(jsonPath("$.result.content[0].comment").value("1번댓글"))
                .andExpect(jsonPath("$.result.content[1].id").value(2L))
                .andExpect(jsonPath("$.result.content[1].comment").value("2번댓글"));

    }


    @Test
    @WithMockUser
    void 댓글_작성_성공() throws Exception {
        //given
        CommentAddRequest request = new CommentAddRequest("테스트");
        //when
        when(service.add(any(), any(), any())).thenReturn(CommentAddResponse.builder().comment("테스트").postId(postId).userName(userName).build());
        //then
        mockMvc.perform(post("/api/v1/posts/1/comments")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(jsonPath("$.result.comment").exists())
                .andExpect(jsonPath("$.result.userName").exists())
                .andExpect(jsonPath("$.result.postId").exists())
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @WithMockUser
    void 댓글_수정_성공() throws Exception {
        //given
        CommentPutRequest request = new CommentPutRequest("수정");
        //when
        when(service.update(any(), any(), any(), any())).thenReturn(CommentPutResponse.builder().id(1L).comment("테스트").postId(postId).userName(userName).build());
        //then
        mockMvc.perform(put("/api/v1/posts/1/comments/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(jsonPath("$.result.id").exists())
                .andExpect(jsonPath("$.result.comment").exists())
                .andExpect(jsonPath("$.result.userName").exists())
                .andExpect(jsonPath("$.result.postId").exists())
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @WithMockUser
    void 댓글_삭제_성공() throws Exception {
        //given
        CommentDelResponse commentDto = new CommentDelResponse("댓글 삭제 완료", 1L);
        //when
        when(service.delete(any(), any(), any())).thenReturn(commentDto);
        //then
        mockMvc.perform(delete("/api/v1/posts/1/comments/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(commentDto)))
                .andExpect(jsonPath("$.result.id").exists())
                .andExpect(jsonPath("$.result.message").exists())
                .andExpect(status().isOk())
                .andDo(print());
    }

}