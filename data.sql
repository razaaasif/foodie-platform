-- MySQL dump 10.13  Distrib 8.0.42, for Linux (x86_64)
--
-- Host: localhost    Database: foodie_db
-- ------------------------------------------------------
-- Server version	8.0.42-0ubuntu0.22.04.1

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
-- Current Database: `foodie_db`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `foodie_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `foodie_db`;

--
-- Table structure for table `menu_item`
--

DROP TABLE IF EXISTS `menu_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `menu_item` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `available` bit(1) NOT NULL,
  `category` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `restaurant_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menu_item`
--

LOCK TABLES `menu_item` WRITE;
/*!40000 ALTER TABLE `menu_item` DISABLE KEYS */;
INSERT INTO `menu_item` VALUES (1,_binary '','Main Course','Slow-cooked black lentils in tomato gravy','Dal Bukhara',895,1),(2,_binary '','Appetizer','Grilled jumbo prawns with Indian spices','Tandoori Jhinga',1195,1),(3,_binary '','Main Course','Pork ribs with sweet pickling spices','Meetha Achaar Spare Ribs',1350,2),(4,_binary '','Main Course','South Indian spiced mutton in ghee','Ghee Roast Mutton',1450,2),(5,_binary '','Main Course','Coconut milk-based vegetable stew with appam','Appam with Stew',550,3),(6,_binary '','Seafood','Crab in traditional coconut-based curry','Mangalorean Crab Curry',775,3),(7,_binary '','Main Course','Awadhi-style mutton biryani','Dum Pukht Biryani',925,1),(8,_binary '','Main Course','Slow-cooked lamb in aromatic spices','Shahi Nihari',975,1),(9,_binary '','Main Course','Creamy chicken with mushrooms and rice','Chicken Stroganoff',650,1),(10,_binary '','Snacks','Fried fish with French fries','Fish and Chips',600,3),(11,_binary '','BBQ','Marinated prawns on the grill','Grilled Prawns',790,4),(12,_binary '','Vegetarian','Spiced cottage cheese skewers','Paneer Tikka',490,4),(13,_binary '','Main Course','Rice with mutton and chicken kebabs','Chelo Kebab',595,3),(14,_binary '','Main Course','Assorted grilled meats and vegetables','Sizzler',650,3),(15,_binary '','Starter','Fried potatoes tossed in spicy sauce','Crispy Chilli Potatoes',450,3),(16,_binary '','Main Course','Chinese-style stir-fried chicken','Kung Pao Chicken',625,3),(17,_binary '','Main Course','Hyderabadi dum biryani','Chicken Biryani',490,3),(18,_binary '','Dessert','Traditional Hyderabadi dessert','Double Ka Meetha',220,3),(19,_binary '','Main Course','Rice with berries and mutton','Berry Pulao',550,1),(20,_binary '','Main Course','Mutton curry with fried potato sticks','Sali Boti',580,1);
/*!40000 ALTER TABLE `menu_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_item`
--

DROP TABLE IF EXISTS `order_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_item` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `menu_item_id` bigint NOT NULL,
  `price_per_item` decimal(38,2) NOT NULL,
  `quantity` int NOT NULL,
  `special_instructions` varchar(255) DEFAULT NULL,
  `order_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKt4dc2r9nbvbujrljv3e23iibt` (`order_id`),
  CONSTRAINT `FKt4dc2r9nbvbujrljv3e23iibt` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_item`
--

LOCK TABLES `order_item` WRITE;
/*!40000 ALTER TABLE `order_item` DISABLE KEYS */;
INSERT INTO `order_item` VALUES (1,1,790.00,2,'Extra spicy',1),(2,2,1195.00,2,'Extra spicy',1);
/*!40000 ALTER TABLE `order_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_on` datetime(6) DEFAULT NULL,
  `delivery_address` varchar(255) NOT NULL,
  `delivery_time` datetime(6) DEFAULT NULL,
  `order_time` datetime(6) NOT NULL,
  `payment_method` enum('BHARAT_PE','MOCK','PHONE_PE','RAZORPAY') NOT NULL,
  `payment_status` enum('FAILED','INITIATED','NOT_STARTED','SUCCESS') NOT NULL,
  `payment_time` datetime(6) DEFAULT NULL,
  `restaurant_id` bigint NOT NULL,
  `rider_id` varchar(255) DEFAULT NULL,
  `status` enum('CANCELLED','CREATED','DELIVERED','ON_THE_WAY','OUT_FOR_DELIVERY','PAYMENT_COMPLETED','PAYMENT_FAILED','PAYMENT_FAILED_CANCELLED','PAYMENT_PENDING','PICKED_UP','PREPARED','PREPARING','READY_FOR_PICKUP','RESTAURANT_CONFIRMED','RIDER_ASSIGN') NOT NULL,
  `total_amount` decimal(38,2) NOT NULL,
  `transaction_id` varchar(255) DEFAULT NULL,
  `updated_on` datetime(6) DEFAULT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,'2025-05-29 17:04:27.214900','224 Shyam Colony Faridabad, 121003','2025-05-29 17:06:09.051663','2025-05-29 17:04:27.124854','RAZORPAY','SUCCESS','2025-05-29 17:04:58.881983',1,'e13dd0d7-c4d8-40ef-93b1-b072278dfaf3','DELIVERED',3970.00,'4c04bf84-08e0-4501-a94d-453c669d28da','2025-05-29 17:06:09.056058',1);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payments`
--

DROP TABLE IF EXISTS `payments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payments` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `amount` decimal(38,2) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `method` enum('BHARAT_PE','MOCK','PHONE_PE','RAZORPAY') DEFAULT NULL,
  `order_id` bigint DEFAULT NULL,
  `status` enum('FAILED','INITIATED','NOT_STARTED','SUCCESS') DEFAULT NULL,
  `transaction_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payments`
--

LOCK TABLES `payments` WRITE;
/*!40000 ALTER TABLE `payments` DISABLE KEYS */;
INSERT INTO `payments` VALUES (1,3970.00,'2025-05-29 17:04:27.967186','RAZORPAY',1,'SUCCESS','4c04bf84-08e0-4501-a94d-453c669d28da');
/*!40000 ALTER TABLE `payments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `restaurants`
--

DROP TABLE IF EXISTS `restaurants`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `restaurants` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `restaurants`
--

LOCK TABLES `restaurants` WRITE;
/*!40000 ALTER TABLE `restaurants` DISABLE KEYS */;
INSERT INTO `restaurants` VALUES (1,'UAE','Americana Foods','+96-80-66604545'),(2,'faridabad ,Harayana','Burger Kings','+96-80-66604545'),(3,'faridabad ,Harayana','Haldirams','+96-80-66604545');
/*!40000 ALTER TABLE `restaurants` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `verified` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'vtiwari@americana-food.com','Vinod Tiwari',_binary '\0'),(2,'aasifraza9123@gmail.com','Aasif Raza',_binary '\0');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'foodie_db'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-05-29 22:37:21
