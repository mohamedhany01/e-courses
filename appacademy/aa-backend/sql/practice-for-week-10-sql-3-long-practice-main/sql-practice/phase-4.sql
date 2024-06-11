-- Give "Red" the cat one of every toy the other cats have
INSERT INTO toys (cat_id, name)
SELECT (
        SELECT id
        FROM cats
        WHERE name = 'Red'
    ),
    name
FROM toys
GROUP BY name;
-- Query spoiled cats reporting the most spoiled first
SELECT cats.name,
    COUNT(toys.id) AS toy_count
FROM cats
    JOIN toys ON cats.id = toys.cat_id
GROUP BY toys.cat_id
HAVING COUNT(toys.cat_id) >= 2
ORDER BY toys.cat_id DESC;
