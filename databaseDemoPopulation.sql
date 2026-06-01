-- 3. Populate data
INSERT INTO users (email, password, first_name, enabled) VALUES
('sakib@wardroby.com', '$2a$10$wN.9.2J1G2/S2L3K4J5G6O7P8Q9R0T1U2V3W4X5Y6Z7A8B9C0D1E2', 'Sakib', 1),
('khan@wardroby.com', '$2a$10$wN.9.2J1G2/S2L3K4J5G6O7P8Q9R0T1U2V3W4X5Y6Z7A8B9C0D1E2', 'Khan', 1),
('tamim@wardroby.com', '$2a$10$wN.9.2J1G2/S2L3K4J5G6O7P8Q9R0T1U2V3W4X5Y6Z7A8B9C0D1E2', 'Tamim', 1);

INSERT INTO authorities (email, authority) VALUES
('sakib@wardroby.com', 'ROLE_ADMIN'),
('sakib@wardroby.com', 'ROLE_MODERATOR'),
('sakib@wardroby.com', 'ROLE_USER'),
('khan@wardroby.com', 'ROLE_MODERATOR'),
('khan@wardroby.com', 'ROLE_USER'),
('tamim@wardroby.com', 'ROLE_USER');