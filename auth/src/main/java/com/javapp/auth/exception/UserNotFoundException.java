package com.javapp.auth.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final String message;
    private final HttpStatus httpStatus;

    public UserNotFoundException(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public HttpStatus getStatus() {
        return httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
