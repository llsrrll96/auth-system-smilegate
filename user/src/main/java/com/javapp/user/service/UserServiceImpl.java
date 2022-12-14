package com.javapp.user.service;

import com.javapp.user.dto.user.UserDto;
import com.javapp.user.entity.user.User;
import com.javapp.user.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserJpaRepository userJpaRepository;


    private UserDto mapToUserDto(User user){
        return new UserDto(user);
    }

    @Override
    public List<UserDto> findByUsers() {
        List<User> list = userJpaRepository.findAll();
        return list.stream().map(l-> mapToUserDto(l)).collect(Collectors.toList());
    }

    @Override
    public UserDto findUserDetail(Long userId) {
        return new UserDto(userJpaRepository.findById(userId).get());
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
