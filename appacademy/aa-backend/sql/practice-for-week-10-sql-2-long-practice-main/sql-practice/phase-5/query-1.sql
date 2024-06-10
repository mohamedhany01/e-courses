-- Find the first owner that has a last name containing the lowercase letter "r"
SELECT first_name
FROM owners
WHERE last_name LIKE "%r";
