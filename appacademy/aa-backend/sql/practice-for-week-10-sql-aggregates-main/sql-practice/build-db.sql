PRAGMA foreign_keys = on;
-- Turns on foreign key support in SQLite3
-- Create / re-create tables
DROP TABLE IF EXISTS toys;
DROP TABLE IF EXISTS cats;
CREATE TABLE cats (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  name TEXT,
  birth_year INTEGER
);
CREATE TABLE toys (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  name TEXT,
  cat_id INTEGER,
  FOREIGN KEY (cat_id) REFERENCES cats(id) ON DELETE CASCADE
);
-- Seed Data
INSERT INTO cats (name, birth_year)
VALUES ('Smudge', 2014),
  ('Molly', 2015),
  ('Lucky', 2016),
  ('Bella', 2020),
  ('Tiger', 2012),
  ('Oscar', 2010),
  ('Garfield', 2009),
  ('Crookshanks', 2017);
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
-- Q1
SELECT COUNT(*)
FROM cats AS total_cats;
-- Q2
SELECT MAX(birth_year)
FROM cats;
SELECT MIN(birth_year)
FROM cats;
-- BONUS | Q1
SELECT cats.name,
  COUNT(*) AS total_toys_per_cat
FROM cats
  JOIN toys ON cats.id = toys.cat_id
GROUP BY toys.cat_id
ORDER BY total_toys_per_cat DESC;
-- Q2
SELECT cats.name,
  COUNT(*) AS total_toys_per_cat
FROM cats
  JOIN toys ON cats.id = toys.cat_id
GROUP BY toys.cat_id
HAVING total_toys_per_cat >= 2;
