CREATE DATABASE IF NOT EXISTS gym_membership
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE gym_membership;

CREATE TABLE IF NOT EXISTS members (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    phone VARCHAR(30) NOT NULL,
    membership_type ENUM('Regular', 'Premium') NOT NULL,
    registration_date DATE NOT NULL,
    expiry_date DATE NOT NULL,
    INDEX idx_members_name (name),
    CONSTRAINT chk_members_dates CHECK (expiry_date >= registration_date)
);
