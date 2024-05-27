DROP TABLE IF EXISTS rental_profiles;

DROP TABLE IF EXISTS comments;

DROP TABLE IF EXISTS users;

CREATE TYPE comment_tag AS ENUM (
    'registerred_to_rent',
    'mold',
    'pet_friendly',
    'parking',
    'shared_walls',
    'accessibility',
    'access_to_utility_panels',
    'accessory_dwelling_unit'
);

CREATE TABLE rental_profiles (
    address VARCHAR(255) PRIMARY KEY,
    pics TEXT ARRAY);

CREATE TABLE comments (
    comment_id BIGSERIAL PRIMARY KEY,
    address VARCHAR(255),
    user_name VARCHAR(255),
    comment_text TEXT,
    tags comment_tag ARRAY,
    creation_time TIMESTAMP
);

CREATE INDEX by_address ON comments (address);
CREATE INDEX by_user_name ON comments (user_name);

CREATE TABLE users (
    user_name VARCHAR(255) PRIMARY KEY,
    email VARCHAR(255)
);