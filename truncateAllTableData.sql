-- 1. Disable foreign key checks
SET FOREIGN_KEY_CHECKS = 0;

-- 2. Truncate your specific tables
TRUNCATE TABLE authorities;
TRUNCATE TABLE users;

-- 3. Re-enable foreign key checks
SET FOREIGN_KEY_CHECKS = 1;