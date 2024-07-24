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
-- Table structure for table `disciplina`
--

DROP TABLE IF EXISTS `disciplina`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `disciplina` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(100) NOT NULL,
  `descricao` varchar(100) NOT NULL,
  `cargaHoraria` int NOT NULL,
  `Curso_idCurso` int NOT NULL,
  `professor_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_disciplina_Curso1_idx` (`Curso_idCurso`),
  KEY `fk_disciplina_professor1_idx` (`professor_id`),
  CONSTRAINT `fk_disciplina_Curso1` FOREIGN KEY (`Curso_idCurso`) REFERENCES `Curso` (`idCurso`),
  CONSTRAINT `fk_disciplina_professor1` FOREIGN KEY (`professor_id`) REFERENCES `professor` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `disciplina`
--

LOCK TABLES `disciplina` WRITE;
/*!40000 ALTER TABLE `disciplina` DISABLE KEYS */;
INSERT INTO `disciplina` VALUES (1,'Programação Orientada a Objetos','Estudo de conceitos e práticas de POO',60,1,1),(2,'Banco de Dados','Introdução a bancos de dados relacionais',60,1,2),(3,'Desenvolvimento Web','Desenvolvimento de aplicações web',60,1,3),(4,'Fundamentos de Redes','Princípios básicos de redes de computadores',60,2,4),(5,'Segurança de Redes','Conceitos de segurança em redes',60,2,5),(6,'Administração de Redes','Administração e manutenção de redes',60,2,1),(7,'Matemática','Estudo de matemática básica',60,3,2),(8,'Física','Estudo dos conceitos básicos de física',60,3,3),(9,'Química','Introdução à química geral',60,3,4),(10,'Manutenção de Computadores','Práticas de manutenção de hardware',60,4,5),(11,'Suporte Técnico','Técnicas de suporte a usuários',60,4,1),(12,'Redes de Computadores','Configuração e manutenção de redes',60,4,2);
/*!40000 ALTER TABLE `disciplina` ENABLE KEYS */;
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
