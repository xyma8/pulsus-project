create database pulsusdb

USE pulsusdb;

CREATE TABLE `files_on_server` (
	`id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `filehash` CHAR(64) NOT NULL,
    `extension` VARCHAR(8) NOT NULL,
    `size` BIGINT NOT NULL,
    `path` VARCHAR(254) NOT NULL,
	`timestamp` DATETIME NOT NULL
)

CREATE TABLE `roles` (
	`id` INT AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR(50) NOT NULL,
    `description` VARCHAR(254)
)

CREATE TABLE `users` (
	`id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR(20) NOT NULL,
    `surname` VARCHAR(20) NOT NULL,
	`email` VARCHAR(254) UNIQUE NOT NULL,
    `login` VARCHAR(24) UNIQUE NOT NULL,
	`password` VARCHAR(254) NOT NULL,
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
    `name` VARCHAR(254) NOT NULL,
    `description` TEXT,
    `type_sport` VARCHAR(100) NOT NULL,
    `summary` BIGINT NOT NULL,
    `file_workout` BIGINT NOT NULL,
    `access_type` INT NOT NULL,
    FOREIGN KEY (user) REFERENCES users(id),
    FOREIGN KEY (summary) REFERENCES workouts_summary(id),
    FOREIGN KEY (file_workout) REFERENCES files_on_server(id)
)

CREATE TABLE `workouts_summary` (
	`id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `total_distance` FLOAT,
	`total_ellapsed_time` FLOAT,
    `total_timer_time` FLOAT,
    `total_ascent` BIGINT,
    `start_time` DATETIME NOT NULL
)

CREATE TABLE `types_sports` (
	`id` INT AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR(100) NOT NULL,
    `description` VARCHAR(254) NOT NULL
)

CREATE TABLE `workouts_photos` (
	`id` BIGINT AUTO_INCREMENT PRIMARY KEY,
	`workout` BIGINT NOT NULL,
    `photo` BIGINT NOT NULL,
    FOREIGN KEY (photo) REFERENCES files_on_server(id),
    FOREIGN KEY (workout) REFERENCES workouts(id)
)

CREATE TABLE `subscriptions` (
	`id` BIGINT AUTO_INCREMENT PRIMARY KEY,
	`follower` BIGINT NOT NULL,
    `followed` BIGINT NOT NULL,
    FOREIGN KEY (follower) REFERENCES users(id),
    FOREIGN KEY (followed) REFERENCES users(id)
)

CREATE TABLE `workouts_likes` (
	`id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `user` BIGINT NOT NULL,
    `workout` BIGINT NOT NULL,
    FOREIGN KEY (user) REFERENCES users(id),
    FOREIGN KEY (workout) REFERENCES workouts(id)
)

INSERT INTO `types_sports` (`name`, `description`)
VALUES('CYCLING', 'cycling')

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




DELIMITER //

CREATE PROCEDURE get_workouts_for_posts(
    IN p_userIds TEXT,
    IN p_page INT,
    IN p_size INT
)
BEGIN
    DECLARE v_offset INT;

    -- Calculate the offset for pagination
    SET v_offset = (p_page - 1) * p_size;

    -- Prepare dynamic SQL statement
    SET @sql = CONCAT(
        'SELECT w.id ',
        'FROM workouts w ',
        'JOIN workouts_summary ws ON w.summary = ws.id ',
        'WHERE w.user IN (', p_userIds, ') ',
        'ORDER BY ws.start_time DESC ',
        'LIMIT ', v_offset, ', ', p_size
    );

    -- Execute the dynamic SQL statement
    PREPARE stmt FROM @sql;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;
END //

DELIMITER ;


CALL get_workouts_for_posts('14', 1, 5);
