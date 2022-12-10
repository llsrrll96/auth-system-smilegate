package com.javapp.user.controller;

import com.javapp.user.dto.user.SignUpDto;
import com.javapp.user.dto.user.UserDto;
import com.javapp.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    private ResponseEntity<UserDto> signUp(@RequestBody SignUpDto signUpDto){

        UserDto userDto = userService.createUser(signUpDto);
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }
}
