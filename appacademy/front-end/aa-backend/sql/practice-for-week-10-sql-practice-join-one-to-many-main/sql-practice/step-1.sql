-- Step 1
-- JOIN the tables, matching FOREIGN KEYs to the corresponding PRIMARY KEY.

SELECT * FROM albums
  JOIN bands ON (bands.id = albums.band_id);
