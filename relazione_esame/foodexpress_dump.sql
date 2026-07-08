-- MySQL dump 10.13  Distrib 9.7.1, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: foodexpress
-- ------------------------------------------------------
-- Server version	9.7.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `foodexpress`
--

/*!40000 DROP DATABASE IF EXISTS `foodexpress`*/;

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `foodexpress` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `foodexpress`;

--
-- Table structure for table `badge`
--

DROP TABLE IF EXISTS `badge`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `badge` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `descrizione` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `icona` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `nome` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `soglia_punti` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKiyd7336fkfdeca0n0fj3pf5u8` (`nome`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `badge`
--

LOCK TABLES `badge` WRITE;
/*!40000 ALTER TABLE `badge` DISABLE KEYS */;
INSERT INTO `badge` VALUES (1,'Primi 50 punti fedeltà','🍴','Buongustaio',50),(2,'150 punti fedeltà','👨‍🍳','Gourmet',150),(3,'300 punti fedeltà','⭐','Chef del Gusto',300),(4,'600 punti fedeltà','🏆','Leggenda del Gusto',600);
/*!40000 ALTER TABLE `badge` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `carrello`
--

DROP TABLE IF EXISTS `carrello`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `carrello` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `utente_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKrx4nxhgb8yg9u6wu7lfxp4qjc` (`utente_id`),
  CONSTRAINT `FK2db8mu3vg5gjb438ygvfq6d2l` FOREIGN KEY (`utente_id`) REFERENCES `utente` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `carrello`
--

LOCK TABLES `carrello` WRITE;
/*!40000 ALTER TABLE `carrello` DISABLE KEYS */;
INSERT INTO `carrello` VALUES (1,2);
/*!40000 ALTER TABLE `carrello` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categoria`
--

DROP TABLE IF EXISTS `categoria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categoria` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `descrizione` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `icona` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `nome` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKprx5elpv558ah8pk8x18u56yc` (`nome`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categoria`
--

