package com.example.demo.exception;

import lombok.Getter;

@Getter
public enum UserError {
    USER_NOT_FOUND("User not found."),
    EMAIL_ALREADY_EXISTS("Email already exists."),
    TELEPHONE_ALREADY_EXISTS("Telephone number already exists."),
    INVALID_USER_DATA("Invalid restaurant data."),
    USER_CANNOT_BE_DELETED("User cannot be deleted."),
    UNAUTHORIZED_ACCESS("Unauthorized access to restaurant."),
    INTERNAL_ERROR("Internal server error occurred."),

    USER_ALREADY_EXISTS("User already exists");
    private final String errorMessage;

    UserError(String errorMessage) {
        this.errorMessage = errorMessage;
    }



}