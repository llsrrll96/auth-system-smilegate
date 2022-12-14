package com.javapp.user.service;

import com.javapp.user.dto.user.UserDto;
import com.javapp.user.entity.user.User;

import java.util.List;

public interface UserService {
    // r
    List<UserDto> findByUsers();

    UserDto findUserDetail(Long userId);

    // u
    UserDto updateUser(User user);

    // d
    void deleteUser(User user);
}
