CREATE DATABASE  IF NOT EXISTS `instacart` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `instacart`;
-- MySQL dump 10.13  Distrib 8.0.27, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: instacart
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
-- Table structure for table `data_object_type`
--

DROP TABLE IF EXISTS `data_object_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `data_object_type` (
  `_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `data_object_type`
--

LOCK TABLES `data_object_type` WRITE;
/*!40000 ALTER TABLE `data_object_type` DISABLE KEYS */;
INSERT INTO `data_object_type` VALUES (1,'Products'),(2,'Orders'),(3,'Order_Products');
/*!40000 ALTER TABLE `data_object_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `instacart_data_object`
--

DROP TABLE IF EXISTS `instacart_data_object`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `instacart_data_object` (
  `_id` int NOT NULL AUTO_INCREMENT,
  `user_data_id` int NOT NULL,
  `object_type_id` int NOT NULL,
  `bucket_name` varchar(1000) DEFAULT NULL,
  `bucket_key` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`_id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `instacart_data_object`
--

LOCK TABLES `instacart_data_object` WRITE;
/*!40000 ALTER TABLE `instacart_data_object` DISABLE KEYS */;
/*!40000 ALTER TABLE `instacart_data_object` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `instacart_features`
--

DROP TABLE IF EXISTS `instacart_features`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `instacart_features` (
  `_id` int NOT NULL AUTO_INCREMENT,
  `instacart_object_id` int DEFAULT NULL,
  `feature_source` varchar(500) DEFAULT NULL,
  `feature_name` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`_id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `instacart_features`
--

LOCK TABLES `instacart_features` WRITE;
/*!40000 ALTER TABLE `instacart_features` DISABLE KEYS */;
/*!40000 ALTER TABLE `instacart_features` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `segmentation`
--

DROP TABLE IF EXISTS `segmentation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `segmentation` (
  `_id` int NOT NULL AUTO_INCREMENT,
  `user_data_object_id` int DEFAULT NULL,
  `product_id` varchar(500) DEFAULT NULL,
  `destination_bucket` varchar(500) DEFAULT NULL,
  `destination_object` varchar(500) DEFAULT NULL,
  `response_id` varchar(500) DEFAULT NULL,
  `status` int DEFAULT '1',
  `segmentation_name` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `segmentation`
--

LOCK TABLES `segmentation` WRITE;
/*!40000 ALTER TABLE `segmentation` DISABLE KEYS */;
/*!40000 ALTER TABLE `segmentation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `train_instacart`
--

DROP TABLE IF EXISTS `train_instacart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `train_instacart` (
  `_id` int NOT NULL AUTO_INCREMENT,
  `status` int DEFAULT '1',
  `user_data_object_id` int DEFAULT NULL,
  PRIMARY KEY (`_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `train_instacart`
--

LOCK TABLES `train_instacart` WRITE;
/*!40000 ALTER TABLE `train_instacart` DISABLE KEYS */;
/*!40000 ALTER TABLE `train_instacart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_data_object`
--

DROP TABLE IF EXISTS `user_data_object`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_data_object` (
  `_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `user_id` int DEFAULT '1',
  `status` int DEFAULT '0',
  PRIMARY KEY (`_id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_data_object`
--

LOCK TABLES `user_data_object` WRITE;
/*!40000 ALTER TABLE `user_data_object` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_data_object` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-04-07  0:06:08
