package org.example.parkbuddy.service;

import org.example.parkbuddy.model.ParkingSpace;

import java.util.List;
import java.util.Optional;

public interface ParkingSpaceService {

    List<ParkingSpace> getParkingSpaces();

    Optional<ParkingSpace> getParkingSpaceById(long id);

    ParkingSpace createParkingSpace(ParkingSpace parkingSpace);

    ParkingSpace modifyParkingSpace(ParkingSpace parkingSpace);

    void deleteParkingSpace(long id);
}
