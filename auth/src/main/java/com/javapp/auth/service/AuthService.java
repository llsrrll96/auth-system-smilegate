package com.javapp.auth.service;

import com.javapp.auth.domain.User;
import com.javapp.auth.dto.RefreshJwtDto;
import com.javapp.auth.security.jwt.JwtAuthResponse;

public interface AuthService {
    JwtAuthResponse generateATandRT(User user);

    JwtAuthResponse refreshToken(RefreshJwtDto refreshJwtDto);

    boolean deleteToken(RefreshJwtDto refreshJwtDto);

    // RT 확인후 AT 만 재발급

    // RT 만료시키기
}
