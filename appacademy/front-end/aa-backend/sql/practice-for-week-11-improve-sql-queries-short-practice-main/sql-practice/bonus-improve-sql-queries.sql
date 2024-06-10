----------
-- Step 0 - Create a Query 
----------
-- Query: Find a count of `toys` records that have a price greater than
    -- 55 and belong to a cat that has the color "Olive".

SELECT COUNT(*)
FROM cats
    JOIN cat_toys ON cats.id = cat_toys.cat_id
    JOIN toys ON toys.id = cat_toys.toy_id
WHERE toys.price > 55
    AND cats.color = 'Olive';


-- Paste your results below (as a comment):
-- 215



----------
-- Step 1 - Analyze the Query
----------
-- Query:

EXPLAIN QUERY PLAN
SELECT COUNT(*)
FROM cats
    JOIN cat_toys ON cats.id = cat_toys.cat_id
    JOIN toys ON toys.id = cat_toys.toy_id WHE RE toys.price > 55
    AND cats.color = 'Olive';

-- Paste your results below (as a comment):
-- QUERY PLAN
-- |--SCAN cat_toys
-- |--SEARCH cats USING INTEGER PRIMARY KEY (rowid=?)
-- `--SEARCH toys USING INTEGER PRIMARY KEY (rowid=?)

-- What do your results mean?

    -- Was this a SEARCH or SCAN?
    -- SCAN, SEARCH, SEARCH

    -- What does that mean?
    -- SEARCH: Won't scan the whole records
    -- SCAN: Will scan the whole records one by one




----------
-- Step 2 - Time the Query to get a baseline
----------
-- Query (to be used in the sqlite CLI):

    -- .timer on

SELECT COUNT(*)
FROM cats
    JOIN cat_toys ON cats.id = cat_toys.cat_id
    JOIN toys ON toys.id = cat_toys.toy_id
WHERE toys.price > 55
    AND cats.color = 'Olive';

-- Paste your results below (as a comment):

-- Run Time: real 0.003 user 0.000000 sys 0.000000
-- Run Time: real 0.004 user 0.000000 sys 0.000000
-- Run Time: real 0.005 user 0.000000 sys 0.000000



----------
-- Step 3 - Add an index and analyze how the query is executing
----------

-- Create index:

    CREATE INDEX idx_cats_color ON cats(color);
    CREATE INDEX idx_toys_price ON toys(price);
    CREATE INDEX idx_cat_toys_cat_id_toy_id ON cat_toys(cat_id, toy_id);

-- Analyze Query:
EXPLAIN QUERY PLAN
SELECT COUNT(*)
FROM cats
    JOIN cat_toys ON cats.id = cat_toys.cat_id
    JOIN toys ON toys.id = cat_toys.toy_id
WHERE toys.price > 55
    AND cats.color = 'Olive';
QUERY PLAN

-- Paste your results below (as a comment):

-- QUERY PLAN
-- |--SEARCH cats USING COVERING INDEX idx_cats_color (color=?)
-- |--SEARCH cat_toys USING COVERING INDEX idx_cat_toys_cat_id_toy_id (cat_id=?)
-- `--SEARCH toys USING INTEGER PRIMARY KEY (rowid=?)
-- Run Time: real 0.000 user 0.000000 sys 0.000000

-- Analyze Results:

    -- Is the new index being applied in this query?
        -- YES



----------
-- Step 4 - Re-time the query using the new index
----------
-- Query (to be used in the sqlite CLI):

    SELECT COUNT(*)
FROM cats
    JOIN cat_toys ON cats.id = cat_toys.cat_id
    JOIN toys ON toys.id = cat_toys.toy_id
WHERE toys.price > 55
    AND cats.color = 'Olive';

-- Paste your results below (as a comment):
-- 215
-- Run Time: real 0.000 user 0.000000 sys 0.000000
-- Run Time: real 0.001 user 0.000000 sys 0.000000

-- Analyze Results:
    -- Are you still getting the correct query results?
    -- Yes

    -- Did the execution time improve (decrease)?
    -- Yes

    -- Do you see any other opportunities for making this query more efficient?
    -- No


---------------------------------
-- Notes From Further Exploration
---------------------------------
