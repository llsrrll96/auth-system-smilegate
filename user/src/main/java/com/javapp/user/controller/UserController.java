package com.javapp.user.controller;

import com.javapp.user.dto.user.RequestUserDto;
import com.javapp.user.dto.user.UserDto;
import com.javapp.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> findUserDetail(@PathVariable Long userId){
        return new ResponseEntity<>(userService.findUserDetail(userId), HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> findUsers(){
        return new ResponseEntity<>(userService.findByUsers(),HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<UserDto> updateUserDetail(@RequestHeader("X-Authorization-Id") String userId, @RequestBody RequestUserDto requestUserDto){
        requestUserDto.setUserId(Long.parseLong(userId));
        System.out.println("userId: "+userId);
        return new ResponseEntity<>(userService.updateUser(requestUserDto),HttpStatus.OK);
    }
}
