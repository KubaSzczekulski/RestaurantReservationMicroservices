package com.example.Reservation.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationEmailInfo implements Serializable {
    private Reservation reservation;
    private String userEmail;

}

