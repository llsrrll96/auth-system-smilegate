package com.javapp.user.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SignUpDto {
    private String username;
    private String email;
    private String password;
}
