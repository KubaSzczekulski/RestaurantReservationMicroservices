package com.example.Reservation.controllers;

import com.example.Reservation.entity.Reservation;
import com.example.Reservation.service.ReservationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalTime;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ReservationController.class)
public class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservationService reservationService;

//    @Test
//    void getReservation_returnsReservationDetails() throws Exception {
//        // Given
//        Long reservationId = 1L;
//        Reservation mockReservation = new Reservation(reservationId, LocalTime.now(), LocalTime.now(), "Info", 1L, 1L, 1L);
//
//        when(reservationService.getReservation(reservationId)).thenReturn(mockReservation);
//
//        // When & Then
//        mockMvc.perform(get("/reservation/{id}", reservationId))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.reservationId").value(reservationId));
//    }
}
