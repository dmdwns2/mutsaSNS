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
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private PostEntity post;

    @NotNull
    @NotBlank
    @Size(min = 1, max = 255)
    @Column(columnDefinition = "TEXT")
    private String comment;

    private String userName;

    public static CommentResponse of(CommentEntity commentEntity) {
        return new CommentResponse(commentEntity.getId(),
                commentEntity.getUser().getUserName(),
                commentEntity.getComment(),
                commentEntity.getCreatedAt(),
                commentEntity.getLastModifiedAt());
    }

    public CommentResponse toResponse() {
        return CommentResponse.builder()
                .comment(this.comment)
                .userName(this.user.getUserName())
                .createdAt(getCreatedAt())
                .build();

    }
}

