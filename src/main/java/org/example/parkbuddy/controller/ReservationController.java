package org.example.parkbuddy.controller;

import lombok.RequiredArgsConstructor;
import org.example.parkbuddy.dto.CreateReservationDTO;
import org.example.parkbuddy.model.Reservation;
import org.example.parkbuddy.service.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping
    public ResponseEntity<List<Reservation>> getAllReservations() {
        return ResponseEntity.ok(reservationService.findAllReservations());
    }

    @GetMapping("/space")
    public ResponseEntity<List<Reservation>> getAllReservationsBySpace(@RequestParam long id) {
        return ResponseEntity.ok(reservationService.findAllReservationsByParkingSpace(id));
    }

    @PostMapping("/new")
    public ResponseEntity<Reservation> createReservation(@RequestBody CreateReservationDTO dto) {
        return ResponseEntity.ok(reservationService.createReservation(dto));
    }

    @PatchMapping("/cancel")
    public ResponseEntity<Reservation> cancelReservation(@RequestParam UUID id) {
        return ResponseEntity.ok(reservationService.cancelReservation(id));
    }
}
