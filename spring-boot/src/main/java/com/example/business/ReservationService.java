package com.example.business;

import com.example.entity.Guest;
import com.example.entity.Room;

import java.util.Date;
import java.util.List;

public interface ReservationService {

    List<RoomReservation> getRoomReservationsForDate(Date date);

    List<Guest> getAllGuests();

    List<Room> getAllRooms();

    void addGuest(Guest guest);
}
