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


    @ManyToOne
    @JoinColumn(name = "user_id") // join은 안쓰는게 좋음 가져오는동안 양쪽 db에 락이 걸림 , 트랜잭션도 오버헤드가 크다
    private UserEntity userEntity;

    public static PostDto of(PostEntity postEntity) {
        return new PostDto(postEntity.getId(), postEntity.getTitle(), postEntity.getBody(), postEntity.getUserEntity().getUserName());
    }

    public PostResponse toResponse() {
        return PostResponse.builder()
                .id(this.id)
                .title(this.title)
                .body(this.body)
                .userName(this.userEntity.getUserName())
                .build();
    }
}
