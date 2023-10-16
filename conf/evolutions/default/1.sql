-- !Ups

-- arithmetic_calculator.operations definition

CREATE TABLE `operations` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `type` enum('addition','subtraction','multiplication','division','square_root','random_string') DEFAULT NULL,
  `cost` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- arithmetic_calculator.records definition

CREATE TABLE `records` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `operation_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `amount` decimal(10,0) DEFAULT NULL,
  `user_balance` decimal(10,0) DEFAULT NULL,
  `operation_response` varchar(65) DEFAULT NULL,
  `date` timestamp NULL DEFAULT NULL,
  `deleted_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- arithmetic_calculator.users definition

CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(32) DEFAULT NULL,
  `password` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `status` enum('active','inactive') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO arithmetic_calculator.operations
(id, `type`, cost)
VALUES(1, 'addition', 10);
INSERT INTO arithmetic_calculator.operations
(id, `type`, cost)
VALUES(2, 'subtraction', 15);
INSERT INTO arithmetic_calculator.operations
(id, `type`, cost)
VALUES(3, 'multiplication', 50);
INSERT INTO arithmetic_calculator.operations
(id, `type`, cost)
VALUES(4, 'division', 100);
INSERT INTO arithmetic_calculator.operations
(id, `type`, cost)
VALUES(5, 'square_root', 200);
INSERT INTO arithmetic_calculator.operations
(id, `type`, cost)
VALUES(6, 'random_string', 400);

INSERT INTO arithmetic_calculator.records
(id, operation_id, user_id, amount, user_balance, operation_response, `date`, deleted_at)
VALUES(1, 1, 1, 2000, 2000, '2000', '2023-10-07 00:00:00', NULL);

INSERT INTO arithmetic_calculator.users
(id, username, password, status)
VALUES(1, 'rootuser@gmail.com', '$2a$10$Oh234NML9PX/O4xJm.J8ZeJ0LxkgPRol7wDNw1jBJwg9/R4/QEBa6', 'active');

-- !Downs

DROP TABLE operations;
DROP TABLE records;
DROP TABLE users;



