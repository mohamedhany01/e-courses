-- Find All the Toys for Hermione's cats
SELECT toys.name
FROM toys
    JOIN cats ON cats.id = toys.cat_id
    JOIN cat_owners ON cats.id = cat_owners.cat_id
    JOIN owners ON cat_owners.owner_id = owners.id
WHERE owners.first_name = 'Hermione';
-- SELECT toys.name
-- FROM toys
-- JOIN cats ON toys.cat_id = cats.id
-- WHERE cats.id IN (
--   SELECT cat_owners.cat_id
--   FROM cat_owners
--   JOIN owners ON cat_owners.owner_id = owners.id
--   WHERE owners.first_name = 'Hermione'
-- );
