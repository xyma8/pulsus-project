create database pulsusdb

USE pulsusdb;

CREATE TABLE `files_on_server` (
	`id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `extension` varchar(8) NOT NULL,
    `size` BIGINT NOT NULL,
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
    `has_profile_picture` BOOLEAN NOT NULL DEFAULT FALSE
)

CREATE TABLE `users_roles` (
	`id` INT AUTO_INCREMENT PRIMARY KEY,
    `user` BIGINT NOT NULL,
    `role` INT NOT NULL,
    FOREIGN KEY (user) REFERENCES users(id),
    FOREIGN KEY (role) REFERENCES roles(id)
)

CREATE TABLE `workouts` (
	`id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `user` BIGINT NOT NULL,
    `name` varchar(254) NOT NULL,
    `description` varchar(254) NOT NULL,
    `type_sport` INT NOT NULL,
    `file_workout` BIGINT NOT NULL,
    `access_type` INT NOT NULL,
    `timestamp` DATETIME NOT NULL,
    FOREIGN KEY (user) REFERENCES users(id),
	FOREIGN KEY (type_sport) REFERENCES types_sports(id),
    FOREIGN KEY (file_workout) REFERENCES files_on_server(id)
)

CREATE TABLE `types_sports` (
	`id` INT AUTO_INCREMENT PRIMARY KEY,
    `name` varchar(100) NOT NULL,
    `description` varchar(254) NOT NULL
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
