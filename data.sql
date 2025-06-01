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
  PRIMARY KEY (`id`),
  KEY `FKne8547lmh6hsebkoij09nxv9d` (`restaurant_id`),
  CONSTRAINT `FKne8547lmh6hsebkoij09nxv9d` FOREIGN KEY (`restaurant_id`) REFERENCES `restaurants` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menu_item`
--

LOCK TABLES `menu_item` WRITE;
/*!40000 ALTER TABLE `menu_item` DISABLE KEYS */;
INSERT INTO `menu_item` VALUES (21,_binary '','Main Course','Slow-cooked black lentils in tomato gravy','Dal Bukhara',895,1),(22,_binary '','Appetizer','Grilled jumbo prawns with Indian spices','Tandoori Jhinga',1195,1),(23,_binary '','Main Course','Pork ribs with sweet pickling spices','Meetha Achaar Spare Ribs',1350,1),(24,_binary '','Main Course','South Indian spiced mutton in ghee','Ghee Roast Mutton',1450,1),(25,_binary '','Main Course','Coconut milk-based vegetable stew with appam','Appam with Stew',550,1),(26,_binary '','Seafood','Crab in traditional coconut-based curry','Mangalorean Crab Curry',775,1),(27,_binary '','Main Course','Awadhi-style mutton biryani','Dum Pukht Biryani',925,1),(28,_binary '','Main Course','Slow-cooked lamb in aromatic spices','Shahi Nihari',975,1),(29,_binary '','Main Course','Creamy chicken with mushrooms and rice','Chicken Stroganoff',650,1),(30,_binary '','Snacks','Fried fish with French fries','Fish and Chips',600,1),(31,_binary '','BBQ','Marinated prawns on the grill','Grilled Prawns',790,1),(32,_binary '','Vegetarian','Spiced cottage cheese skewers','Paneer Tikka',490,1),(33,_binary '','Main Course','Rice with mutton and chicken kebabs','Chelo Kebab',595,1),(34,_binary '','Main Course','Assorted grilled meats and vegetables','Sizzler',650,1),(35,_binary '','Starter','Fried potatoes tossed in spicy sauce','Crispy Chilli Potatoes',450,1),(36,_binary '','Main Course','Chinese-style stir-fried chicken','Kung Pao Chicken',625,1),(37,_binary '','Main Course','Hyderabadi dum biryani','Chicken Biryani',490,1),(38,_binary '','Dessert','Traditional Hyderabadi dessert','Double Ka Meetha',220,1),(39,_binary '','Main Course','Rice with berries and mutton','Berry Pulao',550,1),(40,_binary '','Main Course','Mutton curry with fried potato sticks','Sali Boti',580,1);
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
  `status` enum('CANCELLED','CREATED','DELIVERED','ON_THE_WAY','PAYMENT_COMPLETED','PAYMENT_FAILED','PAYMENT_FAILED_CANCELLED','PAYMENT_PENDING','PICKED_UP','PREPARED','PREPARING','READY_TO_PICK_UP','RESTAURANT_CONFIRMED','RIDER_ASSIGN') NOT NULL,
  `total_amount` decimal(38,2) NOT NULL,
  `transaction_id` varchar(255) DEFAULT NULL,
  `updated_on` datetime(6) DEFAULT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_restaurant_order` (`restaurant_id`),
  CONSTRAINT `FK2m9qulf12xm537bku3jnrrbup` FOREIGN KEY (`restaurant_id`) REFERENCES `restaurants` (`id`),
  CONSTRAINT `fk_restaurant_order` FOREIGN KEY (`restaurant_id`) REFERENCES `restaurants` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,'2025-06-01 10:51:40.539679','224 Shyam Colony Faridabad, 121003','2025-06-01 10:56:26.522826','2025-06-01 10:51:40.338416','PHONE_PE','SUCCESS','2025-06-01 10:54:17.707012',1,'dd5e8c9b-c945-471f-9b4d-ba8cac7d12b4','DELIVERED',3970.00,'f0ddfa4a-ea71-4c83-8805-0de7a35db527','2025-06-01 10:56:26.532477',1);
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
  PRIMARY KEY (`id`),
  KEY `fk_payment_order` (`order_id`),
  CONSTRAINT `fk_payment_order` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payments`
--

LOCK TABLES `payments` WRITE;
/*!40000 ALTER TABLE `payments` DISABLE KEYS */;
INSERT INTO `payments` VALUES (1,3970.00,'2025-06-01 10:53:29.473701','PHONE_PE',1,'SUCCESS','f0ddfa4a-ea71-4c83-8805-0de7a35db527');
/*!40000 ALTER TABLE `payments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `processed_events`
--

DROP TABLE IF EXISTS `processed_events`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `processed_events` (
  `id` varchar(255) NOT NULL,
  `event_type` enum('CANCELLED','CREATED','DELIVERED','ON_THE_WAY','PAYMENT_COMPLETED','PAYMENT_FAILED','PAYMENT_FAILED_CANCELLED','PAYMENT_PENDING','PICKED_UP','PREPARED','PREPARING','READY_TO_PICK_UP','RESTAURANT_CONFIRMED','RIDER_ASSIGN') NOT NULL,
  `order_id` bigint NOT NULL,
  `processed_at` datetime(6) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK44lt19axa1yqaj4xxbg4alyp8` (`order_id`,`event_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `processed_events`
--

LOCK TABLES `processed_events` WRITE;
/*!40000 ALTER TABLE `processed_events` DISABLE KEYS */;
INSERT INTO `processed_events` VALUES ('094400ad-58b5-4414-a469-ad167137ab86','PICKED_UP',1,'2025-06-01 10:56:14.947377'),('6fc59f2e-8ed5-4867-a30a-68b08867730a','DELIVERED',1,'2025-06-01 10:56:26.525142'),('91d5ad61-0f86-40f8-85c1-2c5c1817dda7','PREPARING',1,'2025-06-01 10:54:50.519453'),('9823fa8e-2134-4500-85ab-46df9ad6a1d0','PREPARED',1,'2025-06-01 10:55:30.619821'),('a035b946-0401-49f2-8ffa-c0fc072ec3bd','RIDER_ASSIGN',1,'2025-06-01 10:55:53.392373'),('e61ca16c-7171-4233-bd44-35a85a5ef90f','RESTAURANT_CONFIRMED',1,'2025-06-01 10:54:37.853788');
/*!40000 ALTER TABLE `processed_events` ENABLE KEYS */;
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
  `status` enum('OPEN','BUSY','CLOSED','HOLIDAY','MAINTENANCE') NOT NULL DEFAULT 'CLOSED' COMMENT 'Restaurant operational status',
  `zip_code` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `restaurants`
--

LOCK TABLES `restaurants` WRITE;
/*!40000 ALTER TABLE `restaurants` DISABLE KEYS */;
INSERT INTO `restaurants` VALUES (1,'UAE','Americana Foods','+97 148890270','OPEN','121003'),(2,'Faridabad, Harayana','Haldirams','+96-80-66604545','CLOSED','121003');
/*!40000 ALTER TABLE `restaurants` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-06-01 17:13:52
