package com.javapp.auth.service;

import com.javapp.auth.domain.User;
import com.javapp.auth.dto.JwtRequestDto;
import com.javapp.auth.dto.SignUpDto;
import com.javapp.auth.dto.UserDto;
import com.javapp.auth.security.jwt.JwtAuthResponse;

public interface SignService {
    User findById(Long userId);

    JwtAuthResponse siginIn(JwtRequestDto jwtRequestDto);

    UserDto createUser(SignUpDto signUpDto);

    boolean existByEmail(String email);
}
