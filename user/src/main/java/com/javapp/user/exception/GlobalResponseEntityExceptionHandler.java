package com.javapp.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class GlobalResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(UserNotFoundByIdException.class)
    public ResponseEntity<ErrorDetails> handleTokenNotFoundException(UserNotFoundByIdException exception, WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails(new Date(),exception.getStatus(),exception.getCode(),exception.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND );
    }
}
