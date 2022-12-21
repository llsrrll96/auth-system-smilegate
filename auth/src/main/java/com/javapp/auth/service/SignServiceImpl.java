package com.javapp.auth.service;

import com.javapp.auth.domain.Role;
import com.javapp.auth.domain.User;
import com.javapp.auth.domain.repository.UserJpaRepository;
import com.javapp.auth.dto.JwtRequestDto;
import com.javapp.auth.dto.SignUpDto;
import com.javapp.auth.dto.UserDto;
import com.javapp.auth.exception.ErrorCode;
import com.javapp.auth.exception.UserNotFoundException;
import com.javapp.auth.security.jwt.JwtAuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
public class SignServiceImpl implements SignService{
    private final AuthenticationManager authenticationManager;
    private final AuthService authService;
    private final UserJpaRepository userJpaRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    public User findById(Long userId){
        return userJpaRepository.findById(userId).orElseThrow(
                ()-> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));
    }

    @Override
    public UserDto createUser(SignUpDto signUpDto) {

        String rawPwd= signUpDto.getPassword();
        String encPwd = bCryptPasswordEncoder.encode(rawPwd);
        User user = User.builder()
                .username(signUpDto.getUsername())
                .email(signUpDto.getEmail())
                .password(encPwd)
                .role(Role.ROLE_USER.getType())
                .build();

        return new UserDto(userJpaRepository.save(user));
    }

    @Override
    public boolean existByEmail(String email) {
        return userJpaRepository.existsByEmail(email);
    }

    @Transactional
    @Override
    public JwtAuthResponse siginIn(JwtRequestDto jwtRequestDto) {
        User user = validateIdAndPasswordForSignIn(jwtRequestDto);

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                user.getUserId(), jwtRequestDto.getPassword()
        ));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return authService.generateATandRT(user);
    }

    private User validateIdAndPasswordForSignIn(JwtRequestDto jwtRequestDto) {
        User user = userJpaRepository.findByEmail(jwtRequestDto.getEmail()).orElseThrow(
                ()->new UserNotFoundException(ErrorCode.USER_NOT_FOUND)
        );
        // validate password
        if(!bCryptPasswordEncoder.matches(jwtRequestDto.getPassword(),user.getPassword())){
            throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
        }
        return user;
    }
}
