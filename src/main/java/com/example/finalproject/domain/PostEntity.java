package com.example.finalproject.domain;

import com.example.finalproject.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PostEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String body;


    @ManyToOne
    @JoinColumn(name = "user_id") // join은 안쓰는게 좋음 가져오는동안 양쪽 db에 락이 걸림 , 트랜잭션도 오버헤드가 크다
    private User user;
}
