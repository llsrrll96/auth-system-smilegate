package com.javapp.auth.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // UUID 사용으로 변경 예정
    private Long userId;

    @Column(nullable = false, length = 20)
    private String username;

    @Column(nullable = false)
    private String email;

    private String role;
}