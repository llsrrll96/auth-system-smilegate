package com.javapp.auth.service;

import com.javapp.auth.domain.User;
import com.javapp.auth.dto.JwtRequestDto;
import com.javapp.auth.dto.SignUpDto;
import com.javapp.auth.dto.UserDto;

public interface AuthService {
    User findById(Long userId);

    String getJwtToken(JwtRequestDto jwtRequestDto);

    UserDto createUser(SignUpDto signUpDto);
}
