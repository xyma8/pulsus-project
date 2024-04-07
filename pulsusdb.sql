create database pulsusdb

USE pulsusdb;

CREATE TABLE `files_on_server` (
	`id` INT AUTO_INCREMENT PRIMARY KEY,
    `extension` varchar(8) NOT NULL,
    `size` float NOT NULL,
    `path` varchar(254) NOT NULL
)

CREATE TABLE `roles` (
	`id` INT AUTO_INCREMENT PRIMARY KEY,
    `name` varchar(50) NOT NULL,
    `description` varchar(254)
)

CREATE TABLE `users` (
	`id` INT AUTO_INCREMENT PRIMARY KEY,
    `name` varchar(20) NOT NULL,
    `surname` varchar(20) NOT NULL,
	`email` varchar(254) UNIQUE NOT NULL,
    `login` varchar(24) UNIQUE NOT NULL,
	`password` varchar(254) NOT NULL,
    `profile_picture` INT NULL,
    `user_role` INT NOT NULL,
	FOREIGN KEY (profile_picture) REFERENCES files_on_server(id),
    FOREIGN KEY (user_role) REFERENCES roles(id)
)
