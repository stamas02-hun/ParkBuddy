package org.example.parkbuddy.dto;

import java.time.LocalDateTime;

public class CreateReservationDTO {
    public long parkingSpaceId;
    public String customerName;
    public String customerNumberPlate;
    public LocalDateTime startTime;
    public LocalDateTime endTime;
}
