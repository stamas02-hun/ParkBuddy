package org.example.parkbuddy.service;

import lombok.RequiredArgsConstructor;
import org.example.parkbuddy.dto.CreateReservationDTO;
import org.example.parkbuddy.exception.ParkingSpaceNotFoundException;
import org.example.parkbuddy.exception.ReservationConflictException;
import org.example.parkbuddy.model.Reservation;
import org.example.parkbuddy.repository.ParkingSpaceRepository;
import org.example.parkbuddy.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final ParkingSpaceRepository parkingSpaceRepository;

    @Override
    public Reservation createReservation(CreateReservationDTO dto) {
        if (!isReservable(dto.parkingSpaceId, dto.startTime, dto.endTime)) {
            throw new ReservationConflictException("Cannot create reservation because of a reservation conflict");
        }

        Reservation reservation = new Reservation();
        reservation.setParkingSpace(parkingSpaceRepository.findById(dto.parkingSpaceId).orElseThrow(() -> new ParkingSpaceNotFoundException(String.format("Cannot find parking spot with ID #%d", dto.parkingSpaceId))));
        reservation.setCustomerName(dto.customerName);
        reservation.setCustomerNumberPlate(dto.customerNumberPlate);
        reservation.setStartsAt(dto.startTime);
        reservation.setEndsAt(dto.endTime);

        return reservationRepository.save(reservation);
    }

    @Override
    public List<Reservation> findAllReservations() {
        return reservationRepository.findAll();
    }

    @Override
    public List<Reservation> findAllReservationsByParkingSpace(long parkingSpaceId) {
        return reservationRepository.findAllByParkingSpace_Id(parkingSpaceId);
    }

    @Override
    public void cancelReservation(UUID id) {
        
    }

    private boolean isReservable(long parkingSpaceId, LocalDateTime start, LocalDateTime end) {
        return !reservationRepository.existsByParkingSpace_IdAndStartsAtLessThanAndEndsAtGreaterThan(parkingSpaceId, end, start);
    }
}
