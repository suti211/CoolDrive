CREATE DATABASE  IF NOT EXISTS `CoolDrive`;
USE `CoolDrive`;
-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: 192.168.150.86    Database: CoolDrive
-- ------------------------------------------------------
-- Server version	5.5.54-0+deb8u1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES 'utf8' */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Files`
--

DROP TABLE IF EXISTS `Files`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = 'utf8' */;
CREATE TABLE `Files` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `path` varchar(210) NOT NULL,
  `size` DECIMAL(10,2) NOT NULL,
  `uploadDate` datetime DEFAULT NULL,
  `filename` varchar(70) DEFAULT NULL,
  `extension` varchar(45) DEFAULT NULL,
  `maxSize` double DEFAULT NULL,
  `isFolder` tinyint(1) DEFAULT NULL,
  `ownerId` int(11) DEFAULT NULL,
  `parentId` int(11) DEFAULT NULL,
  `label` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `Files_id_uindex` (`id`),
  UNIQUE KEY `Files_name_extension_parentid` (`filename`, `extension`, `parentId`),
  KEY `Files_Users_id_fk` (`ownerId`),
  KEY `Files_Files_id_fk` (`parentId`),
  CONSTRAINT `Files_Files_id_fk` FOREIGN KEY (`parentId`) REFERENCES `Files` (`id`),
  CONSTRAINT `Files_Users_id_fk` FOREIGN KEY (`ownerId`) REFERENCES `Users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET='utf8';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Files`
--

LOCK TABLES `Files` WRITE;
/*!40000 ALTER TABLE `Files` DISABLE KEYS */;
INSERT INTO `Files`(`path`, `size`, `uploadDate`, `filename`, `extension`, `maxSize`, `isFolder`, `ownerId`, `parentId`, `label`) VALUES ("D:\\CoolDrive\\Users\\", 0, NOW(), "CoolDrive_Home", "dir", NULL, 1, NULL, NULL, "HOME FOLDER");
/*!40000 ALTER TABLE `Files` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Permissions`
--

DROP TABLE IF EXISTS `Permissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = 'utf8' */;
CREATE TABLE `Permissions` (
  `fileId` int(11) NOT NULL,
  `userId` int(11) NOT NULL,
  KEY `Permissions_Files_id_fk` (`fileId`),
  KEY `Permissions_Users_id_fk` (`userId`),
  CONSTRAINT `Permissions_Files_id_fk` FOREIGN KEY (`fileId`) REFERENCES `Files` (`id`),
  CONSTRAINT `Permissions_Users_id_fk` FOREIGN KEY (`userId`) REFERENCES `Users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET='utf8';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Permissions`
--

LOCK TABLES `Permissions` WRITE;
/*!40000 ALTER TABLE `Permissions` DISABLE KEYS */;
/*!40000 ALTER TABLE `Permissions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Transactions`
--

DROP TABLE IF EXISTS `Transactions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = 'utf8' */;
CREATE TABLE `Transactions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL,
  `firstname` varchar(45) DEFAULT NULL,
  `lastname` varchar(45) DEFAULT NULL,
  `zip` varchar(45) NOT NULL,
  `city` varchar(45) NOT NULL,
  `address1` varchar(45) NOT NULL,
  `address2` varchar(45) DEFAULT NULL,
  `phone` varchar(45) NOT NULL,
  `bought` varchar(45) NOT NULL,
  `boughtDate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `userId_Users.id_idx` (`userId`),
  CONSTRAINT `userId_Users.id` FOREIGN KEY (`userId`) REFERENCES `Users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET='utf8';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Transactions`
--

LOCK TABLES `Transactions` WRITE;
/*!40000 ALTER TABLE `Transactions` DISABLE KEYS */;
/*!40000 ALTER TABLE `Transactions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Users`
--

DROP TABLE IF EXISTS `Users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = 'utf8' */;
CREATE TABLE `Users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(30) NOT NULL,
  `pass` varchar(70) NOT NULL,
  `email` varchar(70) NOT NULL,
  `validated` tinyint(1) NOT NULL,
  `firstname` varchar(70) DEFAULT NULL,
  `lastname` varchar(70) DEFAULT NULL,
  `admin` tinyint(1) DEFAULT NULL,
  `token` varchar(45) DEFAULT NULL,
  `registerdate` date DEFAULT NULL,
  `userhomeid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `Users_id_uindex` (`id`),
  UNIQUE KEY `Users_username_uindex` (`username`),
  UNIQUE KEY `Users_email_uindex` (`email`),
  KEY `userhomeid_idx` (`userhomeid`),
  CONSTRAINT `userhomeid_file_id_fk` FOREIGN KEY (`userhomeid`) REFERENCES `files` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET='utf8';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Users`
--

LOCK TABLES `Users` WRITE;
/*!40000 ALTER TABLE `Users` DISABLE KEYS */;
/*!40000 ALTER TABLE `Users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-08-01 14:57:00