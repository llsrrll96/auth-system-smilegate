package com.javapp.user.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@AllArgsConstructor
@Getter
public class ErrorDetails {
    private Date timestamp;
    private int status;
    private String code;
    private String message;
}
