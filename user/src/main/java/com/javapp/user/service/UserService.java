package com.javapp.user.service;

import com.javapp.user.dto.UserDto;
import com.javapp.user.entity.User;

import java.util.List;

public interface UserService {
    // c
    UserDto createUser(User user);

    // r
    List<UserDto> findByUsers();

    // u
    UserDto updateUser(User user);

    // d
    void deleteUser(User user);
}
