package org.example.parkbuddy.service;

import org.example.parkbuddy.model.Reservation;

import java.util.List;
import java.util.UUID;

public interface ReservationService {

    Reservation createReservation();

    List<Reservation> findAllReservations();

    List<Reservation> findAllReservationsByParkingSpace();

    void cancelReservation(UUID id);
}
