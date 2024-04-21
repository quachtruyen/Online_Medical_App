-- MySQL dump 10.13  Distrib 8.0.27, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: f0athome
-- ------------------------------------------------------
-- Server version	8.0.27

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `assignment`
--

DROP TABLE IF EXISTS `assignment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `assignment` (
  `id` bigint NOT NULL,
  `doctor_id` bigint NOT NULL,
  `patient_id` bigint NOT NULL,
  `admin_id` bigint NOT NULL,
  `assign_date` timestamp NOT NULL,
  `patient_status` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `ffff_idx` (`doctor_id`),
  KEY `admin_fk_idx` (`admin_id`),
  KEY `patient_fk_idx` (`patient_id`),
  CONSTRAINT `admin_assigment_fk` FOREIGN KEY (`admin_id`) REFERENCES `user` (`id`),
  CONSTRAINT `doctor_assigment_fk` FOREIGN KEY (`doctor_id`) REFERENCES `doctor` (`id`),
  CONSTRAINT `patient_assigment_fk` FOREIGN KEY (`patient_id`) REFERENCES `patient` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `assignment`
--

LOCK TABLES `assignment` WRITE;
/*!40000 ALTER TABLE `assignment` DISABLE KEYS */;
INSERT INTO `assignment` VALUES (1,1,1,1,'2022-07-27 08:25:19','Không khỏe'),(2,1,1,1,'2022-07-27 08:25:19','Khỏe'),(3,1,1,1,'2022-07-27 08:25:19','Khỏe'),(4,1,1,1,'2022-07-27 08:25:19','Không khỏe'),(5,1,1,1,'2022-07-27 08:25:19','Khỏe');
/*!40000 ALTER TABLE `assignment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `authority`
--

