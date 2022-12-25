package com.example.finalproject.domain;

import com.example.finalproject.domain.dto.PostDto;
import com.example.finalproject.domain.dto.PostResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PostEntity extends BaseEntity{
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String body;
    private String userName;


    public static PostDto of(PostEntity postEntity) {
        return new PostDto(postEntity.getId(), postEntity.getTitle(), postEntity.getBody(), postEntity.getUserName());
    }

    public PostResponse toResponse() {
        return PostResponse.builder()
                .id(this.id)
                .title(this.title)
                .body(this.body)
                .userName(this.userName)
                .build();
    }
}
