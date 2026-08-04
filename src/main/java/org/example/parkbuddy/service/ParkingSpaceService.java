package org.example.parkbuddy.service;

import org.example.parkbuddy.dto.CreateParkingSpaceDTO;
import org.example.parkbuddy.dto.ModifyParkingSpaceDTO;
import org.example.parkbuddy.model.ParkingSpace;

import java.util.List;
import java.util.Optional;

public interface ParkingSpaceService {

    List<ParkingSpace> getParkingSpaces();

    Optional<ParkingSpace> getParkingSpaceById(long id);

    ParkingSpace createParkingSpace(CreateParkingSpaceDTO dto);

    ParkingSpace modifyParkingSpace(ModifyParkingSpaceDTO dto);

    void deleteParkingSpace(long id);
}
