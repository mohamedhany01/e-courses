-- Find the first and last names of the owner whose cats are born after the year 2015
SELECT owners.first_name,
    owners.last_name
FROM owners
    JOIN cat_owners ON cat_owners.owner_id = owners.id
    JOIN cats ON cats.id = cat_owners.cat_id
WHERE cats.birth_year > 2015;
