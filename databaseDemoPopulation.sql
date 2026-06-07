-- 1. Insert Users (IDs 1, 2, and 3 will be generated automatically)
INSERT INTO users (email, password, first_name, enabled) VALUES
('sakib@wardroby.com', '$2a$10$wN.9.2J1G2/S2L3K4J5G6O7P8Q9R0T1U2V3W4X5Y6Z7A8B9C0D1E2', 'Sakib', 1), -- ID: 1
('khan@wardroby.com', '$2a$10$wN.9.2J1G2/S2L3K4J5G6O7P8Q9R0T1U2V3W4X5Y6Z7A8B9C0D1E2', 'Khan', 1),   -- ID: 2
('tamim@wardroby.com', '$2a$10$wN.9.2J1G2/S2L3K4J5G6O7P8Q9R0T1U2V3W4X5Y6Z7A8B9C0D1E2', 'Tamim', 1); -- ID: 3

-- 2. Insert Core Authorities (IDs 1, 2, and 3 will be generated automatically)
-- Notice we only define each role ONE time now.
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