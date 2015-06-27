-- MySQL dump 10.13  Distrib 5.6.23, for Win64 (x86_64)
--
-- Host: localhost    Database: test
-- ------------------------------------------------------
-- Server version	5.6.25-log

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
-- Table structure for table `file`
--

DROP TABLE IF EXISTS `file`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `file` (
  `fileID` int(11) NOT NULL AUTO_INCREMENT,
  `fileName` varchar(45) NOT NULL,
  `Description` blob,
  `privilege` tinyint(1) NOT NULL,
  `isDeleted` tinyint(1) NOT NULL DEFAULT '0',
  `isEdited` tinyint(1) NOT NULL DEFAULT '0',
  `ownerID` int(11) NOT NULL,
  PRIMARY KEY (`fileID`),
  UNIQUE KEY `name_UNIQUE` (`fileName`),
  KEY `id` (`fileID`),
  KEY `ownerID_idx` (`ownerID`),
  CONSTRAINT `ownerID` FOREIGN KEY (`ownerID`) REFERENCES `user` (`userID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `file`
--

LOCK TABLES `file` WRITE;
/*!40000 ALTER TABLE `file` DISABLE KEYS */;
INSERT INTO `file` VALUES (1,'pic2.doc',NULL,2,0,1,16),(2,'bank.doc',NULL,1,0,0,17),(3,'secret.doc',NULL,2,2,0,18),(4,'pic.jpeg',NULL,2,2,0,19),(5,'db.sql',NULL,2,1,0,16),(57,'fd.jpg','',2,0,0,17),(59,'afds.xlsx','',2,0,0,17);
/*!40000 ALTER TABLE `file` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `filegroups`
--

DROP TABLE IF EXISTS `filegroups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `filegroups` (
  `fileID` int(11) NOT NULL,
  `groupID` int(11) NOT NULL,
  `writeAccess` int(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`fileID`,`groupID`),
  KEY `groupID_idx` (`groupID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `filegroups`
--

LOCK TABLES `filegroups` WRITE;
/*!40000 ALTER TABLE `filegroups` DISABLE KEYS */;
INSERT INTO `filegroups` VALUES (1,1,0),(1,3,0),(4,1,0),(4,2,0),(4,3,0),(5,1,0),(5,3,0),(57,1,0),(59,1,0);
/*!40000 ALTER TABLE `filegroups` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `folder`
--

DROP TABLE IF EXISTS `folder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `folder` (
  `folderID` int(11) NOT NULL AUTO_INCREMENT,
  `userID` int(11) NOT NULL,
  `parentFolderID` int(11) DEFAULT '0',
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`folderID`),
  KEY `userID_idx` (`userID`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `folder`
--

LOCK TABLES `folder` WRITE;
/*!40000 ALTER TABLE `folder` DISABLE KEYS */;
INSERT INTO `folder` VALUES (1,16,0,'/'),(2,16,1,'new'),(3,17,0,'/'),(4,18,0,'/'),(5,19,0,'/'),(6,17,3,'new'),(7,18,4,'new'),(9,20,0,'/'),(10,21,0,'/'),(11,22,0,'/'),(12,23,0,'/'),(13,24,0,'/');
/*!40000 ALTER TABLE `folder` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `group`
--

DROP TABLE IF EXISTS `group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `groupNum` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `groupNum_UNIQUE` (`groupNum`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `group`
--

LOCK TABLES `group` WRITE;
/*!40000 ALTER TABLE `group` DISABLE KEYS */;
/*!40000 ALTER TABLE `group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `grouprequests`
--

DROP TABLE IF EXISTS `grouprequests`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `grouprequests` (
  `userID` int(11) NOT NULL,
  `groupID` int(11) NOT NULL,
  `type` int(1) NOT NULL,
  PRIMARY KEY (`userID`,`groupID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `grouprequests`
--

LOCK TABLES `grouprequests` WRITE;
/*!40000 ALTER TABLE `grouprequests` DISABLE KEYS */;
/*!40000 ALTER TABLE `grouprequests` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `groups`
--

DROP TABLE IF EXISTS `groups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `groups` (
  `groupID` int(11) NOT NULL AUTO_INCREMENT,
  `groupName` varchar(45) NOT NULL,
  PRIMARY KEY (`groupID`),
  UNIQUE KEY `groupName_UNIQUE` (`groupName`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `groups`
--

LOCK TABLES `groups` WRITE;
/*!40000 ALTER TABLE `groups` DISABLE KEYS */;
INSERT INTO `groups` VALUES (3,'engineer'),(2,'mechanics'),(1,'software');
/*!40000 ALTER TABLE `groups` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `userID` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `status` tinyint(1) NOT NULL DEFAULT '0',
  `isAdmin` tinyint(1) NOT NULL DEFAULT '0',
  `counter` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`userID`),
  UNIQUE KEY `username_UNIQUE` (`userName`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (16,'admin','0000',0,1,0),(17,'user','1234',0,0,0),(18,'working','c3',0,0,0),(19,'blocked','7d',0,0,3),(20,'Gil01','1234',0,0,0),(21,'Idan01','4567',2,0,0),(22,'Danny01','1324',1,0,0),(23,'On01','1234',0,0,0),(24,'On02','1234',0,0,0);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `userfile`
--

DROP TABLE IF EXISTS `userfile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `userfile` (
  `userId` int(11) NOT NULL,
  `folderId` int(11) DEFAULT NULL,
  `fileId` int(11) NOT NULL,
  `canUpdate` int(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`userId`,`fileId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `userfile`
--

LOCK TABLES `userfile` WRITE;
/*!40000 ALTER TABLE `userfile` DISABLE KEYS */;
INSERT INTO `userfile` VALUES (1,25,33,1),(5,0,33,1),(7,0,33,1),(8,0,33,1);
/*!40000 ALTER TABLE `userfile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `userfiles`
--

DROP TABLE IF EXISTS `userfiles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `userfiles` (
  `userID` int(11) NOT NULL,
  `folderID` int(11) NOT NULL,
  `fileID` int(11) NOT NULL,
  PRIMARY KEY (`userID`,`folderID`,`fileID`),
  KEY `folderID_idx` (`folderID`),
  KEY `fileID_idx` (`fileID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `userfiles`
--

LOCK TABLES `userfiles` WRITE;
/*!40000 ALTER TABLE `userfiles` DISABLE KEYS */;
INSERT INTO `userfiles` VALUES (16,1,1),(16,2,5),(17,3,1),(17,3,2),(17,3,57),(17,3,59),(18,4,3),(19,5,4),(17,6,5),(18,7,4);
/*!40000 ALTER TABLE `userfiles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usergroups`
--

DROP TABLE IF EXISTS `usergroups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usergroups` (
  `userID` int(11) NOT NULL,
  `groupID` int(11) NOT NULL,
  PRIMARY KEY (`userID`,`groupID`),
  KEY `userid_idx` (`userID`),
  KEY `groupid_idx` (`groupID`),
  CONSTRAINT `groupID` FOREIGN KEY (`groupID`) REFERENCES `groups` (`groupID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usergroups`
--

LOCK TABLES `usergroups` WRITE;
/*!40000 ALTER TABLE `usergroups` DISABLE KEYS */;
INSERT INTO `usergroups` VALUES (16,1),(16,3),(17,1),(17,2),(17,3),(18,2),(19,1),(19,2),(19,3);
/*!40000 ALTER TABLE `usergroups` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-06-27 14:28:31
