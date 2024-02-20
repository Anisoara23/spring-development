package com.example.business;

import java.util.Date;
import java.util.List;

public interface ReservationService {

    List<RoomReservation> getRoomReservationsForDate(Date date);
}
