package org.example.parkbuddy.controller;

import lombok.RequiredArgsConstructor;
import org.example.parkbuddy.dto.CreateParkingSpaceDTO;
import org.example.parkbuddy.dto.ModifyParkingSpaceDTO;
import org.example.parkbuddy.model.ParkingSpace;
import org.example.parkbuddy.service.ParkingSpaceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parking")
@RequiredArgsConstructor
public class ParkingSpaceController {

    private final ParkingSpaceService parkingSpaceService;

    @GetMapping
    public ResponseEntity<List<ParkingSpace>> findAllParkingSpaces() {
        return ResponseEntity.ok(parkingSpaceService.getParkingSpaces());
    }

    @GetMapping("/get")
    public ResponseEntity<ParkingSpace> findParkingSpaceById(@RequestParam long id) {
        ParkingSpace space = parkingSpaceService.getParkingSpaceById(id);
        return ResponseEntity.ok(space);
    }

    @PostMapping("/new")
    public ResponseEntity<ParkingSpace> createParkingSpace(@RequestBody CreateParkingSpaceDTO createParkingSpaceDTO) {
        return new ResponseEntity<>(parkingSpaceService.createParkingSpace(createParkingSpaceDTO), HttpStatus.CREATED);
    }

    @PatchMapping("/modify")
    public ResponseEntity<ParkingSpace> modifyParkingSpace(@RequestBody ModifyParkingSpaceDTO dto) {
        return ResponseEntity.ok(parkingSpaceService.modifyParkingSpace(dto));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteParkingSpace(@RequestParam long id) {
        parkingSpaceService.deleteParkingSpace(id);
        return new ResponseEntity<>(String.format("Parking space (#%d) deleted", id), HttpStatus.OK);
    }
}
