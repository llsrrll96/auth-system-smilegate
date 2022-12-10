package com.javapp.auth.controller;


import com.javapp.auth.domain.User;
import com.javapp.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @GetMapping("/user/{userId}")
    public User findById(@PathVariable Long userId){
        return authService.findById(userId);
    }
}
