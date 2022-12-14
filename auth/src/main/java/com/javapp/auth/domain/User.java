package com.javapp.auth.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // UUID 사용으로 변경 예정
    private Long userId;

    @Column(nullable = false, length = 20)
    private String username;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private Boolean emailVerified = false;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    private String role;

    @Builder
    public User(Long userId, String email, String username, String password, String role){
        this.userId = userId;
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
    }
}