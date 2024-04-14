package com.example.Reservation.repository;

import com.example.Reservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @Query("SELECT r FROM Reservation r WHERE r.tableId = :tableId " +
            "AND (r.reservationDateTime BETWEEN :startTime AND :endTime)")
    List<Reservation> findOverlappingReservations(@Param("tableId") Long tableId,
                                                  @Param("startTime") LocalDateTime startTime,
                                                  @Param("endTime") LocalDateTime endTime);

}
