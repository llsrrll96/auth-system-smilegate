package com.javapp.auth.service;

import com.javapp.auth.domain.User;
import com.javapp.auth.domain.repository.UserJpaRepository;
import com.javapp.auth.dto.RefreshJwtDto;
import com.javapp.auth.security.jwt.JwtAuthResponse;
import com.javapp.auth.security.jwt.JwtTokenProvider;
import com.javapp.auth.security.jwt.models.Token;
import com.javapp.auth.security.jwt.repository.TokenJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService{
    private final UserJpaRepository userJpaRepository;
    private final TokenJpaRepository tokenJpaRepository;
    private final JwtTokenProvider tokenProvider;

    @Override
    public Token generateATandRT(User user) {
        String accessToken = tokenProvider.generateAccessToken(user);
        String refreshToken = tokenProvider.generateRefreshToken(user);

        // DB 작업도 여기해야 될듯

        return Token.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .generateAtDateTime(LocalDateTime.now())
                .build();
    }

    @Override
    public JwtAuthResponse refreshToken(RefreshJwtDto refreshJwtDto) {
        // RT 만료 확인 후 AT 재발급 else AT RT 재발급
        Token token = tokenJpaRepository.findByAccessToken(refreshJwtDto.getAccessToken());
        boolean isValidToken = tokenProvider.validateRefreshToken(token.getRefreshToken());
        if(isValidToken){
            //AT
        }

        Token newToken = generateATandRT(token.getUser());

        return JwtAuthResponse.builder()
                .accessToken(newToken.getAccessToken())
                .refreshToken(newToken.getRefreshToken())
                .build();
    }
}
