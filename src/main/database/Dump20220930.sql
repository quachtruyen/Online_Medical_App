-- MySQL dump 10.13  Distrib 5.7.37, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: F0AtHome
-- ------------------------------------------------------
-- Server version	5.7.37

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `DATABASECHANGELOG`
--

DROP TABLE IF EXISTS `DATABASECHANGELOG`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DATABASECHANGELOG` (
  `ID` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `AUTHOR` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `FILENAME` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `DATEEXECUTED` datetime NOT NULL,
  `ORDEREXECUTED` int(11) NOT NULL,
  `EXECTYPE` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL,
  `MD5SUM` varchar(35) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `DESCRIPTION` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `COMMENTS` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `TAG` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `LIQUIBASE` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `CONTEXTS` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `LABELS` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `DEPLOYMENT_ID` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DATABASECHANGELOG`
--

LOCK TABLES `DATABASECHANGELOG` WRITE;
/*!40000 ALTER TABLE `DATABASECHANGELOG` DISABLE KEYS */;
/*!40000 ALTER TABLE `DATABASECHANGELOG` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `DATABASECHANGELOGLOCK`
--

DROP TABLE IF EXISTS `DATABASECHANGELOGLOCK`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DATABASECHANGELOGLOCK` (
  `ID` int(11) NOT NULL,
  `LOCKED` bit(1) NOT NULL,
  `LOCKGRANTED` datetime DEFAULT NULL,
  `LOCKEDBY` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DATABASECHANGELOGLOCK`
--

LOCK TABLES `DATABASECHANGELOGLOCK` WRITE;
/*!40000 ALTER TABLE `DATABASECHANGELOGLOCK` DISABLE KEYS */;
INSERT INTO `DATABASECHANGELOGLOCK` VALUES (1,_binary '','2022-09-03 11:53:26','192.168.0.113 (192.168.0.113)');
/*!40000 ALTER TABLE `DATABASECHANGELOGLOCK` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `assignment`
--

DROP TABLE IF EXISTS `assignment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `assignment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `doctor_id` bigint(20) NOT NULL,
  `patient_id` bigint(20) NOT NULL,
  `admin_id` bigint(20) DEFAULT NULL,
  `assign_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `patient_status` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `ffff_idx` (`doctor_id`),
  KEY `admin_fk_idx` (`admin_id`),
  KEY `patient_fk_idx` (`patient_id`),
  CONSTRAINT `admin_assigment_fk` FOREIGN KEY (`admin_id`) REFERENCES `user` (`id`),
  CONSTRAINT `doctor_assigment_fk` FOREIGN KEY (`doctor_id`) REFERENCES `doctor` (`id`),
  CONSTRAINT `patient_assigment_fk` FOREIGN KEY (`patient_id`) REFERENCES `patient` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `assignment`
--

LOCK TABLES `assignment` WRITE;
/*!40000 ALTER TABLE `assignment` DISABLE KEYS */;
INSERT INTO `assignment` VALUES (4,1,2,NULL,'2022-09-03 17:07:03','ACCEPT'),(5,1,1,NULL,'2022-09-03 20:50:40','ACCEPT'),(13,1,5,NULL,'2022-09-29 10:20:01','ACCEPT'),(14,1,5,NULL,'2022-09-29 10:20:01','ACCEPT');
/*!40000 ALTER TABLE `assignment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `authority`
--

DROP TABLE IF EXISTS `authority`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `authority` (
  `name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
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
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'Những triệu chứng của bệnh nhân F0'),(2,'Cách xử lý khi có bệnh nhân F0 tại cộng đồng'),(3,'Cách xử lý khi có F0 tại doanh nghiệp'),(4,'Hướng dẫn bệnh nhân F0 tự cách ly tại nhà'),(5,'Hướng dẫn các bước xử lý dành cho bệnh nhân F0 cách ly tại nhà'),(6,'Hehe');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `daily_health_status`
--

DROP TABLE IF EXISTS `daily_health_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `daily_health_status` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `patient_id` bigint(20) NOT NULL,
  `date` timestamp NULL DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `health_status` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `symptoms` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `notes` text COLLATE utf8mb4_unicode_ci,
  PRIMARY KEY (`id`),
  KEY `patient_fk_idx` (`patient_id`),
  CONSTRAINT `daily_health_status_patient_fk` FOREIGN KEY (`patient_id`) REFERENCES `patient` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `daily_health_status`
--

LOCK TABLES `daily_health_status` WRITE;
/*!40000 ALTER TABLE `daily_health_status` DISABLE KEYS */;
INSERT INTO `daily_health_status` VALUES (1,1,'2022-07-27 08:25:19','2022-07-27 08:25:19','2022-07-27 08:25:19','normal','Ho - Sốt','Muốn khỏi bệnh hãy xem \"Nhà tôi 3 đời trị covid\"'),(2,1,'2022-07-27 08:25:19','2022-07-27 08:25:19','2022-07-27 08:25:19','light','Ho bình thường','Lên youtube xem \"Nhà tôi 3 đời ... \"'),(3,1,'2022-07-27 08:25:19','2022-07-27 08:25:19','2022-07-27 08:25:19','emergency','Ho - Khó thở','Tự mà trị khỏi hỏi làm gì'),(4,1,'2022-07-27 08:25:19','2022-07-27 08:25:19','2022-07-27 08:25:19','critical','Ho - sốt - khó thở','Các cụ vẫy gọi rồi'),(5,5,'2022-09-17 10:04:29','2022-09-17 09:04:30','2022-09-19 09:04:30','normal','fdfd','fdf'),(6,5,'2022-09-19 09:28:35','2022-09-19 09:28:38','2022-09-19 09:28:38','emergency','dfgfdgfd','gfgd'),(7,5,'2022-09-19 09:29:15','2022-09-19 09:29:15','2022-09-19 09:29:15','critical','dfdsfsdf','fdf'),(8,5,'2022-09-20 07:51:02','2022-09-20 07:51:02','2022-09-20 07:51:02','normal','Không có gì','Ổn rồi ạ '),(9,5,'2022-09-22 08:27:19','2022-09-22 08:27:19','2022-09-22 08:27:19','light','Ho, Khó thở','Tốt rồi bác sĩ'),(10,5,'2022-09-23 09:39:43','2022-09-23 09:39:43','2022-09-23 09:39:43','light','Đau buồn','Toud'),(11,5,'2022-09-24 22:11:18','2022-09-24 22:11:18','2022-09-24 22:11:18','emergency','Huuu','Ko muốn update nữa'),(12,5,'2022-09-29 09:40:32','2022-09-29 09:40:32','2022-09-29 09:40:32','normal','Đau buồn','dsds'),(13,5,'2022-09-29 09:41:19','2022-09-29 09:41:19','2022-09-29 09:41:19','normal','Đau buồn','dsds'),(14,5,'2022-09-29 09:43:14','2022-09-29 09:43:14','2022-09-29 09:43:14','light','fdfd','ddddd'),(15,5,'2022-09-29 10:13:45','2022-09-29 10:13:45','2022-09-29 10:13:45','normal','fsdfdsf','fdsdsf');
/*!40000 ALTER TABLE `daily_health_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `databasechangelog`
--

DROP TABLE IF EXISTS `databasechangelog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `databasechangelog` (
  `ID` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `AUTHOR` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `FILENAME` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `DATEEXECUTED` datetime NOT NULL,
  `ORDEREXECUTED` int(11) NOT NULL,
  `EXECTYPE` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL,
  `MD5SUM` varchar(35) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `DESCRIPTION` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `COMMENTS` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `TAG` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `LIQUIBASE` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `CONTEXTS` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `LABELS` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `DEPLOYMENT_ID` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL
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
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `databasechangeloglock` (
  `ID` int(11) NOT NULL,
  `LOCKED` bit(1) NOT NULL,
  `LOCKGRANTED` datetime DEFAULT NULL,
  `LOCKEDBY` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
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
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `doctor` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `position` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `hospital` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `specialize` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id_FK_idx` (`user_id`),
  CONSTRAINT `user_id_doctor_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `doctor`
--

LOCK TABLES `doctor` WRITE;
/*!40000 ALTER TABLE `doctor` DISABLE KEYS */;
INSERT INTO `doctor` VALUES (1,9,'Bác sĩ','E','Good'),(2,12,NULL,NULL,NULL);
/*!40000 ALTER TABLE `doctor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `doctor_action`
--

DROP TABLE IF EXISTS `doctor_action`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `doctor_action` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `doctor_id` bigint(20) NOT NULL,
  `daily_health_status_id` bigint(20) NOT NULL,
  `prescription_id` bigint(20) NOT NULL,
  `advise` text COLLATE utf8mb4_unicode_ci,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL,
  `note` text COLLATE utf8mb4_unicode_ci,
  PRIMARY KEY (`id`),
  KEY `doctor_fk_idx` (`doctor_id`),
  KEY `daily_health_status_id_idx` (`daily_health_status_id`),
  KEY `prescription_fk_idx` (`prescription_id`),
  CONSTRAINT `doctor_doctor_action_fk` FOREIGN KEY (`doctor_id`) REFERENCES `doctor` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `doctor_action`
--

LOCK TABLES `doctor_action` WRITE;
/*!40000 ALTER TABLE `doctor_action` DISABLE KEYS */;
INSERT INTO `doctor_action` VALUES (1,1,1,1,'Uống thuốc ít thôi','2022-07-27 08:25:19','2022-07-27 08:25:19','Thuốc than gì tầm này'),(2,1,2,1,'Thuốc than gì tầm này','2022-07-27 08:25:19','2022-07-27 08:25:19','Xem nhà tôi 3 đời đi'),(3,1,1,1,'Xem youtube đi','2022-07-27 08:25:19','2022-07-27 08:25:19','Nhà tôi 3 đời trị covid'),(4,1,8,12,'dsd','2022-09-20 17:16:46','2022-09-14 09:41:45','dsdsd'),(5,1,2,13,'11','2022-09-25 01:14:43','2022-09-25 01:14:43',NULL),(6,1,1,14,'Đừng tin bác sĩ Khánh','2022-09-25 03:18:02','2022-09-25 03:18:02',NULL),(7,1,1,15,'vvvvvvv','2022-09-25 03:21:50','2022-09-25 03:21:50',NULL),(8,1,1,21,'dsd','2022-09-25 03:33:28','2022-09-25 03:33:28',NULL),(9,1,11,22,'Cố gắng lên','2022-09-28 09:39:38','2022-09-28 09:39:38',NULL),(10,1,11,24,'Tao kê nhầm thuốc','2022-09-28 09:41:52','2022-09-28 09:41:52',NULL),(11,1,11,25,'aa','2022-09-28 09:43:15','2022-09-28 09:43:15',NULL),(12,1,14,26,'fgdgfdfgdfgfd','2022-09-29 10:11:46','2022-09-29 10:11:46',NULL);
/*!40000 ALTER TABLE `doctor_action` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `medicine`
--

DROP TABLE IF EXISTS `medicine`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `medicine` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `origin` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `element` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `uses` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `price` int(11) NOT NULL,
  `deleted` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `medicine`
--

LOCK TABLES `medicine` WRITE;
/*!40000 ALTER TABLE `medicine` DISABLE KEYS */;
INSERT INTO `medicine` VALUES (1,'Thuốc ho','Nhà tôi 3 đời','không cần biết','Trị ho do ngứa mồm',1000000,1),(2,'Thuốc gì gì đó','You tu be','biết làm gì','Trị ngứa đòn',2000000,1),(3,'Thuốc trị buồn gnur','không biết','chả ai biết','Ngủ đi rồi biết',1000000000,0),(4,'Thuốc ngứa mồm','biết làm sao được','ngủ thì biết','mơ đê',1111111111,0),(5,'aa','dsd','dsddsdsd','dsd',333,0);
/*!40000 ALTER TABLE `medicine` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `medicine_prescription`
--

DROP TABLE IF EXISTS `medicine_prescription`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `medicine_prescription` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `medicine_id` bigint(20) NOT NULL,
  `prescription_id` bigint(20) NOT NULL,
  `amount` int(11) NOT NULL,
  `price` bigint(20) DEFAULT NULL,
  `description` text COLLATE utf8mb4_unicode_ci,
  PRIMARY KEY (`id`),
  KEY `medicine_prescription_medicine_id_fk_idx` (`medicine_id`),
  KEY `medicine_prescription_prescription_id_fk_idx` (`prescription_id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `medicine_prescription`
--

LOCK TABLES `medicine_prescription` WRITE;
/*!40000 ALTER TABLE `medicine_prescription` DISABLE KEYS */;
INSERT INTO `medicine_prescription` VALUES (1,1,1,1,10000,NULL),(2,2,2,2,10000,NULL),(3,4,5,3,10000,''),(4,4,6,1,10000,''),(5,4,7,1,10000,''),(6,4,8,1,230000,''),(7,4,9,1,10000,''),(8,4,10,1,10000,''),(9,4,11,2,10000,''),(10,4,12,1,10000,''),(11,4,13,1,1111111111,''),(12,2,13,1,2000000,''),(13,4,14,1,1111111111,''),(14,2,14,0,2000000,''),(15,4,15,1,1111111111,'12'),(16,2,15,0,2000000,''),(17,4,17,1,1111111111,'11'),(18,4,18,2,1111111111,'1'),(19,4,20,1,1111111111,'aaa'),(20,4,21,2,1111111111,'1'),(21,2,21,2,2000000,'ffff'),(22,4,22,123,1111111111,'Uống vừa phải'),(23,5,24,1,333,'1'),(24,5,25,1,333,'dsada'),(25,5,26,1,333,'');
/*!40000 ALTER TABLE `medicine_prescription` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notification`
--

DROP TABLE IF EXISTS `notification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `notification` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `from_user_id` bigint(20) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `title` text COLLATE utf8mb4_unicode_ci,
  `content` text COLLATE utf8mb4_unicode_ci,
  `url` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `notification_user_id_fk_idx` (`from_user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notification`
--

LOCK TABLES `notification` WRITE;
/*!40000 ALTER TABLE `notification` DISABLE KEYS */;
INSERT INTO `notification` VALUES (1,10,'2022-07-27 08:25:19','Không có gì','A đù',NULL),(2,10,'2022-09-10 17:32:59','Test','AAA',NULL),(3,10,'2022-09-12 16:49:58','AAA','CCC',NULL),(4,1,'2022-09-28 06:40:07','Thông báo xóa chỉ định','Bạn bị xóa chỉ định chăm sóc cho bệnh nhân 15 patient4 patient4',NULL),(5,1,'2022-09-28 06:40:15','Thông báo xóa chỉ định','Bạn đã bị xóa chỉ định được chăm sóc bởi bác sĩ 9 khánh vũ',NULL),(6,1,'2022-09-28 06:41:58','Thông báo chỉ định','Bạn đã được chỉ định chăm sóc cho bệnh nhân 15 patient4 patient4',NULL),(7,1,'2022-09-28 06:42:00','Thông báo chỉ định','Bạn đã được chỉ định được chăm sóc bởi bác sĩ 9 khánh vũ',NULL),(8,1,'2022-09-28 08:53:01','Thông báo xóa chỉ định','Bạn bị xóa chỉ định chăm sóc cho bệnh nhân 15 patient4 patient4',NULL),(9,1,'2022-09-28 08:53:45','Thông báo xóa chỉ định','Bạn đã bị xóa chỉ định được chăm sóc bởi bác sĩ 9 khánh vũ',NULL),(10,1,'2022-09-28 08:56:49','Thông báo chỉ định','Bạn đã được chỉ định chăm sóc cho bệnh nhân 15 patient4 patient4',NULL),(11,1,'2022-09-28 08:56:52','Thông báo chỉ định','Bạn đã được chỉ định được chăm sóc bởi bác sĩ 9 khánh vũ',NULL),(12,5,'2022-09-28 09:39:38','Thông báo từ bác sĩ','Trạng thái bệnh ngày 2022-09-25T05:11:18Z của bạn đã được bác sĩ xử lý ',NULL),(13,5,'2022-09-28 09:41:52','Thông báo từ bác sĩ','Trạng thái bệnh ngày 2022-09-25T05:11:18Z của bạn đã được bác sĩ xử lý ',NULL),(14,5,'2022-09-28 09:44:00','Thông báo từ bác sĩ','Trạng thái bệnh ngày 2022-09-25T05:11:18Z của bạn đã được bác sĩ xử lý ',NULL),(15,1,'2022-09-29 09:39:14','Thông báo xóa chỉ định','Bạn bị xóa chỉ định chăm sóc cho bệnh nhân 15 patient4 patient4',NULL),(16,1,'2022-09-29 09:39:17','Thông báo xóa chỉ định','Bạn đã bị xóa chỉ định được chăm sóc bởi bác sĩ 9 khánh vũ',NULL),(17,NULL,'2022-09-29 09:41:32','Thông báo bệnh nhân cập nhật sức khỏe ','Bệnh nhân 15 patient4 patient4 vừa cập nhật trạng thái sức khỏe. Bạn cần chỉ định bác sĩ chăm sóc ngay!',NULL),(18,1,'2022-09-29 09:42:20','Thông báo chỉ định','Bạn đã được chỉ định chăm sóc cho bệnh nhân 15 patient4 patient4',NULL),(19,1,'2022-09-29 09:42:20','Thông báo chỉ định','Bạn đã được chỉ định được chăm sóc bởi bác sĩ 9 khánh vũ',NULL),(20,NULL,'2022-09-29 09:43:28','Thông báo bệnh nhân cập nhật sức khỏe ','Bệnh nhân 15 patient4 patient4 vừa cập nhật trạng thái sức khỏe',NULL),(21,1,'2022-09-29 10:08:28','Thông báo xóa chỉ định','Bạn bị xóa chỉ định chăm sóc cho bệnh nhân 15 patient4 patient4',NULL),(22,1,'2022-09-29 10:08:28','Thông báo xóa chỉ định','Bạn đã bị xóa chỉ định được chăm sóc bởi bác sĩ 9 khánh vũ',NULL),(23,1,'2022-09-29 10:08:41','Thông báo chỉ định','Bạn đã được chỉ định chăm sóc cho bệnh nhân 15 patient4 patient4',NULL),(24,1,'2022-09-29 10:08:41','Thông báo chỉ định','Bạn đã được chỉ định được chăm sóc bởi bác sĩ 9 khánh vũ',NULL),(25,1,'2022-09-29 10:10:44','Thông báo xóa chỉ định','Bạn bị xóa chỉ định chăm sóc cho bệnh nhân 15 patient4 patient4',NULL),(26,1,'2022-09-29 10:10:44','Thông báo xóa chỉ định','Bạn đã bị xóa chỉ định được chăm sóc bởi bác sĩ 9 khánh vũ',NULL),(27,1,'2022-09-29 10:10:53','Thông báo chỉ định','Bạn đã được chỉ định chăm sóc cho bệnh nhân 15 patient4 patient4','/doctor/patient-management/5/view'),(28,1,'2022-09-29 10:10:53','Thông báo chỉ định','Bạn đã được chỉ định được chăm sóc bởi bác sĩ 9 khánh vũ','/doctor/patient-management/5/view'),(29,1,'2022-09-29 10:11:46','Thông báo từ bác sĩ','Trạng thái bệnh ngày 2022-09-29T16:43:14Z của bạn đã được bác sĩ xử lý ','/daily-health-status/14/view'),(30,NULL,'2022-09-29 10:13:49','Thông báo bệnh nhân cập nhật sức khỏe ','Bệnh nhân 15 patient4 patient4 vừa cập nhật trạng thái sức khỏe','/doctor/patient-management/5/view'),(31,1,'2022-09-29 10:18:08','Thông báo xóa chỉ định','Bạn bị xóa chỉ định chăm sóc cho bệnh nhân 15 patient4 patient4',NULL),(32,1,'2022-09-29 10:18:08','Thông báo xóa chỉ định','Bạn đã bị xóa chỉ định được chăm sóc bởi bác sĩ 9 khánh vũ',NULL),(33,1,'2022-09-29 10:18:17','Thông báo chỉ định','Bạn đã được chỉ định chăm sóc cho bệnh nhân 15 patient4 patient4','/doctor/patient-management/5/view'),(34,1,'2022-09-29 10:18:17','Thông báo chỉ định','Bạn đã được chỉ định được chăm sóc bởi bác sĩ 9 khánh vũ','/account/settings'),(35,1,'2022-09-29 10:19:14','Thông báo xóa chỉ định','Bạn bị xóa chỉ định chăm sóc cho bệnh nhân 15 patient4 patient4',NULL),(36,1,'2022-09-29 10:19:14','Thông báo xóa chỉ định','Bạn đã bị xóa chỉ định được chăm sóc bởi bác sĩ 9 khánh vũ',NULL),(37,1,'2022-09-29 10:20:01','Thông báo chỉ định','Bạn đã được chỉ định chăm sóc cho bệnh nhân 15 patient4 patient4','/doctor/patient-management/5/view'),(38,1,'2022-09-29 10:20:01','Thông báo chỉ định','Bạn đã được chỉ định chăm sóc cho bệnh nhân 15 patient4 patient4','/doctor/patient-management/5/view'),(39,1,'2022-09-29 10:20:01','Thông báo chỉ định','Bạn đã được chỉ định được chăm sóc bởi bác sĩ 9 khánh vũ','/account/settings'),(40,1,'2022-09-29 10:20:01','Thông báo chỉ định','Bạn đã được chỉ định được chăm sóc bởi bác sĩ 9 khánh vũ','/account/settings');
/*!40000 ALTER TABLE `notification` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `patient`
--

DROP TABLE IF EXISTS `patient`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `patient` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `address` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `health_status` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `disease_symptoms` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `prescription` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id_FK_idx` (`user_id`),
  CONSTRAINT `user_id_patient_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patient`
--

LOCK TABLES `patient` WRITE;
/*!40000 ALTER TABLE `patient` DISABLE KEYS */;
INSERT INTO `patient` VALUES (1,10,'Hà Nội','Emergency','Ho - Sốt ','Nhà tôi 3 đời trị Covid'),(2,11,NULL,'Emergency',NULL,NULL),(3,13,NULL,'Emergency',NULL,NULL),(4,14,NULL,'Normal',NULL,NULL),(5,15,NULL,'Light',NULL,NULL);
/*!40000 ALTER TABLE `patient` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `post`
--

DROP TABLE IF EXISTS `post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `post` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `content` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_by` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `image_url` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `published` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post`
--

LOCK TABLES `post` WRITE;
/*!40000 ALTER TABLE `post` DISABLE KEYS */;
INSERT INTO `post` VALUES (2,'3 đời chữa','không cần chữa','Doctor','2022-07-27 08:25:19','123123123123123123',0),(3,'1ddadsasdsadsad','Khỏi cần chữa','Doctor','2022-09-27 16:48:52','1111111111',1),(4,'Testtttttttttt','adad','adsad','2022-09-27 16:49:10','dsdsds',1),(5,'Huu dep trai','Hay cho toi 10k toi se cho ban 11k','aaaa','2022-09-27 16:49:59','pidspiqpoi',1),(6,'Truyen Xinh gai','Sẽ luôn thật gần bên em','bbb','2022-09-27 16:50:28',NULL,1),(7,'jhgjhgjk','<p>ghffhghg</p>','admin','2022-09-28 01:32:21',NULL,1),(8,'aaaaaaaa','<p>huu dam khac hu</p><p>&nbsp;</p><p>hdsd</p><p>hasld</p><p>ja;sd</p><p>&nbsp;</p>','admin','2022-09-28 02:07:18',NULL,0);
/*!40000 ALTER TABLE `post` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `post_category`
--

DROP TABLE IF EXISTS `post_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `post_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `id_post` bigint(20) NOT NULL,
  `id_category` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `post_category_id_category_id_post_uindex` (`id_category`,`id_post`),
  KEY `post_category_category_id_fk_idx` (`id_category`),
  KEY `post_category_post_id_fk_idx` (`id_post`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post_category`
--

LOCK TABLES `post_category` WRITE;
/*!40000 ALTER TABLE `post_category` DISABLE KEYS */;
INSERT INTO `post_category` VALUES (7,6,1),(8,7,1),(10,8,1),(5,5,2),(9,7,2),(4,4,3),(2,2,5),(3,3,6);
/*!40000 ALTER TABLE `post_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prescription`
--

DROP TABLE IF EXISTS `prescription`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `prescription` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `doctor_id` bigint(20) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prescription`
--

LOCK TABLES `prescription` WRITE;
/*!40000 ALTER TABLE `prescription` DISABLE KEYS */;
INSERT INTO `prescription` VALUES (1,1,'2022-07-27 08:25:19',NULL),(2,1,'2022-07-27 08:25:19',NULL),(3,1,'2022-07-27 08:25:19',NULL),(4,1,'2022-09-14 09:28:43','2022-09-14 09:28:43'),(5,1,'2022-09-14 09:29:26','2022-09-14 09:29:26'),(6,1,'2022-09-14 09:31:30','2022-09-14 09:31:30'),(7,1,'2022-09-14 09:33:33','2022-09-14 09:33:33'),(8,1,'2022-09-14 09:38:48','2022-09-14 09:38:48'),(9,1,'2022-09-14 09:39:07','2022-09-14 09:39:07'),(10,1,'2022-09-14 09:40:01','2022-09-14 09:40:01'),(11,1,'2022-09-14 09:41:07','2022-09-14 09:41:07'),(12,1,'2022-09-14 09:41:43','2022-09-14 09:41:43'),(13,1,'2022-09-25 01:14:37','2022-09-25 01:14:37'),(14,1,'2022-09-25 03:17:58','2022-09-25 03:17:58'),(15,1,'2022-09-25 03:21:48','2022-09-25 03:21:48'),(16,1,'2022-09-25 03:23:08','2022-09-25 03:23:08'),(17,1,'2022-09-25 03:25:53','2022-09-25 03:25:53'),(18,1,'2022-09-25 03:27:24','2022-09-25 03:27:24'),(19,1,'2022-09-25 03:30:44','2022-09-25 03:30:44'),(20,1,'2022-09-25 03:32:46','2022-09-25 03:32:46'),(21,1,'2022-09-25 03:33:25','2022-09-25 03:33:25'),(22,1,'2022-09-28 09:39:35','2022-09-28 09:39:35'),(23,1,'2022-09-28 09:41:22','2022-09-28 09:41:22'),(24,1,'2022-09-28 09:41:51','2022-09-28 09:41:51'),(25,1,'2022-09-28 09:42:55','2022-09-28 09:42:55'),(26,1,'2022-09-29 10:11:40','2022-09-29 10:11:40');
/*!40000 ALTER TABLE `prescription` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `login` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password_hash` varchar(60) COLLATE utf8mb4_unicode_ci NOT NULL,
  `first_name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `last_name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `email` varchar(191) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `image_url` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `activated` bit(1) NOT NULL,
  `lang_key` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `activation_key` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `reset_key` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_by` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_date` timestamp NULL DEFAULT NULL,
  `reset_date` timestamp NULL DEFAULT NULL,
  `last_modified_by` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `last_modified_date` timestamp NULL DEFAULT NULL,
  `gender` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `phone_number` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `dob` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_user_login` (`login`),
  UNIQUE KEY `ux_user_email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'admin','$2a$10$6EWf4YGMbUTOm7ggxp3YDurqPaRZMBnTX8UkNQYLYyHHi1d1OY5ku','Vũ Đặng','Bảo Khánh','khanhiker99@gmail.com','',_binary '','vi',NULL,NULL,'system',NULL,NULL,'admin','2022-07-31 00:22:37','GAY',NULL,NULL),(9,'doctor','$2a$10$3CEhwl2x8uN5TEh6CNZuKO93poiyPlgQQGW5OCLSAStsumlPItqQm','khánh','vũ','khanhiker9x@gmail.com',NULL,_binary '','vi',NULL,NULL,'admin','2022-07-27 08:24:27',NULL,'anonymousUser','2022-07-30 20:26:27',NULL,'0123452312',NULL),(10,'patient','$2a$10$BvgUPL7/K3VMh9RqvuRPUOWAnIlJ3yHsk5oHm0XnrDssGlMz3YE/K','khánh','vũ','khanhiker98@gmail.com',NULL,_binary '','vi',NULL,NULL,'anonymousUser','2022-07-27 08:25:19',NULL,'anonymousUser','2022-07-27 08:26:13','MALE','0002520254','2001-09-02 14:45:45'),(11,'khachuu','$2a$10$L4EtQijewSll5xpEsl3jUeLzvXQw12dn6aapi8pMYwuJ7bxj0weJW','sssss','sssss',NULL,NULL,_binary '','en',NULL,'EM3WIWCWRF0lunpSu0qr','admin','2022-09-02 21:30:02','2022-09-02 21:30:02','admin','2022-09-02 21:30:02',NULL,NULL,NULL),(12,'doctor1','$2a$10$TPzlSd7hyUPu36nIzphE5Obs9v4yt5NGuwEmrVB08.RMUI38Va.VC','2222','111','huudk@gmail.123',NULL,_binary '','en',NULL,'Nk0uC05008z3VAuc9kUG','admin','2022-09-03 09:09:27','2022-09-03 09:09:27','admin','2022-09-03 09:10:09',NULL,NULL,NULL),(13,'patient2','$2a$10$i0aqBH1M5N7oGkDIJsXtTe6uDVZ2BK.Fs0aCJ56HdXlTQLj24FCDO','patient2','patient2','patient2@gmail.com',NULL,_binary '','en',NULL,'LTiW50txzxq6Aq7uiROW','admin','2022-09-03 09:48:13','2022-09-03 09:48:13','admin','2022-09-03 09:48:56',NULL,NULL,NULL),(14,'patient3','$2a$10$tDgpIZPV//yUKXaroW9eWOs2OszsU.8ZTZubBamj5xcvCQyPr.wpq','patient3','patient3','patient3@gmail.com',NULL,_binary '','en',NULL,'Bw4tqLpxJ6YsKVM2JBR1','admin','2022-09-03 09:58:21','2022-09-03 09:58:21','admin','2022-09-03 09:58:33',NULL,NULL,NULL),(15,'patient4','$2a$10$pO64B.3AtV2EZPDSXPAWwe2ots8nHeVtPhLzHHBRMk0HIK6HUevjS','patient4','patient4','patient4@gmail.com',NULL,_binary '','vi','U83x5umviG084sBkEIGE',NULL,'anonymousUser','2022-09-03 10:00:45',NULL,'admin','2022-09-03 21:02:03',NULL,'0333333333',NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_authority`
--

DROP TABLE IF EXISTS `user_authority`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_authority` (
  `user_id` bigint(20) NOT NULL,
  `authority_name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
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
INSERT INTO `user_authority` VALUES (1,'ROLE_ADMIN'),(9,'ROLE_DOCTOR'),(12,'ROLE_DOCTOR'),(10,'ROLE_PATIENT'),(11,'ROLE_PATIENT'),(13,'ROLE_PATIENT'),(14,'ROLE_PATIENT'),(15,'ROLE_PATIENT');
/*!40000 ALTER TABLE `user_authority` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_notification`
--

DROP TABLE IF EXISTS `user_notification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_notification` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `notification_id` bigint(20) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_notification_user_id_fk_idx` (`user_id`),
  KEY `user_notification_notification_id_fk_idx` (`notification_id`),
  CONSTRAINT `user_notification_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_notification`
--

LOCK TABLES `user_notification` WRITE;
/*!40000 ALTER TABLE `user_notification` DISABLE KEYS */;
INSERT INTO `user_notification` VALUES (1,10,1,'2022-09-08 16:32:58','read'),(2,9,1,'2022-09-10 18:16:53','read'),(3,9,2,'2022-09-10 18:12:12','read'),(19,9,3,'2022-09-13 09:29:25','read'),(20,9,4,'2022-09-28 06:40:12','read'),(21,15,5,'2022-09-28 06:40:16','read'),(22,9,6,'2022-09-28 06:41:59','read'),(23,15,7,'2022-09-28 06:42:00','read'),(24,9,8,'2022-09-28 08:53:44','read'),(25,15,9,'2022-09-28 08:53:46','read'),(26,9,10,'2022-09-28 08:56:49','read'),(27,15,11,'2022-09-28 08:56:52','read'),(28,15,12,'2022-09-28 09:39:38','read'),(29,15,13,'2022-09-28 09:41:53','read'),(30,15,14,'2022-09-28 09:44:01','read'),(31,9,15,'2022-09-29 09:39:14','read'),(32,15,16,'2022-09-29 09:39:17','read'),(33,1,17,'2022-09-29 09:41:47','read'),(34,9,18,'2022-09-29 09:42:20','read'),(35,15,19,'2022-09-29 09:42:20','read'),(36,9,20,'2022-09-29 09:43:28','read'),(37,9,21,'2022-09-29 10:08:28','read'),(38,15,22,'2022-09-29 10:08:28','read'),(39,9,23,'2022-09-29 10:08:41','read'),(40,15,24,'2022-09-29 10:08:41','read'),(41,9,25,'2022-09-29 10:10:44','read'),(42,15,26,'2022-09-29 10:10:44','read'),(43,9,27,'2022-09-29 10:10:53','read'),(44,15,28,'2022-09-29 10:10:53','read'),(45,15,29,'2022-09-29 10:11:46','read'),(46,9,30,'2022-09-29 10:13:49','read'),(47,9,31,'2022-09-29 10:18:08','read'),(48,15,32,'2022-09-29 10:18:08','read'),(49,9,33,'2022-09-29 10:18:17','read'),(50,15,34,'2022-09-29 10:18:17','read'),(51,9,35,'2022-09-29 10:19:14','read'),(52,15,36,'2022-09-29 10:19:14','read'),(53,9,37,'2022-09-29 10:20:01','unread'),(54,9,38,'2022-09-29 10:20:01','read'),(55,15,39,'2022-09-29 10:20:01','unread'),(56,15,40,'2022-09-29 10:20:01','read');
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

-- Dump completed on 2022-09-30  0:22:23
