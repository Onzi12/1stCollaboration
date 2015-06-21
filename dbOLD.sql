-- MySQL dump 10.13  Distrib 5.6.17, for Win32 (x86)
--
-- Host: localhost    Database: test
-- ------------------------------------------------------
-- Server version	5.6.23-log

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
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `fileDesc` varchar(45) DEFAULT NULL,
  `priv` int(1) NOT NULL,
  `havedeleted` int(1) NOT NULL DEFAULT '0',
  `isEdited` int(1) NOT NULL DEFAULT '0',
  `ownerId` int(11) DEFAULT NULL,
  `type` varchar(45) DEFAULT NULL,
  `filecol` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`),
  KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `file`
--

LOCK TABLES `file` WRITE;
/*!40000 ALTER TABLE `file` DISABLE KEYS */;
INSERT INTO `file` VALUES (3,'adir','adir',1,0,0,6,NULL,''),(6,'test','dsfsadfdsaf.dsahfjdsahjfkasd',2,0,0,1,NULL,''),(7,'fuck','dsafdsafadsfasd',2,0,0,3,NULL,''),(8,'t','t',2,0,0,1,NULL,''),(9,'12','dsafdas djksahfl hjdaskfhlads jdkahsfl',2,0,0,3,NULL,''),(10,'kjadsnfkjn','kjasdnkfjnsdf',1,0,0,9,NULL,''),(11,'sa','s',2,0,0,7,NULL,''),(12,'xS','d',2,0,0,8,NULL,''),(13,'tututu','asdf',2,0,0,1,NULL,''),(14,'togilski','',2,0,0,1,NULL,''),(15,'test2','',2,0,0,1,NULL,''),(16,'test3','',2,0,0,1,NULL,''),(17,'test4','',2,0,0,1,NULL,''),(18,'GilTest:)','dhgt',0,0,0,7,NULL,''),(19,'Test1000','',2,0,0,1,NULL,''),(20,'222222222222222','asdf2222',1,0,0,7,NULL,''),(21,'testfile','',2,0,0,1,NULL,'');
/*!40000 ALTER TABLE `file` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `filegroups`
--

DROP TABLE IF EXISTS `filegroups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `filegroups` (
  `fileId` int(11) NOT NULL,
  `groupId` int(11) NOT NULL,
  `isUpdateable` int(1) NOT NULL,
  PRIMARY KEY (`fileId`,`groupId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `filegroups`
--

LOCK TABLES `filegroups` WRITE;
/*!40000 ALTER TABLE `filegroups` DISABLE KEYS */;
/*!40000 ALTER TABLE `filegroups` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `folder`
--

DROP TABLE IF EXISTS `folder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `folder` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `folderId` int(11) DEFAULT '0',
  `userId` int(11) NOT NULL,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `folder`
--

LOCK TABLES `folder` WRITE;
/*!40000 ALTER TABLE `folder` DISABLE KEYS */;
INSERT INTO `folder` VALUES (1,NULL,1,'r'),(2,3,2,'g'),(3,NULL,2,'gf'),(12,0,1,'1'),(13,1,1,'Test123'),(14,0,1,'Test123'),(15,0,1,'2');
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
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `iduser` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `status` int(1) NOT NULL DEFAULT '1',
  `admin` int(1) NOT NULL DEFAULT '0',
  `counter` int(1) DEFAULT '0',
  PRIMARY KEY (`iduser`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'t','t',0,1,0),(2,'d','d',0,1,0),(3,'r','r',0,0,0),(4,'p','p',0,0,0),(5,'y','y',0,0,0),(6,'e','e',0,0,0),(7,'a','a',0,1,0),(8,'b','b',0,1,0),(9,'c','c',0,1,0),(10,'dd','dd',0,0,0);
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
INSERT INTO `userfile` VALUES (1,0,2,1),(1,NULL,13,1),(1,NULL,14,1),(1,0,15,1),(1,1,16,1),(1,1,17,1),(1,0,19,1),(1,0,21,1),(7,0,18,1),(7,0,20,1),(8,0,12,1);
/*!40000 ALTER TABLE `userfile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usergroups`
--

DROP TABLE IF EXISTS `usergroups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usergroups` (
  `userId` int(11) NOT NULL,
  `groupId` int(11) NOT NULL,
  PRIMARY KEY (`userId`,`groupId`),
  KEY `userid_idx` (`userId`),
  KEY `groupid_idx` (`groupId`),
  CONSTRAINT `groupId` FOREIGN KEY (`groupId`) REFERENCES `group` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `userId` FOREIGN KEY (`userId`) REFERENCES `user` (`iduser`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usergroups`
--

LOCK TABLES `usergroups` WRITE;
/*!40000 ALTER TABLE `usergroups` DISABLE KEYS */;
/*!40000 ALTER TABLE `usergroups` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `firstname` varchar(45) NOT NULL,
  `lastname` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'t','t','t','t','t'),(2,'r','r','r','r','r'),(3,'g','g','g','g','g');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-06-17 21:49:41
