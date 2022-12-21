package com.javapp.auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class GlobalResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(TokenException.class)
    public ResponseEntity<ErrorDetails> handleTokenNotFoundException(TokenException exception, WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails(new Date(),exception.getStatus(),exception.getCode(),exception.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND );
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleTokenNotFoundException(UserNotFoundException exception, WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails(new Date(),exception.getStatus(),exception.getCode(),exception.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND );
    }
}
