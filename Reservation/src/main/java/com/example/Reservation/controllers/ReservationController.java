package com.example.Reservation.controllers;

import com.example.Reservation.entity.Reservation;
import com.example.Reservation.service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservation")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservation(@PathVariable Long id) {
        Reservation reservation = reservationService.getReservation(id);
        return ResponseEntity.ok(reservation);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@RequestHeader("userId") String userId, @PathVariable Long id) {
        reservationService.deleteReservation(id, userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<Reservation>> getAllReservations() {
        List<Reservation> reservations = reservationService.getAllReservations();
        return ResponseEntity.ok(reservations);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reservation> putReservation(@RequestHeader("userId") String userId, @PathVariable Long id,
                                                      @Valid @RequestBody Reservation reservation) {
        Reservation updatedReservation = reservationService.editReservation(id, reservation, userId);
        return ResponseEntity.ok(updatedReservation);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Reservation> patchReservation(@RequestHeader("userId") String userId, @PathVariable Long id,
                                                        @Valid @RequestBody Reservation reservation) {
        Reservation patchedReservation = reservationService.patchReservation(id, reservation, userId);
        return ResponseEntity.ok(patchedReservation);
    }

    @PostMapping
    public ResponseEntity<Reservation> addReservation2(@RequestHeader("userId") String userId,
                                                       @Valid @RequestBody Reservation reservation) {
        Reservation addedReservation = reservationService.addReservation(userId, reservation);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedReservation);
    }


}