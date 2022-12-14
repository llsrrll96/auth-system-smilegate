package com.javapp.auth.controller;


import com.javapp.auth.domain.User;
import com.javapp.auth.dto.JwtRequestDto;
import com.javapp.auth.dto.SignUpDto;
import com.javapp.auth.dto.UserDto;
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

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signUp(@RequestBody SignUpDto signUpDto){

        UserDto userDto = authService.createUser(signUpDto);
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthResponse> signIn(@RequestBody JwtRequestDto jwtRequestDto){
        System.out.println(jwtRequestDto.getEmail());
        return new ResponseEntity<>(new JwtAuthResponse(authService.getJwtToken(jwtRequestDto)), HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public User findById(@PathVariable Long userId){
        System.out.println("8100 findById," + userId);
        return authService.findById(userId);
    }


}
