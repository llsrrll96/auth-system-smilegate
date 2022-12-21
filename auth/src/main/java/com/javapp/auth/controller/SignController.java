package com.javapp.auth.controller;

import com.javapp.auth.dto.JwtRequestDto;
import com.javapp.auth.dto.SignUpDto;
import com.javapp.auth.dto.UserDto;
import com.javapp.auth.security.jwt.JwtAuthResponse;
import com.javapp.auth.service.SignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/sign")
public class SignController {

    @Autowired
    private SignService signService;

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signUp(@RequestBody SignUpDto signUpDto){
        UserDto userDto = signService.createUser(signUpDto);
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthResponse> signIn(@RequestBody JwtRequestDto jwtRequestDto){
        return new ResponseEntity<>(signService.siginIn(jwtRequestDto), HttpStatus.OK);
    }

//    @GetMapping("/user/{userId}")
//    public User findById(@PathVariable Long userId){
//        return signService.findById(userId);
//    }

    @GetMapping("/existby/{email}")
    public ResponseEntity<Boolean> existByEmail(@PathVariable String email){
        return new ResponseEntity<>(signService.existByEmail(email),HttpStatus.OK);
    }

}
