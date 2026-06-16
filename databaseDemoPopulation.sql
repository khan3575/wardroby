USE wardroby_db;

-- ==========================================
-- 1. Insert Users
-- The plaintext password for all three accounts is: password
-- ==========================================
INSERT INTO users (email, password, first_name, enabled) VALUES
                                                             ('sakib@wardroby.com', '$2a$10$9TwGxDEEZVKYOtTJAxGVbeQxeB7R7FxNIU5qHxKlq8DP7.Xfi65Ky', 'Sakib', 1), -- ID: 1
                                                             ('khan@wardroby.com', '$2a$10$9TwGxDEEZVKYOtTJAxGVbeQxeB7R7FxNIU5qHxKlq8DP7.Xfi65Ky', 'Khan', 1),   -- ID: 2
                                                             ('tamim@wardroby.com', '$2a$10$9TwGxDEEZVKYOtTJAxGVbeQxeB7R7FxNIU5qHxKlq8DP7.Xfi65Ky', 'Tamim', 1); -- ID: 3

-- ==========================================
-- 2. Insert Core Authorities
-- ==========================================
INSERT INTO authorities (authority) VALUES
                                        ('ROLE_ADMIN'),     -- ID: 1
                                        ('ROLE_MODERATOR'), -- ID: 2
                                        ('ROLE_USER');      -- ID: 3

-- ==========================================
-- 3. Map Users to Authorities
-- ==========================================
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

-- ==========================================
-- 4. Insert Items (Wardrobe Inventory for User 1)
-- Categories: TOP, BOTTOM, OUTERWEAR, FOOTWEAR, ACCESSORY
-- Seasons: SPRING, SUMMER, FALL, WINTER, ALL_SEASON
-- ==========================================
INSERT INTO items (user_id, name, brand, color, image_path, category, season) VALUES
                                                                                  (1, 'Navy Blue Chinos', 'Zara', 'Navy', 'navy-chinos.jpg', 'BOTTOM', 'ALL_SEASON'),         -- Item 1
                                                                                  (1, 'White Oxford Shirt', 'Uniqlo', 'White', 'white-oxford.jpg', 'TOP', 'ALL_SEASON'),      -- Item 2
                                                                                  (1, 'Grey Wool Overcoat', 'H&M', 'Grey', 'grey-coat.jpg', 'OUTERWEAR', 'WINTER'),           -- Item 3
                                                                                  (1, 'Black Chelsea Boots', 'Clarks', 'Black', 'chelsea-boots.jpg', 'FOOTWEAR', 'FALL'),     -- Item 4
                                                                                  (1, 'Vintage Graphic Tee', 'Levis', 'Black', 'graphic-tee.jpg', 'TOP', 'SUMMER'),           -- Item 5
                                                                                  (1, 'Classic Denim Jacket', 'Levis', 'Blue', 'denim-jacket.jpg', 'OUTERWEAR', 'SPRING');    -- Item 6

-- ==========================================
-- 5. Insert Combos (Outfits for User 1)
-- Occasions: CASUAL, OFFICE, FORMAL, PARTY, WORKOUT, LOUNGE
-- ==========================================
INSERT INTO combos (user_id, name, occasion) VALUES
                                                 (1, 'Smart Casual Office', 'OFFICE'),   -- Combo 1
                                                 (1, 'Weekend Coffee Run', 'CASUAL'),    -- Combo 2
                                                 (1, 'Winter Evening Out', 'PARTY');     -- Combo 3

-- ==========================================
-- 6. Map Items to Combos (The Join Table)
-- ==========================================
INSERT INTO combo_items (combo_id, item_id) VALUES
-- Combo 1: Smart Casual Office (Chinos, Oxford Shirt, Chelsea Boots)
(1, 1),
(1, 2),
(1, 4),

-- Combo 2: Weekend Coffee Run (Chinos, Graphic Tee, Denim Jacket)
(2, 1),
(2, 5),
(2, 6),

-- Combo 3: Winter Evening Out (Chinos, Oxford Shirt, Wool Overcoat, Chelsea Boots)
(3, 1),
(3, 2),
(3, 3),
(3, 4);