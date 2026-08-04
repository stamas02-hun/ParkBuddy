package org.example.parkbuddy.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "reservations",
        indexes = {
                @Index(name = "idx_reservation_parking_space", columnList = "parking_space_id"),
                @Index(name = "idx_reservation_time", columnList = "starts_at, ends_at")
        }
)
@Getter
@Setter
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parking_space_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ParkingSpace parkingSpace;

    @NotNull(message = "Missing value for reservation customer name")
    @NotEmpty(message = "Customer name cannot be empty")
    @Column(nullable = false)
    private String customerName;

    @NotNull(message = "Missing value for reservation customer number plate")
    @NotEmpty(message = "Customer number plate cannot be empty")
    @Column(nullable = false)
    private String customerNumberPlate;

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @NotNull(message = "Missing value for reservation start time")
    @Column(nullable = false)
    private LocalDateTime startsAt;

    @NotNull(message = "Missing value for reservation end time")
    @Column(nullable = false)
    private LocalDateTime endsAt;

    @Enumerated(EnumType.STRING)
    private EReservationStatus status = EReservationStatus.WAITING;

    @AssertTrue(message = "Incorrect reservation time: reservation end time must be after the start time")
    private boolean isValidTimeRange() {
        return startsAt != null && endsAt != null && startsAt.isBefore(endsAt);
    }
}
