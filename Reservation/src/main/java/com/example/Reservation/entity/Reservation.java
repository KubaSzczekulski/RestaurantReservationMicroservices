package com.example.Reservation.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@Entity
@Table(name = "reservation_table")
public class Reservation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationId;

    @Column(name = "reservation_date")
    private LocalDateTime reservationDateTime;

    @Column(name = "additional_info")
    private String additionalInfo;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "table_id")
    private Long tableId;

    public Reservation() {
    }
}
