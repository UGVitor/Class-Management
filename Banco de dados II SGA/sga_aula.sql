-- MySQL dump 10.13  Distrib 8.0.36, for Linux (x86_64)
--
-- Host: localhost    Database: sga
-- ------------------------------------------------------
-- Server version	8.0.37-0ubuntu0.24.04.1

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
-- Table structure for table `aula`
--

DROP TABLE IF EXISTS `aula`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `aula` (
  `id` int NOT NULL AUTO_INCREMENT,
  `data` date NOT NULL,
  `horario` time DEFAULT NULL,
  `duracao` int DEFAULT NULL,
  `topico` varchar(200) DEFAULT NULL,
  `status` tinyint DEFAULT NULL,
  `disciplina_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_aula_disciplina1_idx` (`disciplina_id`),
  CONSTRAINT `fk_aula_disciplina1` FOREIGN KEY (`disciplina_id`) REFERENCES `disciplina` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `aula`
--

LOCK TABLES `aula` WRITE;
/*!40000 ALTER TABLE `aula` DISABLE KEYS */;
INSERT INTO `aula` VALUES (1,'2024-07-24','08:00:00',90,'Introdução à Programação Orientada a Objetos',1,1),(2,'2024-07-25','10:00:00',90,'Princípios de Encapsulamento e Herança',1,1),(3,'2024-07-26','14:00:00',90,'Polimorfismo e Interfaces',1,1),(4,'2024-07-24','09:00:00',60,'Modelo OSI e Camadas de Rede',1,2),(5,'2024-07-26','11:00:00',60,'Endereçamento IP e Sub-redes',1,2),(6,'2024-07-27','13:00:00',60,'Protocolos de Comunicação',1,2),(7,'2024-07-24','08:00:00',60,'Funções e Gráficos',1,3),(8,'2024-07-25','09:00:00',60,'Álgebra Linear',1,3),(9,'2024-07-26','10:00:00',60,'Geometria Analítica',1,3),(10,'2024-07-24','08:00:00',120,'Diagnóstico e Correção de Problemas de Hardware',1,4),(11,'2024-07-25','10:00:00',120,'Substituição de Componentes e Upgrades',1,4),(12,'2024-07-26','14:00:00',120,'Manutenção Preventiva e Corretiva',1,4);
/*!40000 ALTER TABLE `aula` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-07-24 10:54:02
