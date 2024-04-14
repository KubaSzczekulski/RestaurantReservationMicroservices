package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorInfo> handleUserException(UserException ex) {
        HttpStatus status = determineHttpStatus(ex.getUserError());
        ErrorInfo errorInfo = new ErrorInfo(ex.getMessage());
        return new ResponseEntity<>(errorInfo, status);
    }

    private HttpStatus determineHttpStatus(UserError error) {
        return switch (error) {
            case USER_NOT_FOUND -> HttpStatus.NOT_FOUND;
            case EMAIL_ALREADY_EXISTS, TELEPHONE_ALREADY_EXISTS -> HttpStatus.CONFLICT;
            case INVALID_USER_DATA -> HttpStatus.BAD_REQUEST;
            case USER_CANNOT_BE_DELETED -> HttpStatus.FORBIDDEN;
            case UNAUTHORIZED_ACCESS -> HttpStatus.UNAUTHORIZED;
            default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
    }
}
