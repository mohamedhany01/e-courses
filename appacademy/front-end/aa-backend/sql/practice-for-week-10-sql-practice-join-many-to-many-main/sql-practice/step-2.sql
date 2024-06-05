-- Step 2
-- Just like with one-to-many relationships, you can filter one table by any
-- associated data on a conected table.
SELECT musicians.first_name,
    musicians.last_name,
    instruments.type
FROM musicians
    JOIN musician_instruments ON musician_instruments.musician_id = musicians.id
    JOIN instruments ON musician_instruments.instrument_id = instruments.id
WHERE instruments.type = "piano";
