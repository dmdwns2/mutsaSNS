package com.example.finalproject.service;

import com.example.finalproject.domain.UserEntity;
import com.example.finalproject.domain.dto.UserDto;
import com.example.finalproject.enums.UserRole;
import com.example.finalproject.exception.AppException;
import com.example.finalproject.exception.ErrorCode;
import com.example.finalproject.repository.UserRepository;
import com.example.finalproject.utils.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    public UserEntity getUserByUserName(String userName) {
        UserEntity user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND, ""));
        return user;
    }

    @Value("${jwt.token.secret}")
    private String secretKey;
    private long expiredTimeMs = 1000 * 60 * 60; // 1시간


    @Transactional
    public UserDto join(String userName, String password) {
        userRepository.findByUserName(userName).ifPresent(user -> {
            throw new AppException(ErrorCode.DUPLICATED_USER_NAME, String.format("UserName %s is duplicated", userName));
        });

        UserEntity user = UserEntity.builder()
                .userName(userName)
                .password(encoder.encode(password))
                .role(UserRole.USER)
                .build();

        UserEntity savedUser = userRepository.save(user);
        return UserDto.fromEntity(savedUser);
    }
    public String login(String userName, String password) {
        UserEntity userEntity = userRepository.findByUserName(userName)
                .orElseThrow(() ->
                        new AppException(ErrorCode.USERNAME_NOT_FOUND, ErrorCode.USERNAME_NOT_FOUND.getMessage()));

        if(!encoder.matches(password, userEntity.getPassword())){
            throw new AppException(ErrorCode.INVALID_PASSWORD, ErrorCode.INVALID_PASSWORD.getMessage());
        }

        return JwtTokenUtil.createToken(userName, secretKey, expiredTimeMs);
    }

}
