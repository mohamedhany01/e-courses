--Insert new cat named "Red" born this year
INSERT INTO cats (name, birth_year)
VALUES ('Red', 2024);
--Assign ownership of new cat to George Beatty using subqueries
INSERT INTO cat_owners (cat_id, owner_id)
SELECT (
        SELECT id
        FROM cats
        WHERE name = 'Red'
    ),
    (
        SELECT id
        FROM owners
        WHERE first_name = 'George'
            AND last_name = 'Beatty'
    );
--Query to verify INSERTs worked properly
SELECT cats.name AS cat_name,
    owners.first_name,
    owners.last_name
FROM cats
    JOIN cat_owners ON cats.id = cat_owners.cat_id
    JOIN owners ON owners.id = cat_owners.owner_id
WHERE cats.name = "Red";
