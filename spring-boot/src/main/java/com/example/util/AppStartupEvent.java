package com.example.util;

import com.example.entity.Guest;
import com.example.entity.Reservation;
import com.example.entity.Room;
import com.example.repository.GuestRepository;
import com.example.repository.ReservationRepository;
import com.example.repository.RoomRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Month;

@Component
public class AppStartupEvent implements ApplicationListener<ApplicationReadyEvent> {

    private final RoomRepository roomRepository;

    private final GuestRepository guestRepository;

    private final ReservationRepository reservationRepository;

    public AppStartupEvent(RoomRepository roomRepository, GuestRepository guestRepository, ReservationRepository reservationRepository) {
        this.roomRepository = roomRepository;
        this.guestRepository = guestRepository;
        this.reservationRepository = reservationRepository;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Iterable<Room> rooms = roomRepository.findAll();
        rooms.forEach(System.out::println);

        Iterable<Guest> guests = guestRepository.findAll();
        guests.forEach(System.out::println);

        Iterable<Reservation> reservations =
                reservationRepository.findReservationsByResDate(LocalDate.of(2022, Month.JANUARY, 1));

        reservations.forEach(System.out::println);
    }
}
