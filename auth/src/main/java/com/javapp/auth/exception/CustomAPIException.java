package com.javapp.auth.exception;

import org.springframework.http.HttpStatus;

public class CustomAPIException extends RuntimeException{
    private HttpStatus status;
    private String message;


    public CustomAPIException(String message, HttpStatus status, String message2) {
        super(message);
        this.status = status;
        this.message = message2;
    }

    public CustomAPIException(HttpStatus status, String message) {
        super();
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
