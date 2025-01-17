package com.example.util;

import com.example.business.ReservationService;
import com.example.business.RoomReservation;
import com.example.entity.Guest;
import com.example.entity.Room;
import com.example.repository.GuestRepository;
import com.example.repository.ReservationRepository;
import com.example.repository.RoomRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class AppStartupEvent implements ApplicationListener<ApplicationReadyEvent> {

    private final RoomRepository roomRepository;

    private final GuestRepository guestRepository;

    private final ReservationRepository reservationRepository;

    private final ReservationService reservationService;

    private final DateUtils dateUtils;

    public AppStartupEvent(RoomRepository roomRepository, GuestRepository guestRepository, ReservationRepository reservationRepository, ReservationService reservationService, DateUtils dateUtils) {
        this.roomRepository = roomRepository;
        this.guestRepository = guestRepository;
        this.reservationRepository = reservationRepository;
        this.reservationService = reservationService;
        this.dateUtils = dateUtils;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Iterable<Room> rooms = roomRepository.findAll();
        rooms.forEach(System.out::println);

        Iterable<Guest> guests = guestRepository.findAll();
        guests.forEach(System.out::println);

        Date date = dateUtils.createDateFromDateString("2022-01-01");

        List<RoomReservation> roomReservationsForDate =
                reservationService.getRoomReservationsForDate(date);
        roomReservationsForDate.forEach(System.out::println);
    }
}
