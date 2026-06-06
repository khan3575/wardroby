DROP DATABASE IF EXISTS wardroby_db;
CREATE DATABASE wardroby_db;
USE wardroby_db;

-- 1. Main User table (Stays the same)
CREATE TABLE users (
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(60),
    last_name VARCHAR(60),
    enabled TINYINT(1) DEFAULT 1,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- 2. Refactored Authorities table
CREATE TABLE authorities (
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL, -- 1. Changed from email string to a numeric column
    authority VARCHAR(50) NOT NULL,
    -- 2. Foreign Key now points directly to the users table's primary key ID
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);