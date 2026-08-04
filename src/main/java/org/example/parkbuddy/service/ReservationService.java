package org.example.parkbuddy.service;

import org.example.parkbuddy.dto.CreateReservationDTO;
import org.example.parkbuddy.model.Reservation;

import java.util.List;
import java.util.UUID;

public interface ReservationService {

    Reservation createReservation(CreateReservationDTO dto);

    List<Reservation> findAllReservations();

    List<Reservation> findAllReservationsByParkingSpace(long parkingSpaceId);

    void cancelReservation(UUID id);
}
