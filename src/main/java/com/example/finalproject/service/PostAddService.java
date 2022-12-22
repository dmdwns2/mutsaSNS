package com.example.finalproject.service;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

@Service
@AllArgsConstructor
public class PostAddService {

    private final PostAddRepository repository;

    public interface PostAddRepository {
        long add(String title, String body, String userName);
    }

    @AllArgsConstructor
    public static class PostAddCommand {
        private final String title;
        private final String body;
        private final String userName;
    }

    public long add(PostAddCommand command){
        validate(command);
        return repository.add(command.title, command.body, command.userName);
    }

    private void validate(PostAddCommand command) {
        if(isNull(command)){
            throw new IllegalArgumentException("command is empty");
        }
        if(StringUtils.isBlank(command.title)){
            throw new IllegalArgumentException("title is empty");
        }
        if(StringUtils.isBlank(command.body)){
            throw new IllegalArgumentException("body is empty");
        }
        if(StringUtils.isBlank(command.userName)){
            throw new IllegalArgumentException("userName is empty");
        }

    }
}
