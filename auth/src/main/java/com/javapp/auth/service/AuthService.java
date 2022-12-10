package com.javapp.auth.service;

import com.javapp.auth.domain.User;

public interface AuthService {
    User findById(Long userId);
}
