package com.example.restaurant.exception;

public enum TableError {
    TABLE_NOT_FOUND("Table not found."),
    INVALID_TABLE_DATA("Invalid table data."),
    TABLE_CANNOT_BE_DELETED("Table cannot be deleted due to existing reservations."),
    UNAUTHORIZED_ACCESS("Unauthorized access to table information."),
    INTERNAL_ERROR("Internal server error occurred while processing table request.");

    private final String errorMessage;

    TableError(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
