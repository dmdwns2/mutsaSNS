package com.example.finalproject.domain;

import com.example.finalproject.enums.UserRole;
import lombok.*;

import javax.persistence.*;
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
    private String userName;
    private String password;

    @OneToMany(mappedBy = "postId", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @OrderBy("id asc") // 댓글 정렬
    private List<CommentEntity> comment;

    @Enumerated(EnumType.STRING)
    private UserRole role;
}
