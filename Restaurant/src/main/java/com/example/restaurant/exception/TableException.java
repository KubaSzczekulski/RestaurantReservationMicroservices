package com.example.restaurant.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;

@Getter
public class TableException extends RuntimeException {

    private final LocalDateTime timestamp;
    private final String message;
    private final TableError tableError;


    public TableException(TableError tableError) {
        super(tableError.getErrorMessage());
        this.timestamp = LocalDateTime.now();
        this.message = tableError.getErrorMessage();
        this.tableError = tableError;

    }
}
