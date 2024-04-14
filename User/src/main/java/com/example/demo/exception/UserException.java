package com.example.demo.exception;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class UserException extends RuntimeException {

    private final LocalDateTime timestamp;
    private final String message;
    private final UserError userError;

    public UserException(UserError userError) {
        super(userError.getErrorMessage());
        this.timestamp = LocalDateTime.now();
        this.message = userError.getErrorMessage();
        this.userError = userError;
    }
}
