package com.example.business;

import com.example.entity.Guest;

import java.util.Date;
import java.util.List;

public interface ReservationService {

    List<RoomReservation> getRoomReservationsForDate(Date date);

    List<Guest> getAllGuests();
}
