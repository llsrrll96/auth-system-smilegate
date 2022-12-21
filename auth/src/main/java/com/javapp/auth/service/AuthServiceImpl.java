package com.javapp.auth.service;

import com.javapp.auth.domain.User;
import com.javapp.auth.domain.repository.UserJpaRepository;
import com.javapp.auth.dto.RefreshJwtDto;
import com.javapp.auth.exception.ErrorCode;
import com.javapp.auth.exception.TokenException;
import com.javapp.auth.exception.UserNotFoundException;
import com.javapp.auth.security.jwt.JwtAuthResponse;
import com.javapp.auth.security.jwt.JwtTokenProvider;
import com.javapp.auth.security.jwt.models.Token;
import com.javapp.auth.security.jwt.repository.TokenJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;


@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService{
    private final UserJpaRepository userJpaRepository;
    private final TokenJpaRepository tokenJpaRepository;
    private final JwtTokenProvider tokenProvider;


    @Transactional
    @Override
    public JwtAuthResponse generateATandRT(User user) {
        String accessToken = tokenProvider.generateAccessToken(user);
        String refreshToken = tokenProvider.generateRefreshToken(user);

        // delete previous row
        tokenJpaRepository.deleteByUser(user);

        // save to token table
        Token userToken = Token.builder()
                .refreshToken(refreshToken)
                .generateAtDateTime(LocalDateTime.now())
                .user(user)
                .build();
        tokenJpaRepository.save(userToken);

        return JwtAuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Transactional
    @Override
    public JwtAuthResponse refreshToken(RefreshJwtDto refreshJwtDto) {
        // RT 만료 확인 후 AT RT 재발급

        boolean isValidToken = tokenProvider.validateRefreshToken(refreshJwtDto.getRefreshToken());

        if(isValidToken){
            // AT 재발급
            Map<String, Object> userMap = tokenProvider.getUserFromRefreshToken(refreshJwtDto.getRefreshToken());
            Long userId = Long.valueOf(userMap.get("userId").toString());
            User user = userJpaRepository.findById(userId).orElseThrow(
                    ()->new UserNotFoundException(ErrorCode.USER_NOT_FOUND)
            );

            return generateATandRT(user);

        }else{
            throw new TokenException(ErrorCode.REFRESHTOKEN_EXPIRATION);
        }

    }

    @Transactional
    @Override
    public boolean deleteToken(RefreshJwtDto refreshJwtDto) {
        tokenJpaRepository.deleteByRefreshToken(refreshJwtDto.getRefreshToken());
        return true;
    }
}
