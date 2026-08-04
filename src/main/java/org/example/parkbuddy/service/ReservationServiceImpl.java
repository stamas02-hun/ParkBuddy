package org.example.parkbuddy.service;

import lombok.RequiredArgsConstructor;
import org.example.parkbuddy.dto.CreateReservationDTO;
import org.example.parkbuddy.exception.ParkingSpaceNotFoundException;
import org.example.parkbuddy.exception.ReservationConflictException;
import org.example.parkbuddy.exception.ReservationNotFoundException;
import org.example.parkbuddy.model.EReservationStatus;
import org.example.parkbuddy.model.Reservation;
import org.example.parkbuddy.repository.ParkingSpaceRepository;
import org.example.parkbuddy.repository.ReservationRepository;
import org.springframework.scheduling.annotation.Scheduled;
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
    public Reservation cancelReservation(UUID id) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> new ReservationNotFoundException(String.format("Cannot find reservation with ID %s", id)));

        if (reservation.getEndsAt().isAfter(LocalDateTime.now())) {
            throw new ReservationConflictException("Cannot cancel reservation because it already ended");
        }

        if (reservation.getStatus() == EReservationStatus.COMPLETED) {
            throw new ReservationConflictException("Cannot cancel reservation because reservation has already been completed");
        }

        if (reservation.getStatus() == EReservationStatus.CANCELLED) {
            throw new ReservationConflictException("Cannot cancel reservation because reservation has already been cancelled");
        }

        reservation.setStatus(EReservationStatus.CANCELLED);
        return reservationRepository.save(reservation);
    }

    @Override
    @Scheduled(fixedDelay = 60000)
    public void validateReservationState() {
        List<Reservation> reservationsToCheck = reservationRepository.findAllByStatusIn(List.of(EReservationStatus.ACTIVE, EReservationStatus.WAITING));

        for (Reservation reservation : reservationsToCheck) {
            if (reservation.getStatus().equals(EReservationStatus.ACTIVE) && reservation.getEndsAt().isBefore(LocalDateTime.now())) {
                reservation.setStatus(EReservationStatus.COMPLETED);
            } else if (reservation.getStatus().equals(EReservationStatus.WAITING) && reservation.getStartsAt().isAfter(LocalDateTime.now())) {
                reservation.setStatus(EReservationStatus.ACTIVE);
            }
        }

        reservationRepository.saveAll(reservationsToCheck);
    }

    private boolean isReservable(long parkingSpaceId, LocalDateTime start, LocalDateTime end) {
        return !reservationRepository.existsByParkingSpace_IdAndStartsAtLessThanAndEndsAtGreaterThan(parkingSpaceId, end, start);
    }
}
