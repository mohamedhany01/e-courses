-- Find names of the cats whose owners are both George Beatty and Melynda Abshire, or just George Beatty, or just Melynda Abshire
SELECT DISTINCT cats.name
FROM cats
    JOIN cat_owners AS co1 ON cats.id = co1.cat_id
    JOIN owners AS o1 ON co1.owner_id = o1.id
    LEFT JOIN cat_owners AS co2 ON cats.id = co2.cat_id
    LEFT JOIN owners AS o2 ON co2.owner_id = o2.id
WHERE (
        o1.first_name = 'George'
        AND o1.last_name = 'Beatty'
    )
    OR (
        o2.first_name = 'Melynda'
        AND o2.last_name = 'Abshire'
    );
