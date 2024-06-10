DROP TABLE IF EXISTS employees;
DROP TABLE IF EXISTS parties;
DROP TABLE IF EXISTS performance_reviews;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS vacations;
DROP TABLE IF EXISTS employees_parties;
DROP TABLE IF EXISTS employees_vacations;
CREATE TABLE IF NOT EXISTS employees (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    first_name VARCHAR(40) NOT NULL,
    last_name VARCHAR(40) NOT NULL,
    department VARCHAR(50),
    role VARCHAR(80),
    partner_id INTEGER DEFAULT NULL,
    performance_score REAL DEFAULT 0.0
);
CREATE TABLE IF NOT EXISTS performance_reviews (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    score REAL,
    employee_id INTEGER,
    date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY(employee_id) REFERENCES employees(id)
);
CREATE TABLE IF NOT EXISTS roles (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    role VARCHAR(80),
    department VARCHAR(50),
    employee_id INTEGER,
    date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY(employee_id) REFERENCES employees(id)
);
CREATE TABLE IF NOT EXISTS parties (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    description text DEFAULT NULL,
    location VARCHAR(50),
    date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    budget INTEGER
);
CREATE TABLE IF NOT EXISTS employees_parties (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    employee_id INTEGER,
    party_id INTEGER,
    FOREIGN KEY(employee_id) REFERENCES employees(id),
    FOREIGN KEY(party_id) REFERENCES parties(id)
);
CREATE TABLE IF NOT EXISTS vacations (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    description text DEFAULT NULL,
    start_date TIMESTAMP,
    end_date TIMESTAMP
);
CREATE TABLE IF NOT EXISTS employees_vacations (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    employee_id INTEGER,
    vacation_id INTEGER,
    FOREIGN KEY(employee_id) REFERENCES employees(id),
    FOREIGN KEY(vacation_id) REFERENCES vacations(id)
);
-- 1
-- Add "Michael Scott" to your list of employees in the "Management" department in the role of "Regional Manager"
INSERT INTO employees (first_name, last_name, department, role)
VALUES (
        "Michael",
        "Scott",
        "Management",
        "Regional Manager"
    );
INSERT INTO roles (department, role, employee_id)
VALUES ("Management", "Regional Manager", 1);
INSERT INTO performance_reviews (employee_id)
VALUES (1);
-- 2
-- Add "Dwight Schrute" to your list of employees in the "Sales" department in the role of "Assistant Regional Manager"
INSERT INTO employees (first_name, last_name, department, role)
VALUES (
        "Dwight",
        "Schrute",
        "Sales",
        "Assistant Regional Manager"
    );
INSERT INTO roles (department, role, employee_id)
VALUES ("Sales", "Assistant Regional Manager", 2);
INSERT INTO performance_reviews (employee_id)
VALUES (2);
-- 3
-- Add "Jim Halpert" to your list of employees in the "Sales" department in the role of "Sales Representative"
INSERT INTO employees (first_name, last_name, department, role)
VALUES (
        "Jim",
        "Halpert",
        "Sales",
        "Sales Representative"
    );
INSERT INTO roles (department, role, employee_id)
VALUES ("Sales", "Sales Representative", 2);
INSERT INTO performance_reviews (employee_id)
VALUES (3);
-- 4
-- Add "Pam Beesly" to your list of employees in the "Reception" department in the role of "Receptionist"
INSERT INTO employees (first_name, last_name, department, role)
VALUES ("Pam", "Beesly", "Reception", "Receptionist");
INSERT INTO roles (department, role, employee_id)
VALUES ("Reception", "Receptionist", 2);
INSERT INTO performance_reviews (employee_id)
VALUES (4);
-- 5
-- Add "Kelly Kapoor" to your list of employees in the "Product Oversight" department in the role of "Customer Service Representative"
INSERT INTO employees (first_name, last_name, department, role)
VALUES (
        "Kelly",
        "Kapoor",
        "Product Oversight",
        "Customer Service Representative"
    );
