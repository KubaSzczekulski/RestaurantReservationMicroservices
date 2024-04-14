package com.example.demo.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorInfo {
    private LocalDateTime timestamp;
    private String message;

    public ErrorInfo(String message) {
        this.message=message;

    }

}