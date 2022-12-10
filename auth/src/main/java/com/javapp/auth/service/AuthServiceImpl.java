package com.javapp.auth.service;


import com.javapp.auth.domain.User;
import com.javapp.auth.domain.repository.AuthJpaRepository;
import com.javapp.auth.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService{

    @Autowired
    private AuthJpaRepository authJpaRepository;

    public User findById(Long userId){
        return authJpaRepository.findById(userId).orElseThrow(
                ()-> new UserNotFoundException("유저 없음", HttpStatus.NOT_FOUND));
    }
}