LOCK TABLES `categoria` WRITE;
/*!40000 ALTER TABLE `categoria` DISABLE KEYS */;
INSERT INTO `categoria` VALUES (1,'Per iniziare','🥗','Antipasti'),(2,'Pasta e risotti','🍝','Primi'),(3,'Cotte nel forno a legna','🍕','Pizze'),(4,'Carne e pesce','🍖','Secondi'),(5,'Per concludere','🍰','Dolci'),(6,'Da bere','🥤','Bevande'),(7,'Verdure e sfizi','🥦','Contorni');
/*!40000 ALTER TABLE `categoria` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `impostazioni`
--

DROP TABLE IF EXISTS `impostazioni`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `impostazioni` (
  `id` bigint NOT NULL,
  `indirizzo` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `logo_url` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `messaggio_home` varchar(1000) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `nome_sito` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `telefono` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `impostazioni`
--

LOCK TABLES `impostazioni` WRITE;
/*!40000 ALTER TABLE `impostazioni` DISABLE KEYS */;
INSERT INTO `impostazioni` VALUES (1,'Via Camillo Mazza 20, Ferrara','/immagini/sito/logo-1782848058330.png','Consegna gratis questo weekend!','Trattoria di Sarah','0532 123456');
/*!40000 ALTER TABLE `impostazioni` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ordine`
--

DROP TABLE IF EXISTS `ordine`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ordine` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `citta_consegna` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `costo_consegna` decimal(10,2) DEFAULT NULL,
  `data_ordine` datetime(6) NOT NULL,
  `distanza_km` double NOT NULL,
  `indirizzo_consegna` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `punti_guadagnati` int NOT NULL,
  `stato` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `subtotale` decimal(10,2) DEFAULT NULL,
  `tempo_stimato_min` int NOT NULL,
  `totale` decimal(10,2) DEFAULT NULL,
  `utente_id` bigint DEFAULT NULL,
  `nome_cliente` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `codice_voucher` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `metodo_pagamento` enum('CARTA','CONTANTI') COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `sconto` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKdg320enjlk6khf1wx5x8o28qx` (`utente_id`),
  CONSTRAINT `FKdg320enjlk6khf1wx5x8o28qx` FOREIGN KEY (`utente_id`) REFERENCES `utente` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ordine`
--

LOCK TABLES `ordine` WRITE;
/*!40000 ALTER TABLE `ordine` DISABLE KEYS */;
INSERT INTO `ordine` VALUES (1,'Ferrara',3.22,'2026-06-29 23:06:06.037826',0.9,'Via Mortara 20',24,'CONSEGNATO',21.50,19,24.72,2,'',NULL,NULL,NULL),(2,'Ferrar',4.90,'2026-06-29 23:06:28.800885',3,'Via Bologna 50',33,'CONSEGNATO',29.00,27,33.90,2,'',NULL,NULL,NULL),(3,'Ferrara',3.08,'2026-06-29 23:20:59.852844',0.73,'Via Bologna 10',0,'CONSEGNATO',17.50,18,20.58,NULL,'Giulia Ospite',NULL,NULL,NULL),(4,'Ferrara',4.90,'2026-06-29 23:21:00.393179',3,'Via Test 1',40,'RIFIUTATO',14.00,27,18.90,2,'Mario Rossi',NULL,NULL,NULL),(5,'Ferrara',3.86,'2026-06-29 23:23:32.870439',1.7,'Via Vincenzo Barlaam, 142',0,'RIFIUTATO',59.50,22,63.36,NULL,'Ferrara',NULL,NULL,NULL),(6,'Ferrara',3.86,'2026-06-30 07:56:46.876224',1.7,'Via Vincenzo Barlaam, 142',0,'CONSEGNATO',53.00,22,56.86,NULL,'Ferrara',NULL,NULL,NULL),(7,'Ferrara',3.76,'2026-06-30 08:14:10.231381',1.58,'Via  camillo mazza 25',30,'CONSEGNATO',27.00,21,30.76,2,'Mario Rossi',NULL,NULL,NULL),(8,'Ferrara',26.69,'2026-06-30 08:19:56.521026',30.24,'Via Roma 1',0,'CONSEGNATO',28.00,136,49.09,NULL,'Test Carta','FOOD20','CARTA',5.60),(9,'Ferrara',44.33,'2026-06-30 08:19:57.992427',52.29,'Via Bologna 5',50,'CONSEGNATO',6.50,224,50.83,2,'Mario Rossi',NULL,'CONTANTI',0.00),(10,'Ferrara',3.86,'2026-06-30 08:21:13.338456',1.7,'Via Vincenzo Barlaam, 142',23,'CONSEGNATO',19.50,22,23.36,1,'Admin',NULL,'CARTA',0.00),(11,'Ferrara',3.86,'2026-06-30 19:05:07.674692',1.7,'Via Vincenzo Barlaam, 142',0,'CREATO',61.50,22,65.36,NULL,'admin',NULL,'CONTANTI',0.00),(12,'Ferrara',3.86,'2026-06-30 19:05:36.685771',1.7,'Via Vincenzo Barlaam, 142',0,'CREATO',50.00,22,53.86,NULL,'admin',NULL,'CONTANTI',0.00),(13,'Ferrara',3.86,'2026-06-30 19:23:59.385152',1.7,'Via Vincenzo Barlaam, 142',0,'CREATO',54.00,22,57.86,NULL,'Ferrara',NULL,'CARTA',0.00),(14,'Ferrara',3.86,'2026-06-30 19:42:34.988273',1.7,'Via Vincenzo Barlaam, 142',52,'CREATO',49.00,22,52.86,1,'Admin',NULL,'CONTANTI',0.00),(15,'Ferrara',3.86,'2026-06-30 20:55:51.871008',1.7,'Via Vincenzo Barlaam, 142',1003,'CREATO',1000.00,22,1003.86,1,'Admin',NULL,'CARTA',0.00),(16,'Ferrara',4.90,'2026-07-01 11:52:38.909944',3,'Via Vincenzo Barlaam, 142',148,'CREATO',144.00,27,148.90,1,'Admin',NULL,'CONTANTI',0.00);
/*!40000 ALTER TABLE `ordine` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `piatto`
--

DROP TABLE IF EXISTS `piatto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `piatto` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `descrizione` varchar(1000) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `disponibile` bit(1) NOT NULL,
  `immagine_url` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `nome` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `prezzo` decimal(10,2) NOT NULL,
  `categoria_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3gciw9jeapyy3elajpeljqwb0` (`categoria_id`),
  CONSTRAINT `FK3gciw9jeapyy3elajpeljqwb0` FOREIGN KEY (`categoria_id`) REFERENCES `categoria` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `piatto`
--

LOCK TABLES `piatto` WRITE;
/*!40000 ALTER TABLE `piatto` DISABLE KEYS */;
INSERT INTO `piatto` VALUES (1,'Pane tostato con pomodoro e basilico',_binary '','/immagini/piatti/piatto-1-1782848318489.png','Bruschette miste',10.00,1),(2,'Selezione di salumi e formaggi',_binary '','/immagini/piatti/piatto-2-1782848338876.png','Tagliere di salumi',9.00,1),(3,'Uovo, guanciale, pecorino',_binary '','/immagini/piatti/piatto-3-1782848478760.png','Spaghetti alla Carbonara',8.50,2),(4,'Riso Carnaroli e funghi porcini',_binary '','/immagini/piatti/piatto-4-1782848618415.png','Risotto ai funghi',9.50,2),(5,'Ragù di carne e besciamella',_binary '','/immagini/piatti/piatto-5-1782848757942.png','Lasagne al forno',9.00,2),(6,'Pomodoro, mozzarella, basilico',_binary '','/immagini/piatti/piatto-6-1782848762332.png','Pizza Margherita',6.50,3),(7,'Pomodoro, mozzarella, salame piccante',_binary '','/immagini/piatti/piatto-7-1782848847184.png','Pizza Diavola',8.00,3),(8,'Mozzarella, gorgonzola, fontina, parmigiano,noci',_binary '','/immagini/piatti/piatto-8-1782848907364.png','Pizza Quattro Formaggi e noci',9.50,3),(9,'Con rucola e grana',_binary '','/immagini/piatti/piatto-9-1782849087853.png','Tagliata di manzo',28.00,4),(10,'Con patate e olive',_binary '','/immagini/piatti/piatto-10-1782849158878.png','Branzino al forno',15.00,4),(11,'Il classico tiramisù della casa',_binary '','/immagini/piatti/piatto-11-1782849265292.png','Tiramisù',4.50,5),(12,'Con coulis ai frutti di bosco',_binary '','/immagini/piatti/piatto-12-1782851617732.png','Panna cotta',4.00,5),(15,NULL,_binary '','/immagini/piatti/piatto-15-1782851705326.png','Tortellini in brodo',10.50,2),(17,'Pane tostato con pomodoro e basilico',_binary '','/immagini/piatti/piatto-17-1782851956186.png','Tartar di Tonno ',18.00,1),(20,'Mozzarella di bufala e pomodoro',_binary '','/immagini/piatti/piatto-20-1782851961560.png','Caprese di bufala',8.50,1),(21,'Con rucola e scaglie di grana',_binary '','/immagini/piatti/piatto-21-1782852149962.png','Carpaccio di manzo',9.50,1),(22,'Classico piemontese',_binary '','/immagini/piatti/piatto-22-1782852211714.png','Vitello tonnato',9.00,1),(23,'Polpette di carne al pomodoro',_binary '','/immagini/piatti/piatto-23-1782852307716.png','Polpette al sugo',7.50,1),(24,'Croccanti e dorate',_binary '','/immagini/piatti/piatto-24-1782852362940.png','Crocchette di patate',6.00,1),(25,'Cozze in guazzetto',_binary '','/immagini/piatti/piatto-25-1782852430040.png','Cozze alla marinara',10.00,1),(26,'Calamari e gamberi fritti',_binary '','/immagini/piatti/piatto-26-1782852504170.png','Fritto misto di mare',12.00,1),(28,'Riso e mozzarella fritti',_binary '','/immagini/piatti/piatto-28-1782852556522.png','Suppli al telefono',6.50,1),(29,'Ragu bolognese',_binary '','/immagini/piatti/piatto-29-1782851539906.png','Tagliatelle al ragu',9.50,2),(30,'Gnocchi di patate e pesto',_binary '','/immagini/piatti/piatto-30-1782852615799.png','Gnocchi al pesto',9.00,2),(31,'Pomodoro piccante',_binary '','/immagini/piatti/piatto-31-1782852690479.png','Penne all\'arrabbiata',8.00,2),(32,'Vongole e prezzemolo',_binary '','/immagini/piatti/piatto-32-1782852768752.png','Spaghetti alle vongole',12.00,2),(33,'Allo zafferano',_binary '','/immagini/piatti/piatto-33-1782852857394.png','Risotto alla milanese',10.00,2),(34,'Ripieni di zucca',_binary '','/immagini/piatti/piatto-34-1782852989047.png','Tortelli di zucca',10.50,2),(35,'Melanzane e ricotta salata',_binary '','/immagini/piatti/piatto-35-1782853129269.png','Pasta alla Norma',9.00,2),(36,'Al forno',_binary '','/immagini/piatti/piatto-36-1782853188627.png','Cannelloni ricotta e spinaci',9.50,2),(41,'Con erbe aromatiche',_binary '','/immagini/piatti/piatto-41-1782853264485.png','Pollo arrosto',11.00,4),(46,'Scottata con sesamo',_binary '','/immagini/piatti/piatto-46-1782853344256.png','Tagliata di tonno',16.00,4),(47,'Manzo brasato',_binary '','/immagini/piatti/piatto-47-1782853429557.png','Brasato al Barolo',15.50,4),(48,'Melanzane al forno',_binary '','/immagini/piatti/piatto-48-1782853558207.png','Parmigiana di melanzane',10.00,4),(49,'Croccanti',_binary '','/immagini/piatti/piatto-49-1782853677657.png','Patatine fritte',4.00,7),(50,'Verdure fresche',_binary '','/immagini/piatti/piatto-50-1782853828835.png','Insalata mista',4.50,7),(51,'Di stagione',_binary '','/immagini/piatti/piatto-51-1782853957554.png','Verdure grigliate',5.50,7),(53,'Con rosmarino',_binary '','/immagini/piatti/piatto-53-1782854060227.png','Patate al forno',4.50,7),(59,'Pomodoro, aglio, origano',_binary '','/immagini/piatti/piatto-59-1782854090435.png','Pizza Marinara',5.50,3),(61,'Prosciutto, funghi, carciofi',_binary '','/immagini/piatti/piatto-61-1782906372174.png','Pizza Capricciosa',8.50,3),(62,'Quattro gusti',_binary '','/immagini/piatti/piatto-62-1782906542424.png','Pizza Quattro Stagioni',8.50,3),(63,'Cotto e funghi',_binary '','/immagini/piatti/piatto-63-1782906618615.png','Pizza Prosciutto e funghi',8.00,3),(64,'Pomodoro e mozzarella di bufala',_binary '','/immagini/piatti/piatto-64-1782906782444.png','Pizza Bufala',8.50,3),(81,'Cremosa',_binary '','/immagini/piatti/piatto-81-1782906864740.png','Cheesecake ai frutti di bosco',5.50,5),(82,'Cuore caldo',_binary '','/immagini/piatti/piatto-82-1782906945543.png','Tortino al cioccolato',6.00,5),(83,'Crema caramellata',_binary '','/immagini/piatti/piatto-83-1782907020339.png','Creme brulee',5.50,5),(85,'Variante al pistacchio',_binary '','/immagini/piatti/piatto-85-1782907095846.png','Tiramisu al pistacchio',5.50,5),(87,'Crema e pinoli',_binary '','/immagini/piatti/piatto-87-1782907216936.png','Torta della nonna',5.00,5),(88,'Frutta fresca',_binary '','/immagini/piatti/piatto-88-1782907302822.png','Macedonia di frutta',4.00,5),(89,'Espresso italiano',_binary '','/immagini/piatti/piatto-89-1782907423296.png','Caffe espresso',1.50,6),(91,'Con un goccio di latte',_binary '','/immagini/piatti/piatto-91-1782907497023.png','Caffe macchiato',1.70,6),(93,'Variante amara',_binary '','/immagini/piatti/piatto-93-1782907583150.png','Spritz Campari',6.00,6),(94,'Lager alla spina',_binary '','/immagini/piatti/piatto-94-1782907662515.png','Birra bionda 0.4L',5.00,6),(95,'Ambrata',_binary '','/immagini/piatti/piatto-95-1782907752171.png','Birra rossa 0.4L',5.50,6);
/*!40000 ALTER TABLE `piatto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `utente`
--

DROP TABLE IF EXISTS `utente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `utente` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `livello` int NOT NULL,
  `nome` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `punti_fedelta` int NOT NULL,
  `ruolo` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `bloccato` bit(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKgxvq4mjswnupehxnp35vawmo2` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `utente`
--

LOCK TABLES `utente` WRITE;
/*!40000 ALTER TABLE `utente` DISABLE KEYS */;
INSERT INTO `utente` VALUES (1,'admin@foodexpress.it',13,'Admin','$2a$10$Gq.PyspMrJua3.SLx/PdquoXGi7BUALn0Fl8VIiZkjV0.prUMrRhG',1226,'ADMIN',_binary '\0'),(2,'user@foodexpress.it',2,'Mario Rossi','$2a$10$5z4AjJfD/6PkzeXf329PRO19Pi0G8ALQGXNjcA4NETBSY1xoVoC3e',177,'USER',_binary '\0');
/*!40000 ALTER TABLE `utente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `utente_badge`
--

DROP TABLE IF EXISTS `utente_badge`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `utente_badge` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `data_sblocco` datetime(6) NOT NULL,
  `badge_id` bigint NOT NULL,
  `utente_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKi3scs0p1b9dnbk4l2mkx3dblf` (`utente_id`,`badge_id`),
  KEY `FKe3cq6ceffkodafhwml95gb4fn` (`badge_id`),
  CONSTRAINT `FK4q3o8ndo0q2xea5jf8ptcmf3t` FOREIGN KEY (`utente_id`) REFERENCES `utente` (`id`),
  CONSTRAINT `FKe3cq6ceffkodafhwml95gb4fn` FOREIGN KEY (`badge_id`) REFERENCES `badge` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `utente_badge`
--

LOCK TABLES `utente_badge` WRITE;
/*!40000 ALTER TABLE `utente_badge` DISABLE KEYS */;
INSERT INTO `utente_badge` VALUES (1,'2026-06-29 23:06:28.809884',1,2),(2,'2026-06-30 08:19:59.039697',2,2),(3,'2026-06-30 19:42:35.105088',1,1),(4,'2026-06-30 20:55:51.999853',2,1),(5,'2026-06-30 20:55:52.004061',3,1),(6,'2026-06-30 20:55:52.005254',4,1);
/*!40000 ALTER TABLE `utente_badge` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `voce_carrello`
--

DROP TABLE IF EXISTS `voce_carrello`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `voce_carrello` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `quantita` int NOT NULL,
  `carrello_id` bigint NOT NULL,
  `piatto_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKoqp1xncy95u4xlmpjmkr1qbjv` (`carrello_id`,`piatto_id`),
  KEY `FKcon4v2eu06snj1hg7sxh0tk5s` (`piatto_id`),
  CONSTRAINT `FKcon4v2eu06snj1hg7sxh0tk5s` FOREIGN KEY (`piatto_id`) REFERENCES `piatto` (`id`),
  CONSTRAINT `FKqu5j69ibt1qsyojloqjj25cta` FOREIGN KEY (`carrello_id`) REFERENCES `carrello` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `voce_carrello`
--

LOCK TABLES `voce_carrello` WRITE;
/*!40000 ALTER TABLE `voce_carrello` DISABLE KEYS */;
/*!40000 ALTER TABLE `voce_carrello` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `voce_ordine`
--

DROP TABLE IF EXISTS `voce_ordine`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `voce_ordine` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nome_piatto` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `prezzo_unitario` decimal(10,2) NOT NULL,
  `quantita` int NOT NULL,
  `ordine_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKldlf45j47aoshjtxs9vwd9ti` (`ordine_id`),
  CONSTRAINT `FKldlf45j47aoshjtxs9vwd9ti` FOREIGN KEY (`ordine_id`) REFERENCES `ordine` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `voce_ordine`
--

LOCK TABLES `voce_ordine` WRITE;
/*!40000 ALTER TABLE `voce_ordine` DISABLE KEYS */;
INSERT INTO `voce_ordine` VALUES (1,'Spaghetti alla Carbonara',8.50,1,1),(2,'Pizza Margherita',6.50,2,1),(3,'Tagliata di manzo',14.00,1,2),(4,'Branzino al forno',15.00,1,2),(5,'Pizza Margherita',6.50,2,3),(6,'Tiramisù',4.50,1,3),(7,'Tagliata di manzo',14.00,1,4),(8,'Bruschette miste',5.50,1,5),(9,'Tagliere di salumi',9.00,2,5),(10,'Spaghetti alla Carbonara',8.50,2,5),(11,'Risotto ai funghi',9.50,2,5),(12,'Tagliere di salumi',9.00,4,6),(13,'Spaghetti alla Carbonara',8.50,2,6),(14,'Lasagne al forno',9.00,3,7),(15,'Tagliata di manzo',14.00,2,8),(16,'Pizza Margherita',6.50,1,9),(17,'Pizza Margherita',6.50,3,10),(18,'Bruschette miste',10.00,1,11),(19,'Tagliere di salumi',9.00,1,11),(20,'Spaghetti alla Carbonara',8.50,5,11),(21,'Bruschette miste',10.00,5,12),(22,'Pasta alla Norma',9.00,6,13),(23,'Bruschette miste',10.00,4,14),(24,'Tagliere di salumi',9.00,1,14),(25,'Bruschette miste',10.00,100,15),(26,'Pizza Margherita',6.50,6,16),(27,'Pizza Diavola',8.00,6,16),(28,'Pizza Quattro Formaggi e noci',9.50,6,16);
/*!40000 ALTER TABLE `voce_ordine` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `voucher`
--

DROP TABLE IF EXISTS `voucher`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `voucher` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `attivo` bit(1) NOT NULL,
  `codice` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `descrizione` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `minimo_ordine` decimal(10,2) NOT NULL,
  `tipo_sconto` enum('FISSO','PERCENTUALE') COLLATE utf8mb4_unicode_ci NOT NULL,
  `valore` decimal(10,2) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK5wo3pamngmcnm3lefu2do69mk` (`codice`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `voucher`
--

LOCK TABLES `voucher` WRITE;
/*!40000 ALTER TABLE `voucher` DISABLE KEYS */;
INSERT INTO `voucher` VALUES (1,_binary '','BENVENUTO10','Sconto di benvenuto 10%',0.00,'PERCENTUALE',10.00),(2,_binary '','SCONTO5','5€ di sconto sopra i 15€',15.00,'FISSO',5.00),(3,_binary '','FOOD20','20% di sconto sopra i 25€',25.00,'PERCENTUALE',20.00);
/*!40000 ALTER TABLE `voucher` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-07-01 14:58:53
