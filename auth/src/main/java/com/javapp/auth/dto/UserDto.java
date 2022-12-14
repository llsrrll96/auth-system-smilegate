package com.javapp.auth.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.javapp.auth.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class UserDto {
    private Long userId;

    private String username;

    private String email;

    private String role;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime createDateTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime modifiedDateTime;

    public UserDto(User user) {
        this.userId = user.getUserId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.role = user.getRole();
        this.createDateTime = user.getCreateDateTime();
        this.modifiedDateTime = user.getModifiedDateTime();
    }
}
