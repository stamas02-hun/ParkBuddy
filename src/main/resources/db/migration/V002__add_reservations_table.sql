CREATE TABLE reservations
(
    id UUID NOT NULL,
    parking_space_id BIGINT NOT NULL,
    customer_name VARCHAR(255) NOT NULL,
    customer_number_plate VARCHAR(255) NOT NULL,
    created_at TIMESTAMP(6) WITHOUT TIME ZONE NOT NULL,
    starts_at TIMESTAMP(6) WITHOUT TIME ZONE NOT NULL,
    ends_at TIMESTAMP(6) WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_reservations PRIMARY KEY (id)
);

CREATE INDEX idx_reservation_time ON reservations (starts_at, ends_at);

ALTER TABLE reservations
    ADD CONSTRAINT FK_RESERVATIONS_ON_PARKING_SPACE FOREIGN KEY (parking_space_id) REFERENCES parking_spaces (id);

CREATE INDEX idx_reservation_parking_space ON reservations (parking_space_id);