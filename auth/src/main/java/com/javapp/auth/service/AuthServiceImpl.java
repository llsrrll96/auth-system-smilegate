package com.javapp.auth.service;

import com.javapp.auth.domain.User;
import com.javapp.auth.domain.repository.UserJpaRepository;
import com.javapp.auth.dto.RefreshJwtDto;
import com.javapp.auth.exception.ErrorCode;
import com.javapp.auth.exception.TokenNotFoundException;
import com.javapp.auth.security.jwt.JwtAuthResponse;
import com.javapp.auth.security.jwt.JwtTokenProvider;
import com.javapp.auth.security.jwt.models.Token;
import com.javapp.auth.security.jwt.repository.TokenJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService{
    private final UserJpaRepository userJpaRepository;
    private final TokenJpaRepository tokenJpaRepository;
    private final JwtTokenProvider tokenProvider;

    @Override
    public Token generateAT(User user) {
        String accessToken = tokenProvider.generateAccessToken(user);

        Token userToken = Token.builder()
                .accessToken(accessToken)
                .generateAtDateTime(LocalDateTime.now())
                .user(user)
                .build();

        return userToken;
    }

    @Transactional
    @Override
    public Token generateATandRT(User user) {
        String accessToken = tokenProvider.generateAccessToken(user);
        String refreshToken = tokenProvider.generateRefreshToken(user);

        // delete previous row
        tokenJpaRepository.deleteByUser(user);

        // save to token table
        Token userToken = Token.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .generateAtDateTime(LocalDateTime.now())
                .user(user)
                .build();
        tokenJpaRepository.save(userToken);

        return Token.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .generateAtDateTime(LocalDateTime.now())
                .build();
    }

    @Transactional
    @Override
    public JwtAuthResponse refreshToken(RefreshJwtDto refreshJwtDto) {
        // RT 만료 확인 후 AT 재발급 else AT RT 재발급
        Token token = tokenJpaRepository.findByAccessToken(refreshJwtDto.getAccessToken()).orElseThrow(
                () -> new TokenNotFoundException(ErrorCode.TOKEN_IS_NOT_FOUND)
        );
        boolean isValidToken = tokenProvider.validateRefreshToken(token.getRefreshToken());

        Token newToken = null;
        if(isValidToken){
            //AT
            newToken = generateAT(token.getUser());
            newToken.setRefreshToken(token.getRefreshToken());

            token.setAccessToken(newToken.getAccessToken());
        }else{
            newToken = generateATandRT(token.getUser());
        }

        return JwtAuthResponse.builder()
                .accessToken(newToken.getAccessToken())
                .refreshToken(newToken.getRefreshToken())
                .build();
    }

    @Transactional
    @Override
    public boolean deleteToken(RefreshJwtDto refreshJwtDto) {
        tokenJpaRepository.deleteByAccessToken(refreshJwtDto.getAccessToken());
        return true;
    }
}
