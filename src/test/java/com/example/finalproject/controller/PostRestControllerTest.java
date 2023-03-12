package com.example.finalproject.controller;

import com.example.finalproject.domain.dto.PostDto;
import com.example.finalproject.domain.dto.request.PostAddRequest;
import com.example.finalproject.domain.dto.request.PostPutRequest;
import com.example.finalproject.domain.dto.response.PostAddResponse;
import com.example.finalproject.domain.dto.response.PostDelResponse;
import com.example.finalproject.domain.dto.response.PostPutResponse;
import com.example.finalproject.exception.AppException;
import com.example.finalproject.exception.ErrorCode;
import com.example.finalproject.service.PostService;
import com.example.finalproject.utils.JwtTokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    @Value("${jwt.token.secret}")
    private String key;

    String token;

    @BeforeEach()
    public void getToken() {
        long expireTimeMs = 1000 * 60 * 60;
        token = JwtTokenUtil.createToken("messi", key, System.currentTimeMillis() + expireTimeMs);
    }

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
    void ADD_동작_확인() throws Exception {

        //given
        PostAddRequest request = new PostAddRequest("hi", "hello");
        //when
        when(service.add(any(), any())).thenReturn(PostAddResponse.builder().title("hi").body("hello").build());
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
    void 작성_인증_실패_JWT를_Bearer_Token으로_보내지_않은_경우() throws Exception {

        PostAddRequest postAddRequest = new PostAddRequest("test", "body");
        given(service.add(any(), any())).willThrow(new AppException(ErrorCode.INVALID_TOKEN, "잘못된 토큰입니다."));
        mockMvc.perform(post("/api/v1/posts")
                        .with(csrf())
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(postAddRequest)))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }

    @Test
    void 작성_인증_실패_JWT가_유효하지_않은_경우() throws Exception {

        token = JwtTokenUtil.createToken("bappe", key, System.currentTimeMillis());
        PostAddRequest postAddRequest = new PostAddRequest("테스트", "테스트");
        given(service.add(any(), any())).willThrow(new AppException(ErrorCode.INVALID_TOKEN, "잘못된 토큰입니다."));
        mockMvc.perform(post("/api/v1/posts")
                        .with(csrf())
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(postAddRequest)))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }

    // 포스트 수정
    @WithMockUser
    @Test
    void 수정_인증_실패() throws Exception {

        PostPutRequest postPutRequest = new PostPutRequest("title", "body");
        given(service.update(any(), any(), any())).willThrow(new AppException(ErrorCode.INVALID_PERMISSION, ErrorCode.INVALID_PERMISSION.getMessage()));
        mockMvc.perform(put("/api/v1/posts/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(postPutRequest)))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }

    @Test
    void 수정_작성자_불일치() throws Exception {
        // given
        PostPutRequest postPutRequest = new PostPutRequest("modified title", "modified body");
        // when
        when(service.update(any(), any(), any()))
                .thenThrow(new AppException(ErrorCode.INVALID_PERMISSION, ErrorCode.INVALID_PERMISSION.getMessage()));
        // then
        mockMvc.perform(put("/api/v1/posts/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(postPutRequest)))
                .andDo(print())
                .andExpect(status().is(ErrorCode.INVALID_PERMISSION.getStatus().value()));
    }

    @Test
    @WithMockUser
    void 수정_데이터베이스_에러() throws Exception {
        // given
        PostPutRequest postPutRequest = new PostPutRequest("modified title", "modified body");
        // when
        when(service.update(any(), any(), any()))
                .thenThrow(new AppException(ErrorCode.DATABASE_ERROR, ErrorCode.DATABASE_ERROR.getMessage()));
        // then
        mockMvc.perform(put("/api/v1/posts/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(postPutRequest)))
                .andDo(print())
                .andExpect(status().is(ErrorCode.DATABASE_ERROR.getStatus().value()));
    }

    @Test
    @WithMockUser
    void 수정_성공() throws Exception {
        // given
        PostDto dto = PostDto.builder()
                .id(1l)
                .title("bftitle")
                .body("bfbody")
                .build();
        // when
        when(service.update(any(), any(), any())).thenReturn(PostPutResponse.builder().title("hi").body("hi").build());
        //then
        mockMvc.perform(put("/api/v1/posts/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(dto)))
                .andExpect(jsonPath("$.result.title").exists())
                .andExpect(jsonPath("$.result.body").exists())
                .andDo(print())
                .andExpect(status().isOk());
    }

    // 포스트 삭제
    @Test
    void 삭제_인증_실패() throws Exception{
        PostDelResponse postDelete = new PostDelResponse(1L, "포스트 삭제 완료");
        when(service.delete(any(), any())).thenReturn(postDelete);
        mockMvc.perform(delete("/api/v1/posts/1")
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().is(ErrorCode.INVALID_PERMISSION.getStatus().value()));

    }

    @Test
    void 삭제_작성자_불일치() throws Exception{
        PostDelResponse postDelete = new PostDelResponse(1L, "포스트 삭제 완료");
        when(service.delete(any(), any())).thenReturn(postDelete);
        mockMvc.perform(delete("/api/v1/posts/1")
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().is(ErrorCode.INVALID_PERMISSION.getStatus().value()));
    }

    @Test
    @WithMockUser
    void 삭제_데이터베이스_에러() throws Exception{
        when(service.delete(any(), any()))
                .thenThrow(new AppException(ErrorCode.DATABASE_ERROR,ErrorCode.DATABASE_ERROR.getMessage()));

        // then
        mockMvc.perform(delete("/api/v1/posts/1")
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().is(ErrorCode.DATABASE_ERROR.getStatus().value()));
    }

    @Test
    @WithMockUser
    void 삭제_성공() throws Exception {
        // given
        PostDelResponse postDelResponse = new PostDelResponse(1L, "포스트 삭제 완료");
        // when
        when(service.delete(any(), any())).thenReturn(postDelResponse);
        // then
        mockMvc.perform(delete("/api/v1/posts/1")
                        .with(csrf()))
                .andDo(print())
                .andExpect(jsonPath("$.result.message").exists())
                .andExpect(jsonPath("$.result.postId").exists())
                .andExpect(status().isOk());
    }
}
