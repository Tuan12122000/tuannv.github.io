/*
SQLyog Community v13.1.9 (64 bit)
MySQL - 8.0.32 : Database - payment
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`payment` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `payment`;

/*Table structure for table `payment` */

DROP TABLE IF EXISTS `payment`;

CREATE TABLE `payment` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `amount` double NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `time_created` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  `oder_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `payment` */

insert  into `payment`(`id`,`address`,`amount`,`description`,`name`,`time_created`,`user_id`,`oder_id`) values
(1,'Hà Nội',1000000,'Thu giá dịch vụ thu gom, vận chuyển và xử lý rác thải sinh hoạt','Tuan','1696218102038','0',NULL),
(2,'Vĩnh Yên',100000,'Thu giá dịch vụ thu gom, vận chuyển và xử lý rác thải sinh hoạt','Tuan','2023-10-02 17:34:13','3','5554422'),
(3,'Hà Nội',200000,'Thu giá dịch vụ thu gom, vận chuyển và xử lý rác thải sinh hoạt','Tannvc','2023-10-03 10:37:00','3','2');

/*Table structure for table `payments` */

DROP TABLE IF EXISTS `payments`;

CREATE TABLE `payments` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `amount` double NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `time_created` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `payments` */

/*Table structure for table `roles` */

DROP TABLE IF EXISTS `roles`;

CREATE TABLE `roles` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ofx66keruapi6vyqpv6f2or37` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `roles` */

insert  into `roles`(`id`,`name`) values
(1,'ROLE_ADMIN'),
(2,'ROLE_USER');

/*Table structure for table `users` */

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `mobile` int NOT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `type` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_6dotkott2kjsp8vw4d0m25fb7` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `users` */

insert  into `users`(`id`,`email`,`mobile`,`name`,`password`,`type`) values
(1,'admin@gmail.com',327236156,'Dương','$2a$10$Druk2rJk7vDS4lYzTZko0OckGExqu6jR8FUAf/DemFVuIICh3UdCa',1),
(3,'nguyenvantuan1212@gmail.com',398451454,'tuannv','$2a$10$boEkUESIHkUxcGUAe0xYve4j8gITjB6.yP4o/rSiXJuyEfP86skSy',1),
(4,'nvd140313@gmail.com',964462663,'dương','$2a$10$NPbx09UjGvYZk5CH2oHBF.nklIsqTmbjln69RHl3RorWLgegECcp.',1),
(8,'nguyenvantuan1232@gmail.com',327236156,'Nguyễn Văn Tuấn','$2a$10$4Al9ZHUko/o09EuXFDpfzezD6rFR6N2aotoEmJBGhtqjo0VOC34c6',0),
(11,'nvt12122000@gmail.com',327236156,'TuấnNV','$2a$10$d9tqgk3sl.15fmAx6yk2aeqo3fpPTLepzwQVhzWSpeVb/c3NWx1Qa',0);

/*Table structure for table `users_roles` */

DROP TABLE IF EXISTS `users_roles`;

CREATE TABLE `users_roles` (
  `user_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  KEY `FKj6m8fwv7oqv74fcehir1a9ffy` (`role_id`),
  KEY `FK2o0jvgh89lemvvo17cbqvdxaa` (`user_id`),
  CONSTRAINT `FK2o0jvgh89lemvvo17cbqvdxaa` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKj6m8fwv7oqv74fcehir1a9ffy` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `users_roles` */

insert  into `users_roles`(`user_id`,`role_id`) values
(1,1),
(3,2),
(4,2),
(11,2),
(8,2);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
