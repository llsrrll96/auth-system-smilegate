package com.javapp.auth.security.jwt.models;

import com.javapp.auth.domain.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Entity(name = "token")
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tokenId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @Column(nullable = false, unique = true)
    private String accessToken;

    @Column(nullable = false, unique = true)
    private String refreshToken;

    @Column(nullable = false)
    private LocalDateTime generateAtDateTime;

    @Builder
    public Token(User user, String accessToken, String refreshToken, LocalDateTime generateAtDateTime) {
        this.user = user;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.generateAtDateTime = generateAtDateTime;
    }
}
