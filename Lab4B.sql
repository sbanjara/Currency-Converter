-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.7.18-log


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema lab4b
--

CREATE DATABASE IF NOT EXISTS lab4b;
USE lab4b;

--
-- Definition of table `currencies`
--

DROP TABLE IF EXISTS `currencies`;
CREATE TABLE `currencies` (
  `code` char(3) NOT NULL,
  `description` varchar(45) NOT NULL,
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `currencies`
--

/*!40000 ALTER TABLE `currencies` DISABLE KEYS */;
INSERT INTO `currencies` (`code`,`description`) VALUES 
 ('AUD','Australian Dollar'),
 ('BGN','Bulgarian Lev'),
 ('BRL','Brazilian Real'),
 ('CAD','Canadian Dollar'),
 ('CHF','Swiss Franc'),
 ('CNY','Chinese Yuan'),
 ('CZK','Czech Koruna'),
 ('DKK','Danish Krone'),
 ('EEK','Estonian Kroon'),
 ('EUR','Euro'),
 ('GBP','U.K. Pound Sterling'),
 ('HKD','Hong Kong Dollar'),
 ('HRK','Croatian Kuna'),
 ('HUF','Hungarian Forint'),
 ('IDR','Indonesian Rupiah'),
 ('ILS','Israeli New Sheqel'),
 ('INR','Indian Rupee'),
 ('ISK','Icelandic Krona'),
 ('JPY','Japanese Yen'),
 ('KRW','South Korean Won'),
 ('LTL','Lithuanian Lita'),
 ('LVL','Latvian Lats'),
 ('MXN','Mexican Peso'),
 ('MYR','Malaysian Ringgit'),
 ('NOK','Norwegian Krone'),
 ('NZD','New Zealand Dollar'),
 ('PHP','Philippine Peso'),
 ('PLN','Polish Zloty'),
 ('RON','Romanian New Leu'),
 ('RUB','Russian Rouble'),
 ('SEK','Swedish Krona'),
 ('SGD','Singapore Dollar'),
 ('THB','Thai Baht'),
 ('TRY','Turkish Lira'),
 ('USD','U.S. Dollar'),
 ('ZAR','South African Rand');
/*!40000 ALTER TABLE `currencies` ENABLE KEYS */;


--
-- Definition of table `rates`
--

DROP TABLE IF EXISTS `rates`;
CREATE TABLE `rates` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `code` char(3) NOT NULL,
  `rate` double NOT NULL,
  `date` date NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_rates_1` (`code`),
  CONSTRAINT `FK_rates_1` FOREIGN KEY (`code`) REFERENCES `currencies` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `rates`
--

/*!40000 ALTER TABLE `rates` DISABLE KEYS */;
INSERT INTO `rates` (`id`,`code`,`rate`,`date`) VALUES 
 (1,'CAD',1.3248232161,'2019-09-30'),
 (2,'HKD',7.839838369,'2019-09-30'),
 (3,'ISK',124.0701625494,'2019-09-30'),
 (4,'PHP',51.9358986133,'2019-09-30'),
 (5,'DKK',6.85664432,'2019-09-30'),
 (6,'HUF',307.4938010837,'2019-09-30'),
 (7,'CZK',23.7083295068,'2019-09-30'),
 (8,'GBP',0.81341721,'2019-09-30'),
 (9,'RON',4.3618330425,'2019-09-30'),
 (10,'SEK',9.822573239,'2019-09-30'),
 (11,'IDR',14195.0041326109,'2019-09-30'),
 (12,'INR',70.8618789604,'2019-09-30'),
 (13,'BRL',4.1590596014,'2019-09-30'),
 (14,'RUB',64.9790614381,'2019-09-30'),
 (15,'HRK',6.8059509597,'2019-09-30'),
 (16,'JPY',107.9897143907,'2019-09-30'),
 (17,'THB',30.5950959684,'2019-09-30'),
 (18,'CHF',0.9961428965,'2019-09-30'),
 (19,'EUR',0.9183579759,'2019-09-30'),
 (20,'MYR',4.1869776839,'2019-09-30'),
 (21,'BGN',1.7961245293,'2019-09-30'),
 (22,'TRY',5.6470750298,'2019-09-30'),
 (23,'CNY',7.14335568,'2019-09-30'),
 (24,'NOK',9.0874276793,'2019-09-30'),
 (25,'NZD',1.5956469832,'2019-09-30'),
 (26,'ZAR',15.2058040224,'2019-09-30'),
 (27,'USD',1,'2019-09-30'),
 (28,'MXN',19.7007989714,'2019-09-30'),
 (29,'SGD',1.3830471118,'2019-09-30'),
 (30,'AUD',1.480944072,'2019-09-30'),
 (31,'ILS',3.4784645055,'2019-09-30'),
 (32,'KRW',1198.3010377445,'2019-09-30'),
 (33,'PLN',4.0207548903,'2019-09-30');
/*!40000 ALTER TABLE `rates` ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
