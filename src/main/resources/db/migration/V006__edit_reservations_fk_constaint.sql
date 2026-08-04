ALTER TABLE reservations
DROP CONSTRAINT fk_reservations_on_parking_space;

ALTER TABLE reservations
ADD CONSTRAINT fk_reservations_on_parking_space
FOREIGN KEY (parking_space_id)
REFERENCES parking_spaces (id)
ON DELETE CASCADE;