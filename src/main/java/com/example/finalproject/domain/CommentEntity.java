package com.example.finalproject.domain;

import com.example.finalproject.controller.CommentController;
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
    private CommentEntity post;

    private String userName;
    private String comment;


    public static CommentController.CommentResponse of(CommentEntity commentEntity) {
        return new CommentController.CommentResponse(commentEntity.getId(),
                commentEntity.getUser().getUserName(),
                commentEntity.getComment(),
                commentEntity.getCreatedAt(),
                commentEntity.getLastModifiedAt());
    }

}

