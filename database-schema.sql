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

CREATE table password_reset_tokens(
	id BIGINT not null primary key AUTO_INCREMENT,
    user_id BIGINT not null,
    token_hash varchar(255) not null,
    expiry_date datetime not null,
    used tinyint not null default (0),
    constraint fk_password_reset_token foreign key(user_id) references users(id) on delete cascade

);

-- items --

CREATE TABLE items(
	id BIGINT PRIMARY KEY auto_increment,
    user_id BIGINT not null,
    name varchar(255) not null,
    brand varchar(100),
    color varchar(50),
    image_path varchar(255) not null,
    category varchar(50) not null,
    season varchar(50) not null,
    created_at datetime default CURRENT_TIMESTAMP,
    constraint fk_items_users
    foreign key (user_id)
    references users(id) on delete cascade
);

CREATE TABLE combos(
	id BIGINT not null primary key auto_increment,
    user_id BIGINT not null,
    name varchar(255) not null,
    occasion varchar(50) not null,
    created_at datetime default current_timestamp,
    constraint fk_combo_users
    foreign key (user_id)
    references users(id) on delete cascade
);

CREATE TABLE combo_items(
	combo_id BIGINT not null,
    item_id BIGINT not null,
    primary key(combo_id, item_id),
    constraint fk_ci_combo
    foreign key (combo_id)
    references combos(id) on delete cascade,
    constraint fk_ci_items
    foreign key (item_id)
    references items(id)
    on delete cascade
);