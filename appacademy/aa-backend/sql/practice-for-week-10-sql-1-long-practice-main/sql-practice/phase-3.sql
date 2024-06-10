-- 1
-- A new customer joined the loyalty program with the following customer information
INSERT INTO customers (name, phone)
VALUES ('Rachel', '111-111-1111');
-- 2
-- Rachel purchases a coffee. (When adding a coffee order, you must first check the current points of the customer, update the customer's points, then add the coffee order.)
SELECT points
FROM customers
WHERE name = "Rachel";
UPDATE customers
SET points = points + 1
WHERE name = 'Rachel';
INSERT INTO coffee_orders (is_redeemed, customer_id)
VALUES (FALSE, 1);
-- 3
-- Two new customers joined the loyalty program with the following customer information
INSERT INTO customers (name, phone, email)
VALUES ('Monica', '222-222-2222', 'monica@friends.show');
INSERT INTO customers (name, phone, email)
VALUES ('Phoebe', '333-333-3333', 'phoebe@friends.show');
-- 4
-- Phoebe purchases three coffees.
UPDATE customers
SET points = points + 3
WHERE name = 'Phoebe';
INSERT INTO coffee_orders (is_redeemed, customer_id)
VALUES (FALSE, 3);
INSERT INTO coffee_orders (is_redeemed, customer_id)
VALUES (FALSE, 3);
INSERT INTO coffee_orders (is_redeemed, customer_id)
VALUES (FALSE, 3);
-- 5
-- Rachel and Monica each purchase four coffees.
UPDATE customers
SET points = points + 4
WHERE name = 'Rachel';
INSERT INTO coffee_orders (is_redeemed, customer_id)
VALUES (FALSE, 1);
INSERT INTO coffee_orders (is_redeemed, customer_id)
VALUES (FALSE, 1);
INSERT INTO coffee_orders (is_redeemed, customer_id)
VALUES (FALSE, 1);
INSERT INTO coffee_orders (is_redeemed, customer_id)
VALUES (FALSE, 1);
UPDATE customers
SET points = points + 4
WHERE name = 'Monica';
INSERT INTO coffee_orders (is_redeemed, customer_id)
VALUES (FALSE, 2);
INSERT INTO coffee_orders (is_redeemed, customer_id)
VALUES (FALSE, 2);
INSERT INTO coffee_orders (is_redeemed, customer_id)
VALUES (FALSE, 2);
INSERT INTO coffee_orders (is_redeemed, customer_id)
VALUES (FALSE, 2);
-- 6
-- Monica wants to know her new point total.
SELECT points
FROM customers
WHERE name = "Monica";
-- 7
-- Rachel wants to check her total points. Redeem her points for a coffee if she has enough points. If she doesn't, she wants to purchase a coffee.
SELECT points
FROM customers
WHERE name = "Rachel";
-- 10 points
UPDATE customers
SET points = 0
WHERE name = "Rachel";
INSERT INTO coffee_orders (is_redeemed, customer_id)
VALUES (TRUE, 1);
SELECT *
FROM customers
WHERE name = "Rachel";
SELECT *
FROM coffee_orders
WHERE customer_id = 1;
-- 8
-- Three new customers joined the loyalty program with the following customer information:
INSERT INTO customers (name, email)
VALUES ('Joey', 'joey@friends.show');
INSERT INTO customers (name, email)
VALUES ('Chandler', 'chandler@friends.show');
INSERT INTO customers (name, email)
VALUES ('Ross', 'ross@friends.show');
-- 9
-- Ross purchases six coffees.
UPDATE customers
SET points = points + 6
WHERE name = 'Ross';
INSERT INTO coffee_orders (is_redeemed, customer_id)
VALUES (TRUE, 6);
INSERT INTO coffee_orders (is_redeemed, customer_id)
VALUES (TRUE, 6);
INSERT INTO coffee_orders (is_redeemed, customer_id)
VALUES (TRUE, 6);
INSERT INTO coffee_orders (is_redeemed, customer_id)
VALUES (TRUE, 6);
INSERT INTO coffee_orders (is_redeemed, customer_id)
VALUES (TRUE, 6);
INSERT INTO coffee_orders (is_redeemed, customer_id)
VALUES (TRUE, 6);
-- 10
-- Monica purchases three coffees.
UPDATE customers
SET points = points + 3
WHERE name = 'Monica';
INSERT INTO coffee_orders (is_redeemed, customer_id)
VALUES (FALSE, 2);
INSERT INTO coffee_orders (is_redeemed, customer_id)
VALUES (FALSE, 2);
INSERT INTO coffee_orders (is_redeemed, customer_id)
VALUES (FALSE, 2);
-- 11
-- Phoebe wants to check her total points. Redeem her points for a coffee if she has enough points. If she doesn't, she wants to purchase a coffee.
SELECT points
FROM customers
WHERE name = "Phoebe";
UPDATE customers
SET points = points + 1
WHERE name = 'Phoebe';
INSERT INTO coffee_orders (is_redeemed, customer_id)
VALUES (FALSE, 3);
-- 12
-- Ross demands a refund for the last two coffees that he ordered. (Make sure you delete Ross's coffee orders and not anyone else's. Update his points to reflect the returned purchases.)
-- SELECT points FROM customers WHERE name="Ross";
-- SELECT * FROM coffee_orders WHERE id=6;
DELETE FROM coffee_orders
WHERE id = 18;
DELETE FROM coffee_orders
WHERE id = 19;
UPDATE customers
SET points = points - 2
WHERE name = 'Ross';
SELECT points
FROM customers
WHERE name = "Ross";
SELECT *
FROM coffee_orders
WHERE customer_id = 6;
-- 13
-- Joey purchases two coffees.
UPDATE customers
SET points = points + 2
WHERE name = 'Joey';
INSERT INTO coffee_orders (is_redeemed, customer_id)
VALUES (FALSE, 4);
INSERT INTO coffee_orders (is_redeemed, customer_id)
VALUES (FALSE, 4);
-- 14
-- Monica wants to check her total points. Redeem her points for a coffee if she has enough points. If she doesn't, she wants to purchase a coffee.
SELECT points
FROM customers
WHERE name = "Monica";
-- 12
UPDATE customers
SET points = points - 10
WHERE name = 'Monica';
-- 2
INSERT INTO coffee_orders (is_redeemed, customer_id)
VALUES (TRUE, 2);
-- 15
-- Chandler wants to delete his loyalty program account.
DELETE FROM customers
WHERE name = "Chandler";
DELETE FROM coffee_orders
WHERE customer_id = 5;
-- 16
-- Ross wants to check his total points. Redeem his points for a coffee if he has enough points. If he doesn't, he wants to purchase a coffee.
SELECT points
FROM customers
WHERE name = "Ross";
-- 9
UPDATE customers
SET points = points + 1
WHERE name = 'Ross';
INSERT INTO coffee_orders (is_redeemed, customer_id)
VALUES (FALSE, 6);
-- 17
-- Joey wants to check his total points. Redeem his points for a coffee if he has enough points. If he doesn't, he wants to purchase a coffee.
SELECT points
FROM customers
WHERE name = "Joey";
UPDATE customers
SET points = points + 1
WHERE name = 'Joey';
INSERT INTO coffee_orders (is_redeemed, customer_id)
VALUES (FALSE, 4);
-- 18
UPDATE customers
SET email = "p_as_in_phoebe@friends.show"
WHERE name = "Phoebe";
