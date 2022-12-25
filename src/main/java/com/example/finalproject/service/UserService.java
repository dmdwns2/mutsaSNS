package com.example.finalproject.service;

import com.example.finalproject.domain.UserEntity;
import com.example.finalproject.domain.dto.UserDto;
import com.example.finalproject.domain.dto.UserJoinRequest;
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

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    public UserEntity getUserByUserName(String userName) {
        UserEntity user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUNDED, ""));
        return user;
    }

    @Value("${jwt.token.secret}")
    private String secretKey;



    private Long expiredTimeMs = 1000 * 60 * 60l;


    public UserDto join(String userName, String password) {
        // 중복 Check
        userRepository.findByUserName(userName).ifPresent(user -> {
            throw new AppException(ErrorCode.DUPLICATED_USER_NAME, String.format("UserName %s is duplicated", userName));
        });

        // 위에서 에러가 안났다면 회원가입(DB에 저장)
        UserEntity user = UserEntity.builder()
                .userName(userName)
                .password(encoder.encode(password))
                .role(UserRole.USER)
                .build();

        UserEntity savedUser = userRepository.save(user);

        return UserDto.fromEntity(savedUser);
    }
    public String login(String userName, String password) {
        return JwtTokenUtil.createToken(userName, secretKey, expiredTimeMs);
    }
}
