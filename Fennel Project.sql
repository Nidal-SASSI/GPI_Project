-- MySQL dump 10.13  Distrib 8.0.22, for macos10.15 (x86_64)
--
-- Host: localhost    Database: f_fennel
-- ------------------------------------------------------
-- Server version	8.0.22

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
-- Table structure for table `f_admin`
--

DROP TABLE IF EXISTS `f_admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `f_admin` (
  `ID` bigint NOT NULL,
  `FirstName` varchar(225) DEFAULT NULL,
  `LastName` varchar(225) DEFAULT NULL,
  `Login` varchar(225) DEFAULT NULL,
  `Password` varchar(225) DEFAULT NULL,
  `roleId` bigint DEFAULT NULL,
  `Created_by` varchar(225) DEFAULT NULL,
  `modified_by` varchar(225) DEFAULT NULL,
  `created_datetime` timestamp NULL DEFAULT NULL,
  `modified_DateTime` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `f_admin`
--

LOCK TABLES `f_admin` WRITE;
/*!40000 ALTER TABLE `f_admin` DISABLE KEYS */;
INSERT INTO `f_admin` VALUES (1,'Admin','Admin','Admin123','Admin@123',1,'root','root','2021-03-22 21:31:31','2021-03-22 21:31:33');
/*!40000 ALTER TABLE `f_admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `f_advertise`
--

DROP TABLE IF EXISTS `f_advertise`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `f_advertise` (
  `id` bigint NOT NULL,
  `adv_price` varchar(45) DEFAULT NULL,
  `adv_name` varchar(45) DEFAULT NULL,
  `recipient_adv` varchar(45) DEFAULT NULL,
  `sender` varchar(45) DEFAULT NULL,
  `adv_category` varchar(45) DEFAULT NULL,
  `created_by` varchar(45) DEFAULT NULL,
  `modified_by` varchar(45) DEFAULT NULL,
  `created_datetime` timestamp NULL DEFAULT NULL,
  `modified_datetime` timestamp NULL DEFAULT NULL,
  `Image` longblob,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `f_advertise`
--

LOCK TABLES `f_advertise` WRITE;
/*!40000 ALTER TABLE `f_advertise` DISABLE KEYS */;
/*!40000 ALTER TABLE `f_advertise` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `f_cart`
--

DROP TABLE IF EXISTS `f_cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `f_cart` (
  `ID` bigint NOT NULL,
  `ItemName` varchar(225) DEFAULT NULL,
  `ItemId` bigint DEFAULT NULL,
  `Description` varchar(225) DEFAULT NULL,
  `quantity` varchar(225) DEFAULT NULL,
  `price` varchar(225) DEFAULT NULL,
  `totalPrice` varchar(225) DEFAULT NULL,
  `UserId` bigint DEFAULT NULL,
  `Created_by` varchar(225) DEFAULT NULL,
  `modified_by` varchar(225) DEFAULT NULL,
  `created_datetime` timestamp NULL DEFAULT NULL,
  `modified_datetime` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `f_cart`
--

LOCK TABLES `f_cart` WRITE;
/*!40000 ALTER TABLE `f_cart` DISABLE KEYS */;
/*!40000 ALTER TABLE `f_cart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `f_customer`
--

DROP TABLE IF EXISTS `f_customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `f_customer` (
  `id` bigint NOT NULL,
  `username` varchar(245) DEFAULT NULL,
  `firstname` varchar(245) DEFAULT NULL,
  `surname` varchar(245) DEFAULT NULL,
  `dob` date DEFAULT NULL,
  `prof_social_category` varchar(245) DEFAULT NULL,
  `address` varchar(245) DEFAULT NULL,
  `phoneno` varchar(245) DEFAULT NULL,
  `emailid` varchar(245) DEFAULT NULL,
  `comm_category` varchar(245) DEFAULT NULL,
  `password` varchar(245) DEFAULT NULL,
  `role_id` bigint DEFAULT NULL,
  `created_by` varchar(245) DEFAULT NULL,
  `modified_by` varchar(245) DEFAULT NULL,
  `created_datetime` timestamp NULL DEFAULT NULL,
  `modified_datetime` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `f_customer`
--

LOCK TABLES `f_customer` WRITE;
/*!40000 ALTER TABLE `f_customer` DISABLE KEYS */;
/*!40000 ALTER TABLE `f_customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `f_items`
--

DROP TABLE IF EXISTS `f_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `f_items` (
  `id` bigint NOT NULL,
  `designation` varchar(245) DEFAULT NULL,
  `price` varchar(145) DEFAULT NULL,
  `stock` varchar(145) DEFAULT NULL,
  `quantities` varchar(145) DEFAULT NULL,
  `description` varchar(245) DEFAULT NULL,
  `image` longblob,
  `created_by` varchar(245) DEFAULT NULL,
  `modified_by` varchar(245) DEFAULT NULL,
  `created_datetime` timestamp NULL DEFAULT NULL,
  `modified_datetime` timestamp NULL DEFAULT NULL,
  `category` varchar(225) DEFAULT NULL,
  `Name` varchar(225) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `f_items`
--

LOCK TABLES `f_items` WRITE;
/*!40000 ALTER TABLE `f_items` DISABLE KEYS */;
/*!40000 ALTER TABLE `f_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `f_order`
--

DROP TABLE IF EXISTS `f_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `f_order` (
  `ID` bigint NOT NULL,
  `Time_Slot` date DEFAULT NULL,
  `status` varchar(225) DEFAULT NULL,
  `total` varchar(225) DEFAULT NULL,
  `name` varchar(225) DEFAULT NULL,
  `email` varchar(225) DEFAULT NULL,
  `mobileNo` varchar(225) DEFAULT NULL,
  `address1` varchar(225) DEFAULT NULL,
  `address2` varchar(225) DEFAULT NULL,
  `city` varchar(225) DEFAULT NULL,
  `state` varchar(225) DEFAULT NULL,
  `userId` bigint DEFAULT NULL,
  `created_By` varchar(225) DEFAULT NULL,
  `modified_By` varchar(225) DEFAULT NULL,
  `created_Datetime` timestamp NULL DEFAULT NULL,
  `modified_Datetime` timestamp NULL DEFAULT NULL,
  `ItemId` bigint DEFAULT NULL,
  `ItemName` varchar(225) DEFAULT NULL,
  `Category` varchar(225) DEFAULT NULL,
  `Qunatity` varchar(225) DEFAULT NULL,
  `OrderId` bigint DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `f_order`
--

LOCK TABLES `f_order` WRITE;
/*!40000 ALTER TABLE `f_order` DISABLE KEYS */;
/*!40000 ALTER TABLE `f_order` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-04-10 19:39:42
