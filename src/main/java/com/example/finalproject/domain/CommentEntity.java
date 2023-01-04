package com.example.finalproject.domain;

import com.example.finalproject.domain.dto.response.CommentResponse;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CommentEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private PostEntity postId;

    @NotNull
    @NotBlank
    @Size(min = 1, max = 255)
    @Column(columnDefinition = "TEXT")
    private String comment;

    public static CommentResponse of(CommentEntity commentEntity) {
        return new CommentResponse(commentEntity.getId(),
                commentEntity.getComment(),
                commentEntity.getUserId().getUserName(),
                commentEntity.getPostId().getId(),
                commentEntity.getCreatedAt());
    }

    public CommentResponse toResponse() {
        return CommentResponse.builder()
                .comment(this.comment)
                .userName(this.userId.getUserName())
                .createdAt(getCreatedAt())
                .build();

    }
}

