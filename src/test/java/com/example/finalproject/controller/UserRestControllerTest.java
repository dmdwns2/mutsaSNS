package com.example.finalproject.controller;

import com.example.finalproject.domain.dto.UserDto;
import com.example.finalproject.exception.AppException;
import com.example.finalproject.exception.ErrorCode;
import com.example.finalproject.service.PostService;
import com.example.finalproject.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static com.example.finalproject.controller.UserRestController.UserJoinRequest;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserRestController.class)
class UserRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;


    @MockBean
    PostService postService;

    @Autowired
    ObjectMapper objectMapper;


    @Test
    @WithMockUser
    void 회원가입_성공() throws Exception {
        //given
        UserJoinRequest userJoinRequest = UserJoinRequest.builder()
                .userName("mbappe")
                .password("123")
                .build();
        //when
        when(userService.join(any(), any())).thenReturn(mock(UserDto.class));
        //then
        mockMvc.perform(post("/api/v1/users/join")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(userJoinRequest)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void 회원가입_실패() throws Exception {
        //given
        UserJoinRequest userJoinRequest = UserJoinRequest.builder()
                .userName("bappe")
                .password("123qwe")
                .build();
        //given
        when(userService.join(any(), any())).thenThrow(new AppException(ErrorCode.DUPLICATED_USER_NAME, ""));
        //then
        mockMvc.perform(post("/api/v1/users/join")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(userJoinRequest)))
                .andDo(print())
                .andExpect(status().isConflict());

    }

    @Test
    @WithMockUser
    void 로그인_성공() throws Exception {
        //given
        UserJoinRequest userJoinRequest = UserJoinRequest.builder()
                .userName("bappe")
                .password("123qwe")
                .build();
        //when
        when(userService.login(any(), any())).thenReturn("token");
        //then
        mockMvc.perform(post("/api/v1/users/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(userJoinRequest)))
                .andDo(print())
                .andExpect(jsonPath("$.resultCode").exists())
                .andExpect(jsonPath("$.result.jwt").exists())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("로그인 실패 - id없음")
    @WithMockUser
    void 로그인_실패() throws Exception {
        //given
        UserJoinRequest userJoinRequest = UserJoinRequest.builder()
                .userName("bappe")
                .password("123qwe")
                .build();
        // when
        when(userService.login(any(), any())).thenThrow(new AppException(ErrorCode.USERNAME_NOT_FOUND, ""));

        // then
        mockMvc.perform(post("/api/v1/users/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(userJoinRequest)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("로그인 실패 - 비밀번호 틀림")
    @WithMockUser
    void 로그인_실패_비밀번호() throws Exception {
        //given
        UserJoinRequest userJoinRequest = UserJoinRequest.builder()
                .userName("bappe")
                .password("123qwe")
                .build();
        // when
        when(userService.login(any(), any())).thenThrow(new AppException(ErrorCode.INVALID_PASSWORD, ""));

        // then
        mockMvc.perform(post("/api/v1/users/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(userJoinRequest)))
                .andDo(print())
                .andExpect(status().is(ErrorCode.INVALID_PASSWORD.getStatus().value()));
    }
}

// {$.~~} = 변수가 들어오는 것

