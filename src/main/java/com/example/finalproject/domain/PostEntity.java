package com.example.finalproject.domain;

import com.example.finalproject.domain.dto.PostDto;
import com.example.finalproject.domain.dto.response.PostResponse;
import lombok.*;

import javax.persistence.*;
import java.util.List;

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

    @OneToMany(mappedBy = "postId", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @OrderBy("id asc") // 댓글 정렬
    private List<CommentEntity> comments;

    public static PostDto of(PostEntity postEntity) {
        return new PostDto(postEntity.getId(), postEntity.getTitle(), postEntity.getBody(),
                postEntity.getUserName(), postEntity.getCreatedAt(), postEntity.getLastModifiedAt());
    }

    public PostResponse toResponse() {
        return PostResponse.builder()
                .postId(this.id)
                .title(this.title)
                .body(this.body)
                .userName(this.userName)
                .createdAt(getCreatedAt())
                .lastModifiedAt(getLastModifiedAt())
                .build();
    }
}
