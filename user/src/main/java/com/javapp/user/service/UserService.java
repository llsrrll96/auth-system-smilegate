package com.javapp.user.service;

import com.javapp.user.dto.user.SignUpDto;
import com.javapp.user.dto.user.UserDto;
import com.javapp.user.entity.user.User;

import java.util.List;

public interface UserService {
    // c
    UserDto createUser(SignUpDto signUpDto);

    // r
    List<UserDto> findByUsers();

    // u
    UserDto updateUser(User user);

    // d
    void deleteUser(User user);
}
