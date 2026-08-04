ALTER TABLE reservations
    ADD updated_at TIMESTAMP(6) WITHOUT TIME ZONE;

UPDATE reservations
    SET updated_at = created_at;

ALTER TABLE reservations
    ALTER COLUMN updated_at SET NOT NULL;