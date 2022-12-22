package com.example.finalproject.adapter;

import com.example.finalproject.domain.dto.PostEntity;
import com.example.finalproject.repository.JpaPostRepository;
import com.example.finalproject.service.PostAddService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PostAddJpaRepositoryAdapter implements PostAddService.PostAddRepository {
    private final JpaPostRepository delegate;

    @Override
    public long add(String title, String body, String userName) {
        PostEntity savedEntity = delegate.save(PostEntity.builder()
                .title(title)
                .body(body)
                .userName(userName)
                .build());
        return savedEntity.getId();
    }
}
