package com.javapp.user.service;

import com.javapp.user.dto.user.SignUpDto;
import com.javapp.user.dto.user.UserDto;
import com.javapp.user.entity.user.Role;
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

    @Override
    public UserDto createUser(SignUpDto signUpDto) {
        // 검증

        User user = mapToUserEntity(signUpDto);

        // 추후 인증서버를 통해 암호화된 비밀번호를 받아와 회원가입 진행

        return new UserDto(userJpaRepository.save(user));
    }

    private User mapToUserEntity(SignUpDto signUpDto) {
        return User.builder()
                .username(signUpDto.getUsername())
                .email(signUpDto.getEmail())
                .password(signUpDto.getPassword())
                .role(Role.ROLE_USER.getType())
                .build();
    }

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
