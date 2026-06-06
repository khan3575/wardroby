-- 3. Populate data

-- Insert Users (IDs 1, 2, and 3 will be generated automatically)
INSERT INTO users (email, password, first_name, enabled) VALUES
('sakib@wardroby.com', '$2a$10$wN.9.2J1G2/S2L3K4J5G6O7P8Q9R0T1U2V3W4X5Y6Z7A8B9C0D1E2', 'Sakib', 1), -- ID: 1
('khan@wardroby.com', '$2a$10$wN.9.2J1G2/S2L3K4J5G6O7P8Q9R0T1U2V3W4X5Y6Z7A8B9C0D1E2', 'Khan', 1),   -- ID: 2
('tamim@wardroby.com', '$2a$10$wN.9.2J1G2/S2L3K4J5G6O7P8Q9R0T1U2V3W4X5Y6Z7A8B9C0D1E2', 'Tamim', 1); -- ID: 3

-- Insert Authorities mapped to user_id instead of email
INSERT INTO authorities (user_id, authority) VALUES
(1, 'ROLE_ADMIN'),      -- Sakib
(1, 'ROLE_MODERATOR'),  -- Sakib
(1, 'ROLE_USER'),       -- Sakib
(2, 'ROLE_MODERATOR'),  -- Khan
(2, 'ROLE_USER'),       -- Khan (Fixed the typo from 'ROLEauthorities_USER')
(3, 'ROLE_USER');       -- Tamim