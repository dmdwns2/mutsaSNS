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
        // @return id of the inserted post
        long add(String title, String body);
    }

    @AllArgsConstructor
    public static class PostAddCommand {
        private final String title;
        private final String body;
    }

    public long add(PostAddCommand command){
        validate(command);
        return repository.add(command.title, command.body);
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
    }
}
