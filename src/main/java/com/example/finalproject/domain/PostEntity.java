package com.example.finalproject.domain;

import com.example.finalproject.domain.dto.PostDto;
import com.example.finalproject.domain.dto.PostResponse;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Setter
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
        return new PostDto(postEntity.getId(), postEntity.getTitle(), postEntity.getBody(),
                postEntity.getUserName(), postEntity.getCreatedAt(), postEntity.getLastModifiedAt());
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