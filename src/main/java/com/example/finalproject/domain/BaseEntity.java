package com.example.finalproject.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

    @CreatedDate // 데이터 생성날짜를 자동으로 주입하는 어노테이션
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate // 데이터 수정날짜를 자동으로 주입하는 어노테이션
    private LocalDateTime lastModifiedAt;
}

