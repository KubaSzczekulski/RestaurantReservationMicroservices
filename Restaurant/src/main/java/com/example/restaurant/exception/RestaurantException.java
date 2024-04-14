package com.example.restaurant.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;

@Getter
public class RestaurantException extends RuntimeException {

    private final LocalDateTime timestamp;
    private final String message;
    private final RestaurantError restaurantError;

    public RestaurantException(RestaurantError restaurantError) {
        super(restaurantError.getErrorMessage());
        this.timestamp = LocalDateTime.now();
        this.message = restaurantError.getErrorMessage();
        this.restaurantError = restaurantError;
    }
}
