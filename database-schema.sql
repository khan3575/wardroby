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
    authority VARCHAR(50) NOT NULL
);

CREATE TABLE user_authorities(
	user_id BIGINT not null,
    auth_id BIGINT not null,
    
    PRIMARY KEY(user_id, auth_id),
    
    constraint fk_user
		foreign key (user_id)
        references users(id)
        on delete cascade,
        
	constraint fk_authority
		foreign key (auth_id)
        references authorities(id)
        on delete cascade
    
);