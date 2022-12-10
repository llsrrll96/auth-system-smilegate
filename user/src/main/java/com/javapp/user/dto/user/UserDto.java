package com.javapp.user.dto.user;

import com.javapp.user.entity.user.Role;
import com.javapp.user.entity.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserDto {
    private Long userId;

    private String username;

    private String email;

    private Role role;

    public UserDto(User user) {
        this.userId = user.getUserId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.role = user.getRole();
    }
}
