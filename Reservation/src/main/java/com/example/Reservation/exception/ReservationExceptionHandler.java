package com.example.Reservation.exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ReservationExceptionHandler {

    @ExceptionHandler(ReservationException.class)
    public ResponseEntity<ErrorInfo> handleUserException(ReservationException ex) {
        HttpStatus status = determineHttpStatus(ex.getReservationError());
        ErrorInfo errorInfo = new ErrorInfo(ex.getMessage());
        return new ResponseEntity<>(errorInfo, status);
    }

    private HttpStatus determineHttpStatus(ReservationError error) {
        return switch (error) {
            case RESERVATION_NOT_FOUND, USER_NOT_FOUND -> HttpStatus.NOT_FOUND;
            case INVALID_RESERVATION_DATE, INVALID_RESERVATION_DETAILS, SEAT_UNAVAILABLE, USER_HAS_NOT_ALL_DATA_FILLED -> HttpStatus.BAD_REQUEST;
            case RESERVATION_ALREADY_EXISTS, TABLE_IS_RESERVED -> HttpStatus.CONFLICT;
        };
    }

}
