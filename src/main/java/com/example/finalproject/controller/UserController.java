package com.example.finalproject.controller;

import com.example.finalproject.domain.Response;
import com.example.finalproject.domain.dto.*;
import com.example.finalproject.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@Slf4j
@AllArgsConstructor
public class UserController {
    private final UserService service;

    @PostMapping("/join")
    public Response<UserJoinResponse> Join(@RequestBody UserJoinRequest userJoinRequest){
        log.info("join으로 요청이 들어왔습니다.");
        UserDto userDto = service.join(userJoinRequest);
        return Response.suceess(new UserJoinResponse(userDto.getUserName()));
    }

    @PostMapping("/login")
    public Response<UserLoginResponse> login(@RequestBody UserLoginRequest userLoginRequest) {
        log.info("login 요청이 들어왔습니다.");
        String token = service.login(userLoginRequest.getUserName(), userLoginRequest.getPassword());
        return Response.suceess(new UserLoginResponse(token));
    }
}
