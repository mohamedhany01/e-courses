-- Find the name of the cats co-owned by both George Beatty and Melynda Abshire
SELECT cats.name
FROM cats
WHERE cats.id IN (
        SELECT cat_id
        FROM cat_owners
        WHERE owner_id = (
                SELECT id
                FROM owners
                WHERE first_name = 'George'
                    AND last_name = 'Beatty'
            )
    )
    AND cats.id IN (
        SELECT cat_id
        FROM cat_owners
        WHERE owner_id = (
                SELECT id
                FROM owners
                WHERE first_name = 'Melynda'
                    AND last_name = 'Abshire'
            )
    );
