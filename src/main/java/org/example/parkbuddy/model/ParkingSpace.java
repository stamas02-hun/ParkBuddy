package org.example.parkbuddy.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "parking_spaces")
@Getter
@Setter
public class ParkingSpace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "Missing value for parking space name")
    @NotEmpty(message = "Parking space name cannot be empty")
    private String name;
}
