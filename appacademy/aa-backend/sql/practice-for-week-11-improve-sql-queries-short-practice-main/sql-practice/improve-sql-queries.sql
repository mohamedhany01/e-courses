----------
-- Step 0 - Create a Query 
----------
-- Query: Select all cats that have a toy with an id of 5

SELECT cats.name
FROM cats
    JOIN cat_toys ON cats.id = cat_toys.cat_id
    JOIN toys ON toys.id = cat_toys.toy_id
WHERE cat_toys.toy_id = 5;

-- Paste your results below (as a comment):

-- Rachele
-- Rodger
-- Jamal


----------
-- Step 1 - Analyze the Query
----------
-- Query:

EXPLAIN QUERY PLAN
SELECT cats.name
FROM cats
    JOIN cat_toys ON cats.id = cat_toys.cat_id
    JOIN toys ON toys.id = cat_toys.toy_id
WHERE cat_toys.toy_id = 5;

-- Paste your results below (as a comment):

-- QUERY PLAN
-- |--SEARCH toys USING INTEGER PRIMARY KEY (rowid=?)
-- |--SCAN cat_toys
-- `--SEARCH cats USING INTEGER PRIMARY KEY (rowid=?)

-- What do your results mean?

    -- Was this a SEARCH or SCAN?
    -- SEARCH, SCAN, SEARCH

    -- What does that mean?
    -- SEARCH: Won't scan the whole records
    -- SCAN: Will scan the whole records one by one



----------
-- Step 2 - Time the Query to get a baseline
----------
-- Query (to be used in the sqlite CLI):

    -- .timer on
SELECT cats.name
FROM cats
    JOIN cat_toys ON cats.id = cat_toys.cat_id
    JOIN toys ON toys.id = cat_toys.toy_id
WHERE cat_toys.toy_id = 5;

-- Paste your results below (as a comment):

-- Rachele
-- Rodger
-- Jamal
-- Run Time: real 0.001 user 0.000000 sys 0.000000


----------
-- Step 3 - Add an index and analyze how the query is executing
----------

-- Create index:

 CREATE INDEX idx_cat_toys_cat_id_toy_id ON cat_toys(cat_id, toy_id);

-- Analyze Query:

EXPLAIN QUERY PLAN
SELECT cats.name
FROM cats
    JOIN cat_toys ON cats.id = cat_toys.cat_id
    JOIN toys ON toys.id = cat_toys.toy_id
WHERE cat_toys.toy_id = 5;

-- Paste your results below (as a comment):

-- |--SEARCH toys USING INTEGER PRIMARY KEY (rowid=?)
-- |--SEARCH cat_toys USING INDEX idx_cat_toys_toy_id (toy_id=?)
-- `--SEARCH cats USING INTEGER PRIMARY KEY (rowid=?)

-- Analyze Results:

    -- Is the new index being applied in this query?
    -- Yes




----------
-- Step 4 - Re-time the query using the new index
----------
-- Query (to be used in the sqlite CLI):

SELECT cats.name
FROM cats
    JOIN cat_toys ON cats.id = cat_toys.cat_id
    JOIN toys ON toys.id = cat_toys.toy_id
WHERE cat_toys.toy_id = 5;
-- Paste your results below (as a comment):
-- Rachele
-- Rodger
-- Jamal
-- Run Time: real 0.001 user 0.000000 sys 0.000000

-- Analyze Results:
    -- Are you still getting the correct query results?
        -- YES

    -- Did the execution time improve (decrease)?
        -- Note sure since I got the same time

    -- Do you see any other opportunities for making this query more efficient?
        -- Not sure

---------------------------------
-- Notes From Further Exploration
---------------------------------