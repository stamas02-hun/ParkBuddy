package org.example.parkbuddy.controller;

import lombok.RequiredArgsConstructor;
import org.example.parkbuddy.model.ParkingSpace;
import org.example.parkbuddy.service.ParkingSpaceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parking")
@RequiredArgsConstructor
public class ParkingSpaceController {

    private final ParkingSpaceService parkingSpaceService;

    @GetMapping()
    public ResponseEntity<List<ParkingSpace>> findAllParkingSpaces() {
        return ResponseEntity.ok(parkingSpaceService.getParkingSpaces());
    }

    @GetMapping("/get")
    public ResponseEntity<ParkingSpace> findParkingSpaceById(@RequestParam long id) {
        ParkingSpace space = parkingSpaceService.getParkingSpaceById(id).orElse(null);

        if (space == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(space);
        }
    }
}
