package org.example.parkbuddy.service;

import lombok.RequiredArgsConstructor;
import org.example.parkbuddy.model.Reservation;
import org.example.parkbuddy.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;

    @Override
    public Reservation createReservation() {
        return null;
    }

    @Override
    public List<Reservation> findAllReservations() {
        return reservationRepository.findAll();
    }

    @Override
    public List<Reservation> findAllReservationsByParkingSpace() {
        return List.of();
    }

    @Override
    public void cancelReservation(UUID id) {

    }
}
