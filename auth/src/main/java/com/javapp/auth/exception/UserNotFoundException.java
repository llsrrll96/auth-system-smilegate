package com.javapp.auth.exception;

import lombok.Getter;

@Getter
public class UserNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final String message;
    private final String code;
    private final int status;

    public UserNotFoundException(ErrorCode errorCode){
        this.message = errorCode.getMessage();
        this.status = errorCode.getStatus();
        this.code = errorCode.getCode();
    }
}
