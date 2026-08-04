package org.example.parkbuddy.service;

import lombok.RequiredArgsConstructor;
import org.example.parkbuddy.dto.CreateParkingSpaceDTO;
import org.example.parkbuddy.dto.ModifyParkingSpaceDTO;
import org.example.parkbuddy.exception.ParkingSpaceNotFoundException;
import org.example.parkbuddy.model.ParkingSpace;
import org.example.parkbuddy.repository.ParkingSpaceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParkingSpaceServiceImpl implements ParkingSpaceService {

    private final ParkingSpaceRepository parkingSpaceRepository;

    @Override
    public List<ParkingSpace> getParkingSpaces() {
        return parkingSpaceRepository.findAll();
    }

    @Override
    public ParkingSpace getParkingSpaceById(long id) {
        return parkingSpaceRepository.findById(id).orElseThrow(() -> new ParkingSpaceNotFoundException(String.format("Parking space (#%d) not found", id)));
    }

    @Override
    public ParkingSpace createParkingSpace(CreateParkingSpaceDTO dto) {
        ParkingSpace parkingSpace = new ParkingSpace();
        parkingSpace.setName(dto.name);
        return parkingSpaceRepository.save(parkingSpace);
    }

    @Override
    public ParkingSpace modifyParkingSpace(ModifyParkingSpaceDTO dto) {
        ParkingSpace parkingSpace = parkingSpaceRepository.findById(dto.id).orElseThrow(() -> new ParkingSpaceNotFoundException(String.format("Parking space (#%d) not found", dto.id)));

        parkingSpace.setName(dto.name);

        return parkingSpaceRepository.save(parkingSpace);
    }

    @Override
    public void deleteParkingSpace(long id) {
        parkingSpaceRepository.deleteById(id);
    }
}
