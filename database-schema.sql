CREATE DATABASE IF NOT EXISTS wardroby_db;
USE wardroby_db;

-- 1. Main User table
CREATE TABLE users (
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(60),
    last_name VARCHAR(60),
    enabled TINYINT(1) DEFAULT 1,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- 2. Authorities table
CREATE TABLE authorities (
    email VARCHAR(100) NOT NULL,
    authority VARCHAR(50) NOT NULL,
    FOREIGN KEY (email) REFERENCES users(email) ON DELETE CASCADE
);