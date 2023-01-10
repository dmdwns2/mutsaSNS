package com.example.finalproject.domain;

import com.example.finalproject.domain.dto.PostDto;
import com.example.finalproject.domain.dto.response.PostResponse;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PostEntity extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;
    @NotBlank
    @NotNull
    private String title;
    @NotBlank
    @NotNull
    private String body;
    @NotBlank
    @NotNull
    private String userName;

//    @OneToMany(mappedBy = "postId", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
//    @OrderBy("id asc") // 댓글 정렬
//    private List<CommentEntity> comments;

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
