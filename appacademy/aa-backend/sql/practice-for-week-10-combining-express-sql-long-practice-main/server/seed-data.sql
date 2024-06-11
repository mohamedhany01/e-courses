-- BASIC PHASE 1A - DROP and CREATE table
-- Enable foreign key support (optional, as there are no foreign keys in this table)
PRAGMA foreign_keys = ON;
-- Drop the table if it exists
DROP TABLE IF EXISTS trees;
-- Create the trees table
CREATE TABLE trees (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    tree VARCHAR(32) NOT NULL,
    location VARCHAR(64) NOT NULL,
    height_ft REAL,
    ground_circumference_ft REAL
);
-- BASIC PHASE 1B - INSERT seed data
INSERT INTO trees (
        id,
        tree,
        location,
        height_ft,
        ground_circumference_ft
    )
VALUES (
        1,
        'General Sherman',
        'Sequoia National Park',
        274.9,
        102.6
    ),
    (
        2,
        'General Grant',
        'Kings Canyon National Park',
        268.1,
        107.5
    ),
    (
        3,
        'President',
        'Sequoia National Park',
        240.9,
        93.0
    ),
    (
        4,
        'Lincoln',
        'Sequoia National Park',
        255.8,
        98.3
    ),
    (5, 'Stagg', 'Private Land', 243.0, 109.0);
