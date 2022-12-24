package com.example.finalproject.service;

import com.example.finalproject.domain.dto.Post;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PostGetService {
    private final PostGetRepository repository;

    public interface PostGetRepository {
        Post find (long id);
    }

    public Post find(long id){
        return repository.find(id);
    }
}
