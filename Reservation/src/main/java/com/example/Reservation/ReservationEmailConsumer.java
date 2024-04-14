package com.example.Reservation;

import com.fasterxml.jackson.databind.ObjectMapper;


import com.example.Reservation.entity.ReservationEmailInfo;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ReservationEmailConsumer {

    private final JavaMailSender javaMailSender;
    private final ObjectMapper objectMapper;

    public ReservationEmailConsumer(JavaMailSender javaMailSender, ObjectMapper objectMapper) {
        this.javaMailSender = javaMailSender;
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = "reservation")
    public void receiveMessage(Message message) {
        byte[] body = message.getBody();
        try {
            String jsonMessage = new String(body);
            ReservationEmailInfo reservationEmailInfo = objectMapper.readValue(jsonMessage, ReservationEmailInfo.class);
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(reservationEmailInfo.getUserEmail());
            mailMessage.setSubject("Potwierdzenie rezerwacji");
            mailMessage.setText("Drogi Kliencie, Twoja rezerwacja w dniu " + reservationEmailInfo.getReservation().getReservationDateTime()+ " została przyjęta.");
            javaMailSender.send(mailMessage);
        } catch (IOException e) {
            System.out.println("Sending the email failed");
        }
    }
}

