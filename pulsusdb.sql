create database pulsusdb

USE pulsusdb;

CREATE TABLE `files_on_server` (
	`id` BIGINT AUTO_INCREMENT PRIMARY KEY,
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
	`id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `name` varchar(20) NOT NULL,
    `surname` varchar(20) NOT NULL,
	`email` varchar(254) UNIQUE NOT NULL,
    `login` varchar(24) UNIQUE NOT NULL,
	`password` varchar(254) NOT NULL,
    `profile_picture` BIGINT NULL,
	FOREIGN KEY (profile_picture) REFERENCES files_on_server(id)
)

CREATE TABLE `users_roles` (
	`id` INT AUTO_INCREMENT PRIMARY KEY,
    `user` BIGINT NOT NULL,
    `role` INT NOT NULL,
    FOREIGN KEY (user) REFERENCES users(id),
    FOREIGN KEY (role) REFERENCES roles(id)
)

INSERT INTO `roles` (`name`, `description`)
VALUES('ROLE_USER', 'user');

INSERT INTO `roles` (`name`, `description`)
VALUES('ROLE_ADMIN', 'admin');

INSERT INTO `users_roles` (`user`, `role`)
VALUES(1,1)

INSERT INTO `users_roles` (`user`, `role`)
VALUES(2,2)

INSERT INTO `users_roles` (`user`, `role`)
VALUES(3,1)
