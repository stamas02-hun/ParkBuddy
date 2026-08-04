package org.example.parkbuddy.service;

import org.example.parkbuddy.dto.CreateParkingSpaceDTO;
import org.example.parkbuddy.dto.ModifyParkingSpaceDTO;
import org.example.parkbuddy.model.ParkingSpace;

import java.util.List;

public interface ParkingSpaceService {

    List<ParkingSpace> getParkingSpaces();

    ParkingSpace getParkingSpaceById(long id);

    ParkingSpace createParkingSpace(CreateParkingSpaceDTO dto);

    ParkingSpace modifyParkingSpace(ModifyParkingSpaceDTO dto);

    void deleteParkingSpace(long id);
}
