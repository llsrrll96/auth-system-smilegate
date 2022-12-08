package com.javapp.user.service;

import com.javapp.user.dto.UserDto;
import com.javapp.user.entity.User;
import com.javapp.user.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserJpaRepository userJpaRepository;

    @Override
    public UserDto createUser(User user) {
        userJpaRepository.save(user);
        return null;
    }

    @Override
    public List<UserDto> findByUsers() {
        userJpaRepository.findAll();
        return null;
    }

    @Override
    public UserDto updateUser(User user) {
        return null;
    }

    @Override
    public void deleteUser(User user) {
        userJpaRepository.deleteById(user.getUserId());
    }
}
