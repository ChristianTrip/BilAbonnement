DROP DATABASE IF EXISTS `car_subscription`;
CREATE DATABASE IF NOT EXISTS `car_subscription`; 
USE `car_subscription`;

SET NAMES utf8 ;
SET character_set_client = utf8mb4 ;


CREATE TABLE `users` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `user_type` varchar(45) NOT NULL,
  `name` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
INSERT INTO `users` (`user_id`, `user_type`, `name`, `password`) VALUES ('1', 'ADMIN', 'Alfred', 'Password1234');
INSERT INTO `users` (`user_id`, `user_type`, `name`, `password`) VALUES ('2', 'SURVEYREPORTER', 'James', 'Password1234');
INSERT INTO `users` (`user_id`, `user_type`, `name`, `password`) VALUES ('3', 'BUSINESS', 'Lars', 'Password1234');
INSERT INTO `users` (`user_id`, `user_type`, `name`, `password`) VALUES ('4', 'LEASEAPPROVER', 'Morten', 'Password1234');


CREATE TABLE `customers` (
  `customer_id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `address` varchar(45) DEFAULT NULL,
  `postal_code` varchar(45) DEFAULT NULL,
  `city` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `phone` varchar(45) DEFAULT NULL,
  `cpr` varchar(45) NOT NULL UNIQUE,
  `reg_number` varchar(45) DEFAULT NULL,
  `account_number` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`customer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
INSERT INTO `customers` (`customer_id`, `first_name`, `last_name`, `address`, `postal_code`, `city`, `email`, `phone`, `cpr`, `reg_number`, `account_number`) VALUES (1, 'Peter', 'Madsen', 'Amagerbrogade 1', '2300', 'København S', 'PMadsen@mail.dk', '12345678', '1212992883', '2222', '1234993302');
INSERT INTO `customers` (`customer_id`, `first_name`, `last_name`, `address`, `postal_code`, `city`, `email`, `phone`, `cpr`, `reg_number`, `account_number`) VALUES (2, 'Lars', 'Allan', 'Amagerbrogade 22', '2300', 'København S', 'LarsAllan@mail.dk', '61987234', '1204887375', '1111', '4321229845');


CREATE TABLE `lease_agreements` (
  `agreement_id` int(11) NOT NULL AUTO_INCREMENT,
  `approval_date` date NOT NULL,
  `start_date` date NOT NULL,
  `customer_cpr` varchar(45) NOT NULL,
  `chassis_number`varchar(45) NOT NULL,
  PRIMARY KEY (`agreement_id`),
  KEY `lease_customer_cpr_idx` (`customer_cpr`),
  CONSTRAINT `lease_customer_cpr` FOREIGN KEY (`customer_cpr`) REFERENCES `customers` (`cpr`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;
INSERT INTO `lease_agreements` (`agreement_id`, `approval_date`, `start_date`, `customer_cpr`, `chassis_number`) VALUES ('1', '2022-05-08', '2022-05-18', '1204887375', 'ZFA31200003050976');
INSERT INTO `lease_agreements` (`agreement_id`, `approval_date`, `start_date`, `customer_cpr`, `chassis_number`) VALUES ('2', '2022-05-09', '2022-08-01', '1212992883', 'ZFA31203103050976');
INSERT INTO `lease_agreements` (`agreement_id`, `approval_date`, `start_date`, `customer_cpr`, `chassis_number`) VALUES ('3', '2018-05-10', '2018-05-22', '1204887375', 'ZFA31209103050976');


CREATE TABLE `cars` (
  `car_id` int(11) NOT NULL AUTO_INCREMENT,
  `agreement_id` int(11) DEFAULT NULL,
  `chassis_number` varchar(45) NOT NULL UNIQUE,
  `brand` varchar(45) NOT NULL,
  `model` varchar(45) NOT NULL,
  PRIMARY KEY (`car_id`),
  KEY `car_agreement_id_idx` (`agreement_id`),
  CONSTRAINT `car_agreement_id` FOREIGN KEY (`agreement_id`) REFERENCES `lease_agreements` (`agreement_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;
INSERT INTO `cars` (`car_id`, `agreement_id`, `chassis_number`, `brand`, `model`) VALUES (1, 1, 'ZFA31200003050976', 'Fiat', 'Panda 1.2');
INSERT INTO `cars` (`car_id`, `agreement_id`, `chassis_number`, `brand`, `model`) VALUES (2, 2, 'ZFA31203103050976', 'Citroën', 'Grand C4 Spacetourer COOL');
INSERT INTO `cars` (`car_id`, `agreement_id`, `chassis_number`, `brand`, `model`) VALUES (3, 3, 'ZFA31209103050976', 'Peugeot', '208 Active Pack');


CREATE TABLE `subscriptions` (
  `subscription_id` int NOT NULL AUTO_INCREMENT,
  `agreement_id` int NOT NULL,
  `low_deductible` tinyint(1) NOT NULL,
  `delivery_insurance` tinyint(1) DEFAULT NULL,
  `length_in_months` varchar(45) NOT NULL,
  `has_standard_color` tinyint(1) NOT NULL,
  `is_limited` tinyint(1) NOT NULL,
  PRIMARY KEY (`subscription_id`),
  KEY `subscription_agreement_id_idx` (`agreement_id`),
  CONSTRAINT `subscription_agreement_id` FOREIGN KEY (`agreement_id`) REFERENCES `lease_agreements` (`agreement_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;
INSERT INTO `subscriptions` (`agreement_id`, `low_deductible`, `delivery_insurance`, `length_in_months`, `has_standard_color`, `is_limited`) VALUES (1, 1, null, 3, true, true);
INSERT INTO `subscriptions` (`agreement_id`, `low_deductible`, `delivery_insurance`, `length_in_months`, `has_standard_color`, `is_limited`) VALUES (2, 0, null, 24, false, true);
INSERT INTO `subscriptions` (`agreement_id`, `low_deductible`, `delivery_insurance`, `length_in_months`, `has_standard_color`, `is_limited`) VALUES (3, 1, 1, 36, true, false);


CREATE TABLE `pickup_places` (
  `pickup_place_id` int NOT NULL AUTO_INCREMENT,
  `agreement_id` int NOT NULL,
  `address` varchar(45) DEFAULT NULL,
  `postal_code` varchar(45) DEFAULT NULL,
  `city` varchar(45) DEFAULT NULL,
  `delivery_cost` int(11) DEFAULT NULL,
  PRIMARY KEY (`pickup_place_id`),
  KEY `pickup_agreement_id_idx` (`agreement_id`),
  CONSTRAINT `pickup_agreement_id` FOREIGN KEY (`agreement_id`) REFERENCES `lease_agreements` (`agreement_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
INSERT INTO `pickup_places` (`pickup_place_id`, `agreement_id`, `address`, `postal_code`, `city`, `delivery_cost`) VALUES ('1', '1', 'Amagerbrogade 55', '2300', 'København S', '500');
INSERT INTO `pickup_places` (`pickup_place_id`, `agreement_id`, `address`, `postal_code`, `city`, `delivery_cost`) VALUES ('2', '2', 'Holmbladsgade 40', '2300', 'København S', '300');
INSERT INTO `pickup_places` (`pickup_place_id`, `agreement_id`, `address`, `postal_code`, `city`, `delivery_cost`) VALUES ('3', '3', 'Holmbladsgade 40', '2300', 'København S', '300');


CREATE TABLE `price_estimates` (
  `price_estimate_id` int NOT NULL AUTO_INCREMENT,
  `agreement_id` int NOT NULL,
  `subscription_length` int(11) DEFAULT NULL,
  `km_per_month` int DEFAULT NULL,
  `total_price` int NOT NULL,
  PRIMARY KEY (`price_estimate_id`),
  KEY `estimate_agreement_id_idx` (`agreement_id`),
  CONSTRAINT `estimate_agreement_id` FOREIGN KEY (`agreement_id`) REFERENCES `lease_agreements` (`agreement_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
INSERT INTO `price_estimates` (`price_estimate_id`, `agreement_id`, `subscription_length`, `km_per_month`, `total_price`) VALUES ('1', '1', '3', '4500', '4500');
INSERT INTO `price_estimates` (`price_estimate_id`, `agreement_id`, `subscription_length`, `km_per_month`, `total_price`) VALUES ('2', '2', '24','1500', '4500');
INSERT INTO `price_estimates` (`price_estimate_id`, `agreement_id`, `subscription_length`, `km_per_month`, `total_price`) VALUES ('3', '3', '36', '2500', '4500');


CREATE TABLE `survey_reports` (
  `report_id` int NOT NULL AUTO_INCREMENT,
  `agreement_id` int NOT NULL,
  PRIMARY KEY (`report_id`),
  KEY `report_agreement_id_idx` (`agreement_id`),
  CONSTRAINT `report_agreement_id_` FOREIGN KEY (`agreement_id`) REFERENCES `lease_agreements` (`agreement_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
INSERT INTO `survey_reports` (`report_id`, `agreement_id`) VALUES ('1', '3');



CREATE TABLE `deficiencies` (
  `deficiency_id` int(11) NOT NULL AUTO_INCREMENT,
  `report_id` int(11) NOT NULL,
  `title` varchar(45) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `price` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`deficiency_id`),
  KEY `deficiency_report_id_idx` (`report_id`),
  CONSTRAINT `deficiency_report_id` FOREIGN KEY (`report_id`) REFERENCES `survey_reports` (`report_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
INSERT INTO `deficiencies` (`deficiency_id`, `report_id`, `title`, `description`, `price`) VALUES ('1', '1', 'Fodunderlag', 'underlaget ved højre passagersæde mangler', '500');
INSERT INTO `deficiencies` (`deficiency_id`, `report_id`, `title`, `description`, `price`) VALUES ('2', '1', 'Kopholder', 'Kopholder ved føresæde mangler', '500');

CREATE TABLE `injuries` (
  `injury_id` int(11) NOT NULL AUTO_INCREMENT,
  `report_id` int(11) NOT NULL,
  `title` varchar(45) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `price` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`injury_id`),
  KEY `injury_report_id_idx` (`report_id`),
  CONSTRAINT `injury_report_id` FOREIGN KEY (`report_id`) REFERENCES `survey_reports` (`report_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
INSERT INTO `injuries` (`injury_id`, `report_id`, `title`, `description`, `price`) VALUES ('1', '1injuries', 'flækket rude', 'Bagruden er flæket i højre side, kræver udskiftning', '2000');


