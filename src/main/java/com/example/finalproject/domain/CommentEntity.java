package com.example.finalproject.domain;

import com.example.finalproject.domain.dto.response.CommentResponse;
import lombok.*;

import javax.persistence.*;

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

    @Column(columnDefinition = "TEXT", nullable = false)
    private String comment;

    public static CommentResponse of(CommentEntity commentEntity) {
        return new CommentResponse(commentEntity.getId(),
                commentEntity.getUser().getUserName(),
                commentEntity.getComment(),
                commentEntity.getCreatedAt(),
                commentEntity.getLastModifiedAt());
    }

}

