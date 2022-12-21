package com.javapp.auth.controller;

import com.javapp.auth.dto.RefreshJwtDto;
import com.javapp.auth.security.jwt.JwtAuthResponse;
import com.javapp.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    // 로그인 유지
    // RT 만료 확인 후 AT 재발급 else AT RT 재발급
    @PostMapping("/refresh")
    public ResponseEntity<JwtAuthResponse> refresh(@RequestBody RefreshJwtDto refreshJwtDto){
        JwtAuthResponse jwtAuthResponse = authService.refreshToken(refreshJwtDto);
        return new ResponseEntity<>(jwtAuthResponse, HttpStatus.OK);
    }

    // 로그인 유지 X
    // RT 만료 시키기(로그아웃)
    @DeleteMapping("/logout")
    public ResponseEntity<Boolean> logout(@RequestBody RefreshJwtDto refreshJwtDto){
        boolean isSuccess = authService.deleteToken(refreshJwtDto);
        return new ResponseEntity<>(isSuccess,HttpStatus.OK);
    }

}
