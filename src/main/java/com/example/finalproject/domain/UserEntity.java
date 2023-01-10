package com.example.finalproject.domain;

import com.example.finalproject.enums.UserRole;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserEntity {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    @NotNull
    private String userName;
    @NotBlank
    @NotNull
    private String password;

    @OneToMany(mappedBy = "postId", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @OrderBy("id asc") // 댓글 정렬
    private List<CommentEntity> comment;

    @Enumerated(EnumType.STRING)
    private UserRole role;
}