DROP TABLE IF EXISTS `authority`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `authority` (
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `authority`
--

LOCK TABLES `authority` WRITE;
/*!40000 ALTER TABLE `authority` DISABLE KEYS */;
INSERT INTO `authority` VALUES ('ROLE_ADMIN'),('ROLE_DOCTOR'),('ROLE_PATIENT'),('ROLE_USER');
/*!40000 ALTER TABLE `authority` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `id` bigint NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'Những triệu chứng của bệnh nhân F0'),(2,'Cách xử lý khi có bệnh nhân F0 tại cộng đồng'),(3,'Cách xử lý khi có F0 tại doanh nghiệp'),(4,'Hướng dẫn bệnh nhân F0 tự cách ly tại nhà'),(5,'Hướng dẫn các bước xử lý dành cho bệnh nhân F0 cách ly tại nhà');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `daily_health_status`
--

DROP TABLE IF EXISTS `daily_health_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `daily_health_status` (
  `id` bigint NOT NULL,
  `patient_id` bigint NOT NULL,
  `date` datetime DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `health_status` varchar(255) NOT NULL,
  `symptoms` varchar(255) NOT NULL,
  `notes` text,
  PRIMARY KEY (`id`),
  KEY `patient_fk_idx` (`patient_id`),
  CONSTRAINT `daily_health_status_patient_fk` FOREIGN KEY (`patient_id`) REFERENCES `patient` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `daily_health_status`
--

LOCK TABLES `daily_health_status` WRITE;
/*!40000 ALTER TABLE `daily_health_status` DISABLE KEYS */;
INSERT INTO `daily_health_status` VALUES (1,1,'2022-07-27 15:25:19','2022-07-27 08:25:19','2022-07-27 08:25:19','Có bệnh','Ho - Sốt','Muốn khỏi bệnh hãy xem \"Nhà tôi 3 đời trị covid\"'),(2,1,'2022-07-27 15:25:19','2022-07-27 08:25:19','2022-07-27 08:25:19','Không bị bệnh','Ho bình thường','Lên youtube xem \"Nhà tôi 3 đời ... \"'),(3,1,'2022-07-27 15:25:19','2022-07-27 08:25:19','2022-07-27 08:25:19','Có bệnh','Ho - Khó thở','Tự mà trị khỏi hỏi làm gì'),(4,1,'2022-07-27 15:25:19','2022-07-27 08:25:19','2022-07-27 08:25:19','Có bệnh','Ho - sốt - khó thở','Các cụ vẫy gọi rồi');
/*!40000 ALTER TABLE `daily_health_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `databasechangelog`
--

DROP TABLE IF EXISTS `databasechangelog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `databasechangelog` (
  `ID` varchar(255) NOT NULL,
  `AUTHOR` varchar(255) NOT NULL,
  `FILENAME` varchar(255) NOT NULL,
  `DATEEXECUTED` datetime NOT NULL,
  `ORDEREXECUTED` int NOT NULL,
  `EXECTYPE` varchar(10) NOT NULL,
  `MD5SUM` varchar(35) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `COMMENTS` varchar(255) DEFAULT NULL,
  `TAG` varchar(255) DEFAULT NULL,
  `LIQUIBASE` varchar(20) DEFAULT NULL,
  `CONTEXTS` varchar(255) DEFAULT NULL,
  `LABELS` varchar(255) DEFAULT NULL,
  `DEPLOYMENT_ID` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `databasechangelog`
--

LOCK TABLES `databasechangelog` WRITE;
/*!40000 ALTER TABLE `databasechangelog` DISABLE KEYS */;
INSERT INTO `databasechangelog` VALUES ('00000000000001','jhipster','config/liquibase/changelog/00000000000000_initial_schema.xml','2022-06-05 18:03:54',1,'EXECUTED','8:1a5f32270e664dee65256c5fff5c0e33','createTable tableName=jhi_user; createTable tableName=jhi_authority; createTable tableName=jhi_user_authority; addPrimaryKey tableName=jhi_user_authority; addForeignKeyConstraint baseTableName=jhi_user_authority, constraintName=fk_authority_name, ...','',NULL,'4.3.5',NULL,NULL,'4427033925');
/*!40000 ALTER TABLE `databasechangelog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `databasechangeloglock`
--

DROP TABLE IF EXISTS `databasechangeloglock`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `databasechangeloglock` (
  `ID` int NOT NULL,
  `LOCKED` bit(1) NOT NULL,
  `LOCKGRANTED` datetime DEFAULT NULL,
  `LOCKEDBY` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `databasechangeloglock`
--

LOCK TABLES `databasechangeloglock` WRITE;
/*!40000 ALTER TABLE `databasechangeloglock` DISABLE KEYS */;
INSERT INTO `databasechangeloglock` VALUES (1,_binary '','2022-07-16 16:10:57','Khanh-Iker-Laptop (192.168.1.105)');
/*!40000 ALTER TABLE `databasechangeloglock` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `doctor`
--

DROP TABLE IF EXISTS `doctor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `doctor` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `position` varchar(255) DEFAULT NULL,
  `hospital` varchar(255) DEFAULT NULL,
  `specialize` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id_FK_idx` (`user_id`),
  CONSTRAINT `user_id_doctor_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `doctor`
--

LOCK TABLES `doctor` WRITE;
/*!40000 ALTER TABLE `doctor` DISABLE KEYS */;
INSERT INTO `doctor` VALUES (1,9,'Bác sĩ','E','Good');
/*!40000 ALTER TABLE `doctor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `doctor_action`
--

DROP TABLE IF EXISTS `doctor_action`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `doctor_action` (
  `id` bigint NOT NULL,
  `doctor_id` bigint NOT NULL,
  `daily_health_status_id` bigint NOT NULL,
  `prescription_id` bigint NOT NULL,
  `advise` text,
  `created_at` timestamp NOT NULL,
  `updated_at` timestamp NOT NULL,
  `note` text,
  PRIMARY KEY (`id`),
  KEY `doctor_fk_idx` (`doctor_id`),
  KEY `daily_health_status_id_idx` (`daily_health_status_id`),
  KEY `prescription_fk_idx` (`prescription_id`),
  CONSTRAINT `daily_health_status_doctor_action_fk` FOREIGN KEY (`daily_health_status_id`) REFERENCES `daily_health_status` (`id`),
  CONSTRAINT `doctor_doctor_action_fk` FOREIGN KEY (`doctor_id`) REFERENCES `doctor` (`id`),
  CONSTRAINT `prescription_doctor_action_fk` FOREIGN KEY (`prescription_id`) REFERENCES `prescription` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `doctor_action`
--

LOCK TABLES `doctor_action` WRITE;
/*!40000 ALTER TABLE `doctor_action` DISABLE KEYS */;
INSERT INTO `doctor_action` VALUES (1,1,1,1,'Uống thuốc ít thôi','2022-07-27 08:25:19','2022-07-27 08:25:19','Thuốc than gì tầm này'),(2,1,2,1,'Thuốc than gì tầm này','2022-07-27 08:25:19','2022-07-27 08:25:19','Xem nhà tôi 3 đời đi'),(3,1,1,1,'Xem youtube đi','2022-07-27 08:25:19','2022-07-27 08:25:19','Nhà tôi 3 đời trị covid');
/*!40000 ALTER TABLE `doctor_action` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `medicine`
--

DROP TABLE IF EXISTS `medicine`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `medicine` (
  `id` bigint NOT NULL,
  `name` varchar(255) NOT NULL,
  `origin` varchar(255) NOT NULL,
  `element` varchar(255) NOT NULL,
  `uses` varchar(255) NOT NULL,
  `price` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `medicine`
--

LOCK TABLES `medicine` WRITE;
/*!40000 ALTER TABLE `medicine` DISABLE KEYS */;
INSERT INTO `medicine` VALUES (1,'Thuốc ho','Nhà tôi 3 đời','không cần biết','Trị ho do ngứa mồm',1000000),(2,'Thuốc gì gì đó','You tu be','biết làm gì','Trị ngứa đòn',2000000),(3,'Thuốc trị buồn gnur','không biết','chả ai biết','Ngủ đi rồi biết',1000000000),(4,'Thuốc ngứa mồm','biết làm sao được','ngủ thì biết','mơ đê',1111111111);
/*!40000 ALTER TABLE `medicine` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `medicine_prescription`
--

DROP TABLE IF EXISTS `medicine_prescription`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `medicine_prescription` (
  `id` bigint NOT NULL,
  `medicine_id` bigint NOT NULL,
  `prescription_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `medicine_prescription_medicine_id_fk_idx` (`medicine_id`),
  KEY `medicine_prescription_prescription_id_fk_idx` (`prescription_id`),
  CONSTRAINT `medicine_prescription_medicine_id_fk` FOREIGN KEY (`medicine_id`) REFERENCES `medicine` (`id`),
  CONSTRAINT `medicine_prescription_prescription_id_fk` FOREIGN KEY (`prescription_id`) REFERENCES `prescription` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `medicine_prescription`
--

LOCK TABLES `medicine_prescription` WRITE;
/*!40000 ALTER TABLE `medicine_prescription` DISABLE KEYS */;
INSERT INTO `medicine_prescription` VALUES (1,1,1),(2,2,2);
/*!40000 ALTER TABLE `medicine_prescription` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notification`
--

DROP TABLE IF EXISTS `notification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notification` (
  `id` bigint NOT NULL,
  `from_user_id` bigint NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `title` text,
  `content` text,
  PRIMARY KEY (`id`),
  KEY `notification_user_id_fk_idx` (`from_user_id`),
  CONSTRAINT `notification_user_id_fk` FOREIGN KEY (`from_user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notification`
--

LOCK TABLES `notification` WRITE;
/*!40000 ALTER TABLE `notification` DISABLE KEYS */;
INSERT INTO `notification` VALUES (1,10,'2022-07-27 08:25:19','Không có gì','A đù');
/*!40000 ALTER TABLE `notification` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `patient`
--

DROP TABLE IF EXISTS `patient`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `patient` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `health_status` varchar(255) DEFAULT NULL,
  `disease_symptoms` varchar(255) DEFAULT NULL,
  `prescription` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id_FK_idx` (`user_id`),
  CONSTRAINT `user_id_patient_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patient`
--

LOCK TABLES `patient` WRITE;
/*!40000 ALTER TABLE `patient` DISABLE KEYS */;
INSERT INTO `patient` VALUES (1,10,'Hà Nội','Có bệnh','Ho - Sốt ','Nhà tôi 3 đời trị Covid');
/*!40000 ALTER TABLE `patient` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `post`
--

DROP TABLE IF EXISTS `post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `post` (
  `id` bigint NOT NULL,
  `doctor_id` bigint NOT NULL,
  `title` varchar(255) NOT NULL,
  `content` text NOT NULL,
  `created_by` varchar(255) NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `post_doctor_id_fk_idx` (`doctor_id`),
  CONSTRAINT `post_doctor_id_fk` FOREIGN KEY (`doctor_id`) REFERENCES `doctor` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post`
--

LOCK TABLES `post` WRITE;
/*!40000 ALTER TABLE `post` DISABLE KEYS */;
INSERT INTO `post` VALUES (1,1,'Xem you tu be tự khác khỏi bệnh','Nhà tôi 3 đời','Doc tor','2022-07-27 08:25:19','1092831274182'),(2,1,'3 đời chữa','không cần chữa','Doctor','2022-07-27 08:25:19','123123123123123123');
/*!40000 ALTER TABLE `post` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `post_category`
--

DROP TABLE IF EXISTS `post_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `post_category` (
  `id` bigint NOT NULL,
  `id_post` bigint NOT NULL,
  `id_category` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `post_category_post_id_fk_idx` (`id_post`),
  KEY `post_category_category_id_fk_idx` (`id_category`),
  CONSTRAINT `post_category_category_id_fk` FOREIGN KEY (`id_category`) REFERENCES `category` (`id`),
  CONSTRAINT `post_category_post_id_fk` FOREIGN KEY (`id_post`) REFERENCES `post` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post_category`
--

LOCK TABLES `post_category` WRITE;
/*!40000 ALTER TABLE `post_category` DISABLE KEYS */;
INSERT INTO `post_category` VALUES (1,1,1),(2,2,2);
/*!40000 ALTER TABLE `post_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prescription`
--

DROP TABLE IF EXISTS `prescription`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `prescription` (
  `id` bigint NOT NULL,
  `doctor_id` bigint NOT NULL,
  `created_at` timestamp NOT NULL,
  PRIMARY KEY (`id`),
  KEY `prescription_doctor_id_fk_idx` (`doctor_id`),
  CONSTRAINT `prescription_doctor_id_fk` FOREIGN KEY (`doctor_id`) REFERENCES `doctor` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prescription`
--

LOCK TABLES `prescription` WRITE;
/*!40000 ALTER TABLE `prescription` DISABLE KEYS */;
INSERT INTO `prescription` VALUES (1,1,'2022-07-27 08:25:19'),(2,1,'2022-07-27 08:25:19'),(3,1,'2022-07-27 08:25:19');
/*!40000 ALTER TABLE `prescription` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `login` varchar(50) NOT NULL,
  `password_hash` varchar(60) NOT NULL,
  `first_name` varchar(50) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `email` varchar(191) DEFAULT NULL,
  `image_url` varchar(256) DEFAULT NULL,
  `activated` bit(1) NOT NULL,
  `lang_key` varchar(10) DEFAULT NULL,
  `activation_key` varchar(20) DEFAULT NULL,
  `reset_key` varchar(20) DEFAULT NULL,
  `created_by` varchar(50) NOT NULL,
  `created_date` timestamp NULL,
  `reset_date` timestamp NULL DEFAULT NULL,
  `last_modified_by` varchar(50) DEFAULT NULL,
  `last_modified_date` timestamp NULL DEFAULT NULL,
  `gender` varchar(45) DEFAULT NULL,
  `phone_number` varchar(45) DEFAULT NULL,
  `dob` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_user_login` (`login`),
  UNIQUE KEY `ux_user_email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'admin','$2a$10$6EWf4YGMbUTOm7ggxp3YDurqPaRZMBnTX8UkNQYLYyHHi1d1OY5ku','Vũ Đặng','Bảo Khánh','khanhiker99@gmail.com','',_binary '','vi',NULL,NULL,'system',NULL,NULL,'admin','2022-07-31 00:22:37','GAY',NULL,NULL),(9,'doctor','$2a$10$3CEhwl2x8uN5TEh6CNZuKO93poiyPlgQQGW5OCLSAStsumlPItqQm','khánh','vũ','khanhiker9x@gmail.com',NULL,_binary '','vi',NULL,NULL,'admin','2022-07-27 08:24:27',NULL,'anonymousUser','2022-07-30 20:26:27',NULL,NULL,NULL),(10,'patient','$2a$10$BvgUPL7/K3VMh9RqvuRPUOWAnIlJ3yHsk5oHm0XnrDssGlMz3YE/K',NULL,NULL,'khanhiker98@gmail.com',NULL,_binary '','vi',NULL,NULL,'anonymousUser','2022-07-27 08:25:19',NULL,'anonymousUser','2022-07-27 08:26:13',NULL,NULL,NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_authority`
--

DROP TABLE IF EXISTS `user_authority`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_authority` (
  `user_id` bigint NOT NULL,
  `authority_name` varchar(50) NOT NULL,
  PRIMARY KEY (`user_id`,`authority_name`),
  KEY `fk_authority_name` (`authority_name`),
  CONSTRAINT `fk_authority_name` FOREIGN KEY (`authority_name`) REFERENCES `authority` (`name`),
  CONSTRAINT `fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_authority`
--

LOCK TABLES `user_authority` WRITE;
/*!40000 ALTER TABLE `user_authority` DISABLE KEYS */;
INSERT INTO `user_authority` VALUES (1,'ROLE_ADMIN'),(9,'ROLE_DOCTOR'),(10,'ROLE_PATIENT');
/*!40000 ALTER TABLE `user_authority` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_notification`
--

DROP TABLE IF EXISTS `user_notification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_notification` (
  `id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  `notification_id` bigint NOT NULL,
  `created_at` timestamp NOT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_notification_user_id_fk_idx` (`user_id`),
  KEY `user_notification_notification_id_fk_idx` (`notification_id`),
  CONSTRAINT `user_notification_notification_id_fk` FOREIGN KEY (`notification_id`) REFERENCES `notification` (`id`),
  CONSTRAINT `user_notification_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_notification`
--

LOCK TABLES `user_notification` WRITE;
/*!40000 ALTER TABLE `user_notification` DISABLE KEYS */;
INSERT INTO `user_notification` VALUES (1,10,1,'2022-07-27 08:25:19','Không có gì');
/*!40000 ALTER TABLE `user_notification` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-08-24 21:21:32