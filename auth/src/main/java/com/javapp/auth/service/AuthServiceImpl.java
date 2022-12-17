package com.javapp.auth.service;

import com.javapp.auth.domain.User;
import com.javapp.auth.security.jwt.JwtTokenProvider;
import com.javapp.auth.security.jwt.models.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService{

    private final JwtTokenProvider tokenProvider;

    @Override
    public Token generateATandRT(User user) {
        String accessToken = tokenProvider.generateAccessToken(user);
        String refreshToken = tokenProvider.generateRefreshToken(user);

        return Token.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .generateAtDateTime(LocalDateTime.now())
                .build();
    }
}
