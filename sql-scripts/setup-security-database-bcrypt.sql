USE `library`;

DROP TABLE IF EXISTS `authorities`;
DROP TABLE IF EXISTS `users`;

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `username` varchar(50) NOT NULL,
  `password` char(68) NOT NULL,
  `enabled` tinyint NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Inserting data for table `users`
--
-- The passwords are encrypted using BCrypt : https://www.bcryptcalculator.com/encode
--
-- Default passwords here are: test
--

INSERT INTO `users` 
VALUES 
('alex','{bcrypt}$2a$10$5xPXoIESvIT2bemvia/F3OkkiLWyPnaVlStHbkXm0UDUOyX42pll2',1),
('emma','{bcrypt}$2a$10$5xPXoIESvIT2bemvia/F3OkkiLWyPnaVlStHbkXm0UDUOyX42pll2',1),
('john','{bcrypt}$2a$10$5xPXoIESvIT2bemvia/F3OkkiLWyPnaVlStHbkXm0UDUOyX42pll2',1);


--
-- Table structure for table `authorities`
--

CREATE TABLE `authorities` (
  `username` varchar(50) NOT NULL,
  `authority` varchar(50) NOT NULL,
  UNIQUE KEY `authorities4_idx_1` (`username`,`authority`),
  CONSTRAINT `authorities4_ibfk_1` FOREIGN KEY (`username`) REFERENCES `users` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Inserting data for table `authorities`
--

INSERT INTO `authorities` 
VALUES 
('alex','ROLE_USER'),
('emma','ROLE_USER'),
('emma','ROLE_MANAGER'),
('john','ROLE_USER'),
('john','ROLE_MANAGER'),
('john','ROLE_ADMIN');