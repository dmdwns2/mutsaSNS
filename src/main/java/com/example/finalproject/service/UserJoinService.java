package com.example.finalproject.service;

import com.example.finalproject.domain.User;
import com.example.finalproject.domain.dto.UserDto;
import com.example.finalproject.domain.dto.UserJoinRequest;
import com.example.finalproject.exception.AppException;
import com.example.finalproject.exception.ErrorCode;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserJoinService {

    private final UserJoinRepository repository;
    private final BCryptPasswordEncoder encoder;

    public interface UserJoinRepository extends JpaRepository<User, Long> {
        Optional<User> findByUserName(String userName);
    }

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
}
