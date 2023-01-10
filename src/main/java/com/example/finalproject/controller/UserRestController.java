package com.example.finalproject.controller;

import com.example.finalproject.domain.dto.UserDto;
import com.example.finalproject.domain.dto.request.UserJoinRequest;
import com.example.finalproject.domain.dto.request.UserLoginRequest;
import com.example.finalproject.domain.dto.response.UserJoinResponse;
import com.example.finalproject.domain.dto.response.UserLoginResponse;
import com.example.finalproject.domain.response.Response;
import com.example.finalproject.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "User Api")
public class UserRestController {
    private final UserService service;

    @ApiOperation(value = "회원가입")
    @PostMapping("/join")
    public Response<UserJoinResponse> join(@RequestBody UserJoinRequest request) {
        log.info("{}", request);
        UserDto userDto = service.join(request.getUserName(), request.getPassword());
        UserJoinResponse response = UserJoinResponse.fromUser(userDto);
        return Response.success(response);
    }

    @ApiOperation(value = "로그인")
    @PostMapping("/login")
    public Response<UserLoginResponse> login(@RequestBody UserLoginRequest userLoginRequest) {
        log.info("login 요청이 들어왔습니다.");
        String jwt = service.login(userLoginRequest.getUserName(), userLoginRequest.getPassword());
        return Response.success(new UserLoginResponse(jwt));
    }

}
