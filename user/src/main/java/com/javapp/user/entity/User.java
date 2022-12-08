package com.javapp.user.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Singular;

import javax.persistence.*;


@Getter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseTimeEntity{
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
    private String password;

    private String role;
}
