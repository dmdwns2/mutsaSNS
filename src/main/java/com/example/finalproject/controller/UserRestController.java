package com.example.finalproject.controller;

import com.example.finalproject.domain.dto.*;
import com.example.finalproject.domain.response.Response;
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
public class UserRestController {
    private final UserService service;

    @PostMapping("/join")
    public Response<UserJoinResponse> join(@RequestBody UserJoinRequest request) {
        log.info("{}", request);
        UserDto userDto = service.join(request.getUserName(), request.getPassword());
        UserJoinResponse response = UserJoinResponse.fromUser(userDto);
        return Response.success(response);
    }
    @PostMapping("/login")
    public Response<UserLoginResponse> login(@RequestBody UserLoginRequest userLoginRequest) {
        log.info("login 요청이 들어왔습니다.");
        String jwt = service.login(userLoginRequest.getUserName(), userLoginRequest.getPassword());
        return Response.success(new UserLoginResponse(jwt));
    }
}