INSERT INTO roles (department, role, employee_id)
VALUES (
        "Product Oversight",
        "Customer Service Representative",
        5
    );
INSERT INTO performance_reviews (employee_id)
VALUES (5);
-- 6
-- Add "Angela Martin" to your list of employees in the "Accounting" department in the role of "Head of Accounting"
INSERT INTO employees (first_name, last_name, department, role)
VALUES (
        "Angela",
        "Martin",
        "Accounting",
        "Head of Accounting"
    );
INSERT INTO roles (department, role, employee_id)
VALUES ("Accounting", "Head of Accounting", 6);
INSERT INTO performance_reviews (employee_id)
VALUES (6);
-- 7
-- Add "Roy Anderson" to your list of employees in the "Warehouse" department in the role of "Warehouse Staff"
INSERT INTO employees (first_name, last_name, department, role)
VALUES (
        "Roy",
        "Anderson",
        "Warehouse",
        "Warehouse Staff"
    );
INSERT INTO roles (department, role, employee_id)
VALUES ("Warehouse", "Warehouse Staff", 7);
INSERT INTO performance_reviews (employee_id)
VALUES (7);
-- 8
-- "Roy Anderson" and "Pam Beesly" are in a romantic relationship.
UPDATE employees
SET partner_id = 7
WHERE first_name = "Pam";
UPDATE employees
SET partner_id = 4
WHERE first_name = "Roy";
-- 9
-- "Ryan Howard" is hired in the "Reception" department as a "Temp" role.
INSERT INTO employees (first_name, last_name, department, role)
VALUES ("Ryan", "Howard", "Reception", "Temp");
INSERT INTO roles (department, role, employee_id)
VALUES ("Reception", "Temp", 8);
INSERT INTO performance_reviews (employee_id)
VALUES (8);
-- 10
-- An onsite office party is scheduled with a budget of $100.00.
INSERT INTO parties (location, budget)
VALUES ("onsite", 100.00);
-- 11
-- "Dwight Schrute" gets a performance review with a score of 3.3.
UPDATE employees
SET performance_score = 3.3
WHERE first_name = "Dwight";
INSERT INTO performance_reviews (score, employee_id)
VALUES (3.3, 2);
-- 12
-- "Jim Halpert" gets a performance review with a score of 4.2.
UPDATE employees
SET performance_score = 4.2
WHERE first_name = "Jim";
INSERT INTO performance_reviews (score, employee_id)
VALUES (4.2, 3);
-- 13
-- "Dwight Schrute"'s past performance review needs to be changed to a score of 9.0.
UPDATE employees
SET performance_score = 3.3
WHERE first_name = "Dwight";
INSERT INTO performance_reviews (score, employee_id)
VALUES (9.0, 2);
-- 14
-- "Jim Halpert"'s past performance review needs to be changed to a score of 9.3.
UPDATE employees
SET performance_score = 9.3
WHERE first_name = "Dwight";
INSERT INTO performance_reviews (score, employee_id)
VALUES (9.3, 3);
-- 15
-- "Jim Halpert" is promoted to the role of "Assistant Regional Manager".
UPDATE employees
SET role = "Assistant Regional Manager"
WHERE first_name = "Jim";
INSERT INTO roles (department, role, employee_id)
VALUES ("Sales", "Assistant Regional Manager", 3);
-- 16
-- "Ryan Howard" is promoted to the "Sales" department as the role of "Sales Representative".
UPDATE employees
SET role = "Sales"
WHERE first_name = "Ryan";
INSERT INTO roles (department, role, employee_id)
VALUES ("Sales", "Sales Representative", 3);
-- 17
-- An onsite office party is scheduled with a budget of $200.00.
INSERT INTO parties (location, budget)
VALUES ("onsite", 200.00);
-- 18
-- "Angela Martin" and "Dwight Schrute" are in a romantic relationship.
UPDATE employees
SET partner_id = 2
WHERE first_name = "Angela";
UPDATE employees
SET partner_id = 6
WHERE first_name = "Dwight";
-- 19
-- "Angela Martin" gets a performance review score of 6.2.
UPDATE employees
SET performance_score = 6.2
WHERE first_name = "Angela";
INSERT INTO performance_reviews (score, employee_id)
VALUES (6.2, 6);
-- 20
-- "Ryan Howard" and "Kelly Kapoor" are in a romantic relationship.
UPDATE employees
SET partner_id = 5
WHERE first_name = "Ryan";
UPDATE employees
SET partner_id = 8
WHERE first_name = "Kelly";
-- 21
-- An onsite office party is scheduled with a budget of $50.00.
INSERT INTO parties (location, budget)
VALUES ("onsite", 50.00);
-- 22
-- "Jim Halpert" moves to another office branch (make sure to remove his relationships and performance reviews if he has any).
DELETE FROM performance_reviews
WHERE employee_id = 3;
UPDATE employees
SET partner_id = NULL
WHERE id = 3;
-- 23
-- "Roy Anderson" and "Pam Beesly" are NO LONGER in a romantic relationship.
UPDATE employees
SET partner_id = NULL
WHERE first_name = "Pam";
UPDATE employees
SET partner_id = NULL
WHERE first_name = "Roy";
-- 24
-- "Pam Beesly" gets a performance review score of 7.6.
UPDATE employees
SET performance_score = 7.6
WHERE first_name = "Pam";
INSERT INTO performance_reviews (score, employee_id)
VALUES (7.6, 4);
-- 25
-- "Dwight Schrute" gets another performance review score of 8.7.
UPDATE employees
SET performance_score = 8.7
WHERE first_name = "Dwight";
INSERT INTO performance_reviews (score, employee_id)
VALUES (8.7, 2);
-- 26
-- "Ryan Howard" quits the office (make sure to remove his relationships and performance reviews if he has any).
DELETE FROM performance_reviews
WHERE employee_id = 8;
UPDATE employees
SET partner_id = NULL
WHERE id = 8;
-- 27
-- "Jim Halpert" moves back to this office branch's "Sales" department in the role of "Sales Representative"
UPDATE employees
SET role = "Sales Representative"
WHERE first_name = "Jim";
INSERT INTO roles (department, role, employee_id)
VALUES ("Sales", "Sales Representative", 3);
-- 28
-- "Karen Filippelli" moves from a different office into this office's "Sales" department in the role of "Sales Representative"
INSERT INTO employees (first_name, last_name, department, role)
VALUES ("Karen", "Filippelli", NULL, NULL);
INSERT INTO roles (department, role, employee_id)
VALUES (NULL, NULL, 5);
INSERT INTO performance_reviews (employee_id)
VALUES (5);
UPDATE employees
SET role = "Sales Representative"
WHERE first_name = "Karen";
INSERT INTO roles (department, role, employee_id)
VALUES ("Sales", "Sales Representative", 3);
-- 29
-- "Karen Filippelli" and "Jim Halpert" are in a romantic relationship.
UPDATE employees
SET partner_id = 3
WHERE first_name = "Karen";
UPDATE employees
SET partner_id = 9
WHERE first_name = "Jim";
-- 30
-- An onsite office party is scheduled with a budget of $120.00.
INSERT INTO parties (location, budget)
VALUES ("onsite", 120.00);
-- 31
-- The onsite office party scheduled right before this is canceled, and an offsite office party is scheduled instead with a budget of $300.00.
UPDATE parties
SET location = "offsite",
    budget = 300.00
WHERE id = 4;
-- 32
-- "Karen Filippelli" and "Jim Halpert" are NO LONGER in a romantic relationship.
UPDATE employees
SET partner_id = NULL
WHERE first_name = "Karen";
UPDATE employees
SET partner_id = NULL
WHERE first_name = "Jim";
-- 33
-- "Pam Beesly" and "Jim Halpert" are in a romantic relationship.
UPDATE employees
SET partner_id = 3
WHERE first_name = "Pam";
UPDATE employees
SET partner_id = 4
WHERE first_name = "Jim";
