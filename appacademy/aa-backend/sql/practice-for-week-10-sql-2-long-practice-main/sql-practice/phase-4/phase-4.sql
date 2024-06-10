PRAGMA foreign_keys = on;
-- Turns foreign key support in SQLite3 on
-- Drop the tables if they exist
DROP TABLE IF EXISTS cats;
DROP TABLE IF EXISTS owners;
DROP TABLE IF EXISTS cat_owners;
DROP TABLE IF EXISTS toys;
-- Create the cats table
CREATE TABLE IF NOT EXISTS cats (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  name VARCHAR(100) NOT NULL,
  birth_year INTEGER NOT NULL
);
-- Create the owners table
CREATE TABLE IF NOT EXISTS owners (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  first_name VARCHAR(100) NOT NULL,
  last_name VARCHAR(100) NOT NULL
);
-- Create the cat_owners table
CREATE TABLE IF NOT EXISTS cat_owners (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  cat_id INTEGER NOT NULL,
  owner_id INTEGER NOT NULL,
  FOREIGN KEY (cat_id) REFERENCES cats(id) ON DELETE CASCADE,
  FOREIGN KEY (owner_id) REFERENCES owners(id)
);
-- Create the toys table
CREATE TABLE IF NOT EXISTS toys (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  name VARCHAR(100) NOT NULL,
  cat_id INTEGER,
  FOREIGN KEY (cat_id) REFERENCES cats(id) ON DELETE CASCADE
);
INSERT INTO owners (first_name, last_name)
VALUES ('Nathan', 'Shanahan'),
  ('Joycelyn', 'Cummerata'),
  ('Weston', 'Jones'),
  ('Melynda', 'Abshire'),
  ('George', 'Beatty'),
  ('Jonathan', 'Arbuckle'),
  ('Hermione', 'Granger');
INSERT INTO cats (name, birth_year)
VALUES ('Smudge', 2014),
  ('Molly', 2015),
  ('Lucky', 2016),
  ('Bella', 2020),
  ('Tiger', 2012),
  ('Oscar', 2010),
  ('Garfield', 2009),
  ('Crookshanks', 2017);
INSERT INTO cat_owners (cat_id, owner_id)
VALUES (1, 1),
  (1, 2),
  (2, 3),
  (3, 3),
  (4, 4),
  (5, 4),
  (5, 5),
  (7, 6),
  (8, 7);
INSERT INTO toys (cat_id, name)
VALUES (1, 'Catnip Mouse'),
  (2, 'Feather Wand'),
  (2, 'Scratcher'),
  (2, 'Laser Pointer'),
  (3, 'Chew Toy'),
  (4, 'Tunnel'),
  (4, 'Flopping Fish'),
  (5, 'Crinkle Ball'),
  (7, 'Cheetos'),
  (8, 'Yarn');
DELETE FROM cats
WHERE cats.name = 'Smudge';
