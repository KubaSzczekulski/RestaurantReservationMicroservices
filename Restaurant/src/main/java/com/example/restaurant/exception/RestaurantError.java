package com.example.restaurant.exception;

import lombok.Getter;

@Getter
public enum RestaurantError {
    RESTAURANT_NOT_FOUND("Restaurant not found."),
    EMAIL_ALREADY_EXISTS("Email already exists."),
    TELEPHONE_ALREADY_EXISTS("Telephone number already exists."),
    INVALID_RESTAURANT_DATA("Invalid restaurant data."),
    RESTAURANT_CANNOT_BE_DELETED("Restaurant cannot be deleted."),
    UNAUTHORIZED_ACCESS("Unauthorized access to restaurant."),
    INTERNAL_ERROR("Internal server error occurred.");

    private final String errorMessage;

    RestaurantError(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}