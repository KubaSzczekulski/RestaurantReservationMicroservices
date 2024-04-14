package com.example.Reservation.service;

import com.example.Reservation.entity.Reservation;
import com.example.Reservation.entity.ReservationEmailInfo;
import com.example.Reservation.exception.ReservationError;
import com.example.Reservation.exception.ReservationException;
import com.example.Reservation.repository.ReservationRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    private final WebClient webClient;
    private final WebClient webClient2;
    private final RabbitTemplate rabbitTemplate;


    @Autowired
    public ReservationService(ReservationRepository reservationRepository, WebClient webClient, WebClient webClient2,
                              RabbitTemplate rabbitTemplate) {
        this.reservationRepository = reservationRepository;
        this.webClient = webClient;
        this.webClient2 = webClient2;
        this.rabbitTemplate = rabbitTemplate;
    }


    public boolean isTableAvailable(Long tableId, LocalDateTime reservationDateTime) {
        Boolean tableExists = webClient.get()
                .uri("/table/check/{tableId}", tableId)
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();

        if (Boolean.TRUE.equals(tableExists)) {
            LocalDateTime startTime = reservationDateTime.minusHours(1);
            LocalDateTime endTime = reservationDateTime.plusHours(1);
            List<Reservation> overlappingReservations = reservationRepository.findOverlappingReservations(tableId, startTime, endTime);
            return overlappingReservations.isEmpty();
        } else {
            return false;
        }
    }

    public boolean hasUserFilledAllData(String userId) {
        Boolean userExists = webClient2.get()
                .uri("/user/checkFields")
                .header("userId", userId)
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();
        return Boolean.TRUE.equals(userExists);
    }

    public Reservation getReservation(Long id) {
        return reservationRepository.findById(id).orElseThrow(() -> new ReservationException(ReservationError.RESERVATION_NOT_FOUND));
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    @Transactional
    public void deleteReservation(Long id) {
        reservationRepository.findById(id).orElseThrow(
                () -> new ReservationException(ReservationError.RESERVATION_NOT_FOUND)
        );
        reservationRepository.deleteById(id);
    }

    @Transactional
    public Reservation editReservation(Long id, Reservation reservation, String userId) {
        return reservationRepository.findById(id)
                .map(reservationFromDb -> {
                    if (!isTableAvailable(reservation.getTableId(), reservation.getReservationDateTime())) {
                        throw new ReservationException(ReservationError.TABLE_IS_RESERVED);
                    }
                    if (!hasUserFilledAllData(userId)) {
                        throw new ReservationException(ReservationError.USER_HAS_NOT_ALL_DATA_FILLED);
                    }
                    reservationFromDb.setReservationDateTime(reservation.getReservationDateTime());
                    reservationFromDb.setAdditionalInfo(reservation.getAdditionalInfo());
                    reservationFromDb.setTableId(reservation.getTableId());
                    createAndConfirmReservation(reservationFromDb, userId);
                    return reservationRepository.save(reservationFromDb);
                }).orElseThrow(() -> new ReservationException(ReservationError.RESERVATION_NOT_FOUND));
    }

    @Transactional
    public Reservation patchReservation(Long reservationId, Reservation reservation, String userId) {
        System.out.println(reservationRepository.findById(reservationId));
        return reservationRepository.findById(reservationId)
                .map(reservationFromDb -> {
                    if (!isTableAvailable(reservation.getTableId(), reservation.getReservationDateTime())) {
                        throw new ReservationException(ReservationError.TABLE_IS_RESERVED);
                    }

                    if (!hasUserFilledAllData(userId)) {
                        throw new ReservationException(ReservationError.USER_HAS_NOT_ALL_DATA_FILLED);
                    }

                    if (reservation.getReservationDateTime() != null) {
                        reservationFromDb.setReservationDateTime(reservation.getReservationDateTime());
                    }

                    if (reservation.getAdditionalInfo() != null) {
                        reservationFromDb.setAdditionalInfo(reservation.getAdditionalInfo());
                    }

                    if (reservation.getTableId() != null) {
                        reservationFromDb.setTableId(reservation.getTableId());
                    }

                    createAndConfirmReservation(reservationFromDb, userId);

                    return reservationRepository.save(reservationFromDb);
                }).orElseThrow(() -> new ReservationException(ReservationError.RESERVATION_NOT_FOUND));
    }

    public Reservation addReservation(String userId, Reservation reservation) {
        reservation.setUserId(userId);

        if (!isTableAvailable(reservation.getTableId(), reservation.getReservationDateTime())) {
            throw new ReservationException(ReservationError.TABLE_IS_RESERVED);
        }
        if (!hasUserFilledAllData(userId)) {
            throw new ReservationException(ReservationError.USER_HAS_NOT_ALL_DATA_FILLED);
        }
        createAndConfirmReservation(reservation, userId);
        return reservationRepository.save(reservation);
    }

    public void createAndConfirmReservation(Reservation reservation, String userId) {
        Map userMap = webClient2.get()
                .uri("/user")
                .header("userId", userId)
                .retrieve()
                .bodyToMono(Map.class)
                .block();
        String userEmail;
        if (userMap != null) {
            userEmail = (String) userMap.get("email");
        } else {
            throw new ReservationException(ReservationError.USER_HAS_NOT_ALL_DATA_FILLED);
        }
        sendReservationConfirmation(reservation, userEmail);
    }

    private void sendReservationConfirmation(Reservation reservation, String userEmail) {
        ReservationEmailInfo reservationEmailInfo = new ReservationEmailInfo(reservation, userEmail);
        Jackson2JsonMessageConverter messageConverter = new Jackson2JsonMessageConverter();
        messageConverter.setCreateMessageIds(true);
        rabbitTemplate.setMessageConverter(messageConverter);
        rabbitTemplate.convertAndSend("reservation", reservationEmailInfo);
    }


}
