package com.javapp.auth.service;

import com.javapp.auth.domain.Role;
import com.javapp.auth.domain.User;
import com.javapp.auth.domain.repository.AuthJpaRepository;
import com.javapp.auth.dto.JwtRequestDto;
import com.javapp.auth.dto.SignUpDto;
import com.javapp.auth.dto.UserDto;
import com.javapp.auth.exception.UserNotFoundException;
import com.javapp.auth.security.jwt.JwtAuthResponse;
import com.javapp.auth.security.jwt.models.Token;
import com.javapp.auth.security.jwt.repository.TokenJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class SignServiceImpl implements SignService{
    private final AuthenticationManager authenticationManager;
    private final AuthService authService;
    private final AuthJpaRepository authJpaRepository;
    private final TokenJpaRepository tokenJpaRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    public User findById(Long userId){
        return authJpaRepository.findById(userId).orElseThrow(
                ()-> new UserNotFoundException("유저 없음", HttpStatus.NOT_FOUND));
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

        return new UserDto(authJpaRepository.save(user));
    }

    @Override
    public boolean existByEmail(String email) {
        return authJpaRepository.existsByEmail(email);
    }

    @Transactional
    @Override
    public JwtAuthResponse siginIn(JwtRequestDto jwtRequestDto) {
        User user = validateIdAndPasswordForSignIn(jwtRequestDto);

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                user.getUserId(), jwtRequestDto.getPassword()
        ));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Token token = authService.generateATandRT(user);
        String accessToken = token.getAccessToken();
        String refreshToken = token.getRefreshToken();

        // delete previous row
        tokenJpaRepository.deleteByUser(user);

        // save to token table
        Token usertoken = Token.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .generateAtDateTime(LocalDateTime.now())
                .user(user)
                .build();
        tokenJpaRepository.save(usertoken);

        return JwtAuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    private User validateIdAndPasswordForSignIn(JwtRequestDto jwtRequestDto) {
        User user = authJpaRepository.findByEmail(jwtRequestDto.getEmail()).orElseThrow(
                ()->new UserNotFoundException("아이디 또는 비밀번호를 잘못 입력했습니다.",HttpStatus.NOT_FOUND)
        );
        // validate password
        if(!bCryptPasswordEncoder.matches(jwtRequestDto.getPassword(),user.getPassword())){
            throw new UserNotFoundException("아이디 또는 비밀번호를 잘못 입력했습니다.",HttpStatus.NOT_FOUND);
        }
        return user;
    }
}
