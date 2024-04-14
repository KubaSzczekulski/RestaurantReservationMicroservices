package com.example.restaurant.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestaurantExceptionHandler {

    @ExceptionHandler(RestaurantException.class)
    public ResponseEntity<ErrorInfo> handleRestaurantException(RestaurantException ex) {
        HttpStatus status = determineHttpStatus(ex.getRestaurantError());
        ErrorInfo errorInfo = new ErrorInfo(ex.getMessage());

        return new ResponseEntity<>(errorInfo, status);
    }



    private HttpStatus determineHttpStatus(RestaurantError error) {
        return switch (error) {
            case RESTAURANT_NOT_FOUND -> HttpStatus.NOT_FOUND;
            case EMAIL_ALREADY_EXISTS, TELEPHONE_ALREADY_EXISTS -> HttpStatus.CONFLICT;
            case INVALID_RESTAURANT_DATA -> HttpStatus.BAD_REQUEST;
            case RESTAURANT_CANNOT_BE_DELETED -> HttpStatus.FORBIDDEN;
            case UNAUTHORIZED_ACCESS -> HttpStatus.UNAUTHORIZED;
            case INTERNAL_ERROR -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
    }
}
