package com.example.restaurant.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TableExceptionHandler {

    @ExceptionHandler(TableException.class)
    public ResponseEntity<ErrorInfo> handleTableException(TableException ex) {
        HttpStatus status = determineHttpStatus(ex.getTableError());
        ErrorInfo errorInfo = new ErrorInfo(ex.getMessage());

        return new ResponseEntity<>(errorInfo, status);
    }

    private HttpStatus determineHttpStatus(TableError error) {
        return switch (error) {
            case TABLE_NOT_FOUND -> HttpStatus.NOT_FOUND;
            case INVALID_TABLE_DATA -> HttpStatus.BAD_REQUEST;
            case TABLE_CANNOT_BE_DELETED -> HttpStatus.FORBIDDEN;
            case UNAUTHORIZED_ACCESS -> HttpStatus.UNAUTHORIZED;
            case INTERNAL_ERROR -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
    }
}
