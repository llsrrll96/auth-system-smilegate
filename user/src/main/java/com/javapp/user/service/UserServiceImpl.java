package com.javapp.user.service;

import com.javapp.user.dto.user.RequestUserDto;
import com.javapp.user.dto.user.UserDto;
import com.javapp.user.entity.user.User;
import com.javapp.user.exception.ErrorCode;
import com.javapp.user.exception.UserNotFoundByIdException;
import com.javapp.user.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        return new UserDto(userJpaRepository.findById(userId).orElseThrow(
                ()-> new UserNotFoundByIdException(ErrorCode.USER_NOT_FOUND)
        ));
    }

    @Transactional
    @Override
    public UserDto updateUser(RequestUserDto requestUserDto) {
        User user = userJpaRepository.findById(requestUserDto.getUserId()).orElseThrow(
                ()-> new UserNotFoundByIdException(ErrorCode.USER_NOT_FOUND_BYID)
        );
        user.setUsername(requestUserDto.getUsername());

        return new UserDto(user);
    }

    @Override
    public void deleteUser(User user) {
        userJpaRepository.deleteById(user.getUserId());
    }
}
