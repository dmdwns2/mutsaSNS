package com.example.finalproject.domain;

import com.example.finalproject.domain.dto.PostDto;
import com.example.finalproject.domain.dto.response.PostResponse;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Where(clause = "deleted_date is null")
@SQLDelete(sql = "UPDATE post_entity SET deleted_date = now() WHERE id = ?")
public class PostEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @NotNull
    private String title;
    @NotBlank
    @NotNull
    @Column(columnDefinition = "TEXT")
    private String body;
    @NotBlank
    @NotNull
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
