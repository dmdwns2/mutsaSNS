package com.example.finalproject.service;

import com.example.finalproject.domain.User;
import com.example.finalproject.domain.dto.UserDto;
import com.example.finalproject.domain.dto.UserJoinRequest;
import com.example.finalproject.exception.AppException;
import com.example.finalproject.exception.ErrorCode;
import com.example.finalproject.repository.UserRepository;
import com.example.finalproject.utils.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final BCryptPasswordEncoder encoder;

    @Value("${jwt.token.secret}")
    private String secretKey;
    private long expireTimeMs = 1000 * 60 * 60; // 1시간


    public UserDto join(UserJoinRequest request) {

        repository.findByUserName(request.getUserName())
                .ifPresent(user ->{
                    throw new AppException(ErrorCode.DUPLICATED_USER_NAME, String.format("UserName:%s", request.getUserName()));
                });

        // 회원가입 .save()
        User savedUser = repository.save(request.toEntity(encoder.encode(request.getPassword())));
        return UserDto.builder()
                .id(savedUser.getId())
                .userName(savedUser.getUserName())
                .build();
    }

    public String login(String userName, String password) {

        // userName있는지 여부 확인
        // 없으면 NOT_FOUND에러 발생
        User user = repository.findByUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, String.format("%s는 가입된 적이 없습니다.", userName)));

        // password일치 하는지 여부 확인
        if(!encoder.matches(password, user.getPassword())){
            throw new AppException(ErrorCode.INVALID_PASSWORD, String.format("userName 또는 password가 잘못 됐습니다."));
        }

        // 두가지 확인중 예외 안났으면 Token발행
        return JwtTokenUtil.createToken(userName, secretKey, expireTimeMs);
    }
}
