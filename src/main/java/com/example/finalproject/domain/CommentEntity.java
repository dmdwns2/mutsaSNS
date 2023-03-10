package com.example.finalproject.domain;

import com.example.finalproject.domain.dto.response.CommentResponse;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity@Where(clause = "deleted_date is null")
@SQLDelete(sql = "UPDATE comment_entity SET deleted_date = now() WHERE id = ?")
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

    public CommentResponse toResponse() {
        return CommentResponse.builder()
                .id(this.id)
                .comment(this.comment)
                .userName(this.userId.getUserName())
                .postId(this.getPostId().getId())
                .createdAt(getCreatedAt())
                .build();
    }

    public void update(String comment){
        this.comment = comment;
    }
}

