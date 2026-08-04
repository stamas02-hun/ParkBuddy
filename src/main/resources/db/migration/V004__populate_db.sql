INSERT INTO parking_spaces (id, name)
SELECT spot_number,
       'Spot #' || spot_number
FROM generate_series(1, 50) AS spot_number;

WITH reservation_data AS (
    SELECT parking_space_id,
           reservation_number,
           (parking_space_id + reservation_number) % 3 AS time_category
    FROM generate_series(1, 50) AS parking_space_id
    CROSS JOIN LATERAL generate_series(
        1,
        1 + ((parking_space_id - 1) % 5)
    ) AS reservation_number
)
INSERT INTO reservations (
    id,
    parking_space_id,
    customer_name,
    customer_number_plate,
    created_at,
    starts_at,
    ends_at,
    status
)
SELECT md5(
           'park-buddy-reservation-'
               || parking_space_id
               || '-'
               || reservation_number
       )::UUID,
       parking_space_id,
       'Customer ' || parking_space_id || '-' || reservation_number,
       'PB-' || lpad(parking_space_id::TEXT, 2, '0') || '-' || reservation_number,
       LOCALTIMESTAMP - INTERVAL '30 days',
       CASE time_category
           WHEN 0 THEN LOCALTIMESTAMP - (reservation_number + 1) * INTERVAL '1 day'
           WHEN 1 THEN LOCALTIMESTAMP - INTERVAL '1 hour'
           ELSE LOCALTIMESTAMP + (reservation_number + 1) * INTERVAL '1 day'
       END,
       CASE time_category
           WHEN 0 THEN LOCALTIMESTAMP - (reservation_number + 1) * INTERVAL '1 day'
                       + INTERVAL '2 hours'
           WHEN 1 THEN LOCALTIMESTAMP + INTERVAL '1 hour'
           ELSE LOCALTIMESTAMP + (reservation_number + 1) * INTERVAL '1 day'
                       + INTERVAL '2 hours'
       END,
       CASE time_category
           WHEN 0 THEN 'COMPLETED'
           WHEN 1 THEN 'ACTIVE'
           ELSE 'WAITING'
       END
FROM reservation_data;

SELECT setval(
    pg_get_serial_sequence('parking_spaces', 'id'),
    (SELECT MAX(id) FROM parking_spaces),
    true
);
