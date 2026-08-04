package org.example.parkbuddy.repository;

import org.example.parkbuddy.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, UUID> {
    boolean existsByParkingSpace_IdAndStartsAtLessThanAndEndsAtGreaterThan(long id, LocalDateTime endsAt, LocalDateTime startsAt);
}
