package com.javapp.auth.service;

import com.javapp.auth.domain.User;
import com.javapp.auth.domain.repository.UserJpaRepository;
import com.javapp.auth.dto.RefreshJwtDto;
import com.javapp.auth.exception.ErrorCode;
import com.javapp.auth.exception.TokenException;
import com.javapp.auth.exception.UserNotFoundException;
import com.javapp.auth.security.jwt.JwtAuthResponse;
import com.javapp.auth.security.jwt.JwtTokenProvider;
import com.javapp.auth.service.redis.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Map;


@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService{
    private final UserJpaRepository userJpaRepository;
    private final JwtTokenProvider tokenProvider;

    private final RedisUtil redisUtil;

    @Transactional
    @Override
    public JwtAuthResponse generateATandRT(User user) {
        String accessToken = tokenProvider.generateAccessToken(user);
        String refreshToken = tokenProvider.generateRefreshToken(user);

        redisUtil.setValues(String.valueOf(user.getUserId()),refreshToken, Duration.ofMillis(tokenProvider.getRefreshExpirationInMs()));

        return JwtAuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Transactional
    @Override
    public JwtAuthResponse refreshToken(RefreshJwtDto refreshJwtDto) {
        // RT 만료 확인 후 AT RT 재발급
        tokenProvider.validateRefreshToken(refreshJwtDto.getRefreshToken());

        Map<String, Object> userMap = tokenProvider.getUserFromRefreshToken(refreshJwtDto.getRefreshToken());
        Long userId = Long.valueOf(userMap.get("userId").toString());
        // 레디스 확인
        String tokenInRedis = redisUtil.getValues(String.valueOf(userId));

        if(tokenInRedis != null){
            // AT 재발급
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
        String userId = tokenProvider.getUserFromRefreshToken(refreshJwtDto.getRefreshToken()).get("userId").toString();
        redisUtil.deleteValues(userId);
        return true;
    }
}
