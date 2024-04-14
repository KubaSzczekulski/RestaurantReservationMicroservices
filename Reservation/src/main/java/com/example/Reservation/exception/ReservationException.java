package com.example.Reservation.exception;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReservationException extends RuntimeException {

    private final LocalDateTime localDateTime;
    private final String message;
    private final ReservationError reservationError;

    public ReservationException(ReservationError reservationError){
        super(reservationError.getErrorMessage());
        this.localDateTime = LocalDateTime.now();
        this.message = reservationError.getErrorMessage();
        this.reservationError = reservationError;
    }


}
