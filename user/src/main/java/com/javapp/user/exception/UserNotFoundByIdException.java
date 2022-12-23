package com.javapp.user.exception;

import lombok.Getter;

@Getter
public class UserNotFoundByIdException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final String message;
    private final String code;
    private final int status;

    public UserNotFoundByIdException(ErrorCode errorCode){
        this.message = errorCode.getMessage();
        this.status = errorCode.getStatus();
        this.code = errorCode.getCode();
    }
}
