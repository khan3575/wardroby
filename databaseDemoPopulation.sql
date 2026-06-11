-- 1. Insert Users
-- The plaintext password for all three accounts is: password
use wardroby_db;
INSERT INTO users (email, password, first_name, enabled) VALUES
                                                             ('sakib@wardroby.com', '$2a$10$9TwGxDEEZVKYOtTJAxGVbeQxeB7R7FxNIU5qHxKlq8DP7.Xfi65Ky', 'Sakib', 1), -- ID: 1
                                                             ('khan@wardroby.com', '$2a$10$9TwGxDEEZVKYOtTJAxGVbeQxeB7R7FxNIU5qHxKlq8DP7.Xfi65Ky', 'Khan', 1),   -- ID: 2
                                                             ('tamim@wardroby.com', '$2a$10$9TwGxDEEZVKYOtTJAxGVbeQxeB7R7FxNIU5qHxKlq8DP7.Xfi65Ky', 'Tamim', 1); -- ID: 3

-- 2. Insert Core Authorities
INSERT INTO authorities (authority) VALUES
                                        ('ROLE_ADMIN'),     -- ID: 1
                                        ('ROLE_MODERATOR'), -- ID: 2
                                        ('ROLE_USER');      -- ID: 3

-- 3. Map Users to Authorities in the Junction Table
INSERT INTO user_authorities (user_id, auth_id) VALUES
-- Sakib gets Admin (1), Moderator (2), and User (3)
(1, 1),
(1, 2),
(1, 3),

-- Khan gets Moderator (2) and User (3)
(2, 2),
(2, 3),

-- Tamim gets User (3)
(3, 3);