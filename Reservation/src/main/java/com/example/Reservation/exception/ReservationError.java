package com.example.Reservation.exception;

import lombok.Getter;

@Getter
public enum ReservationError {
    RESERVATION_NOT_FOUND("Reservation not found"),
    INVALID_RESERVATION_DATE("Invalid reservation date"),
    RESERVATION_ALREADY_EXISTS("Reservation already exists"),
    USER_NOT_FOUND("User not found"),
    INVALID_RESERVATION_DETAILS("Invalid reservation details"),
    SEAT_UNAVAILABLE("Seat is unavailable"),
    USER_HAS_NOT_ALL_DATA_FILLED("User has not filled all data"),
    TABLE_IS_RESERVED("Table is reserved");
    private final String errorMessage;

    ReservationError(String errorMessage) {
        this.errorMessage = errorMessage;
    }


}
