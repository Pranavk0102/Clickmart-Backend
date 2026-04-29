CREATE DATABASE  IF NOT EXISTS `ecommerce_backend` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `ecommerce_backend`;
-- MySQL dump 10.13  Distrib 8.0.42, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: ecommerce_backend
-- ------------------------------------------------------
-- Server version	8.0.42

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
-- Table structure for table `addresses`
--

DROP TABLE IF EXISTS `addresses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `addresses` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `addr1` varchar(255) NOT NULL,
  `addr2` varchar(255) DEFAULT NULL,
  `city` varchar(100) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `is_default` bit(1) NOT NULL,
  `name` varchar(100) NOT NULL,
  `phone` varchar(15) NOT NULL,
  `pin` varchar(6) NOT NULL,
  `state` varchar(100) NOT NULL,
  `type` varchar(10) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1fa36y2oqhao3wgg2rw1pi459` (`user_id`),
  CONSTRAINT `FK1fa36y2oqhao3wgg2rw1pi459` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `addresses`
--

LOCK TABLES `addresses` WRITE;
/*!40000 ALTER TABLE `addresses` DISABLE KEYS */;
INSERT INTO `addresses` VALUES (1,'Old Pillayar Kovil Street ','Ammapet','Salem','2026-04-15 15:26:11.340854',_binary '','25','9361284505','636005','Tamil Nadu','HOME','2026-04-15 15:26:11.340854',2),(3,'101,Fairlands Road','','Salem','2026-04-20 21:06:58.134772',_binary '','Riddhi Chawla','9480723973','636005','Tamil Nadu','HOME','2026-04-20 21:06:58.134772',7),(4,'2A/27, Padmavathy Colony','Opp.to Sivaraj Inn','Salem','2026-04-21 11:05:43.412246',_binary '','Sanjith R','9025656455','636005','Tamil Nadu','HOME','2026-04-21 11:05:43.412246',4),(5,'89 Annanagar','Varnapuram','Bhvani','2026-04-22 15:44:49.943548',_binary '','Pranav','9874563217','638301','Tamil Nadu','HOME','2026-04-22 15:44:49.943548',8);
/*!40000 ALTER TABLE `addresses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cart_items`
--

DROP TABLE IF EXISTS `cart_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart_items` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `quantity` int NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `product_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1re40cjegsfvw58xrkdp6bac6` (`product_id`),
  KEY `FK709eickf3kc0dujx3ub9i7btf` (`user_id`),
  CONSTRAINT `FK1re40cjegsfvw58xrkdp6bac6` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`),
  CONSTRAINT `FK709eickf3kc0dujx3ub9i7btf` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart_items`
--

LOCK TABLES `cart_items` WRITE;
/*!40000 ALTER TABLE `cart_items` DISABLE KEYS */;
INSERT INTO `cart_items` VALUES (15,'2026-04-28 16:33:40.720884',1,'2026-04-28 16:33:40.720884',32,2);
/*!40000 ALTER TABLE `cart_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categories` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `active` bit(1) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `description` varchar(500) DEFAULT NULL,
  `name` varchar(100) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_t8o6pivur7nn124jehx7cygw5` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES (1,_binary '','2026-04-02 15:58:17.582445','Gadgets & devices','Electronics','2026-04-15 07:35:46.186767'),(26,_binary '','2026-04-03 12:40:05.000000','Clothing, footwear and accessories for men and women','Fashion','2026-04-03 12:40:05.000000'),(27,_binary '','2026-04-03 12:40:05.000000','Furniture, appliances, cookware and home d├®cor','Home & Kitchen','2026-04-03 12:40:05.000000'),(28,_binary '','2026-04-03 12:40:05.000000','Fiction, non-fiction, academic and self-help books','Books','2026-04-03 12:40:05.000000'),(29,_binary '','2026-04-03 12:40:05.000000','Gym equipment, sportswear, outdoor gear and accessories','Sports & Fitness','2026-04-03 12:40:05.000000'),(30,_binary '','2026-04-03 12:40:05.000000','Skincare, haircare, wellness products and supplements','Beauty & Health','2026-04-03 12:40:05.000000'),(31,_binary '','2026-04-03 12:40:05.000000','Toys for kids, board games, puzzles and collectibles','Toys & Games','2026-04-03 12:40:05.000000'),(32,_binary '','2026-04-03 12:40:05.000000','Car accessories, tools, cleaning products and parts','Automotive','2026-04-03 12:40:05.000000'),(33,_binary '','2026-04-03 12:40:05.000000','Fresh produce, packaged foods, beverages and snacks','Groceries','2026-04-03 12:40:05.000000'),(34,_binary '','2026-04-03 12:40:05.000000','Notebooks, pens, art supplies and office supplies','Stationery','2026-04-03 12:40:05.000000'),(35,_binary '','2026-04-03 12:40:05.000000','Instruments, accessories, audio equipment and vinyl records','Music','2026-04-03 12:40:05.000000'),(36,_binary '','2026-04-03 12:40:05.000000','Food, toys, grooming products and accessories for pets','Pet Supplies','2026-04-15 14:12:01.406611'),(50,_binary '','2026-04-15 09:48:12.263148','Solid jewellery using precious metals','Jewellery','2026-04-20 21:13:58.985118'),(51,_binary '','2026-04-20 16:40:05.611199','The most expensive and branded gifts and items','Luxury','2026-04-20 21:14:02.143118');
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `coupons`
--

DROP TABLE IF EXISTS `coupons`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `coupons` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `active` bit(1) NOT NULL,
  `code` varchar(50) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `description` varchar(255) NOT NULL,
  `discount_type` varchar(20) NOT NULL,
  `discount_value` double NOT NULL,
  `expires_at` datetime(6) DEFAULT NULL,
  `max_discount` double DEFAULT NULL,
  `min_order_value` double DEFAULT NULL,
  `category_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_eplt0kkm9yf2of2lnx6c1oy9b` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `coupons`
--

LOCK TABLES `coupons` WRITE;
/*!40000 ALTER TABLE `coupons` DISABLE KEYS */;
INSERT INTO `coupons` VALUES (1,_binary '','WELCOME10','2026-04-03 12:40:05.000000','10% off on your first order','PERCENT',10,'2026-12-31 23:59:59.000000',200,500,NULL),(2,_binary '','FLAT200','2026-04-03 12:40:05.000000','Flat Rs.200 off on orders above Rs.1499','FLAT',200,'2026-09-30 23:59:59.000000',NULL,1499,NULL),(3,_binary '\0','SAVE50','2026-04-03 12:40:05.000000','Flat Rs.50 off on any order above Rs.299','FLAT',50,'2026-08-31 23:59:59.000000',NULL,299,NULL),(4,_binary '','SUMMER25','2026-04-03 12:40:05.000000','25% off sitewide during the summer sale','PERCENT',25,'2026-06-30 23:59:59.000000',500,999,NULL),(5,_binary '','TECH15','2026-04-03 12:40:05.000000','15% off on all electronics','PERCENT',15,'2026-10-31 23:59:59.000000',1500,2000,NULL),(6,_binary '','FASHION20','2026-04-03 12:40:05.000000','20% off on all fashion items','PERCENT',20,'2026-07-31 23:59:59.000000',400,799,NULL),(7,_binary '','BIGBUY500','2026-04-03 12:40:05.000000','Flat Rs.500 off on orders above Rs.4999','FLAT',500,'2026-11-30 23:59:59.000000',NULL,4999,NULL),(8,_binary '','HEALTH10','2026-04-03 12:40:05.000000','10% off on beauty and health products','PERCENT',10,'2026-12-31 23:59:59.000000',150,399,NULL),(9,_binary '','FLASHSALE','2026-04-03 12:40:05.000000','30% off ÔÇô Flash sale limited time offer','PERCENT',30,'2026-04-30 23:59:59.000000',750,1499,NULL),(10,_binary '','BOOKWORM','2026-04-03 12:40:05.000000','Rs.100 off on books purchase above Rs.499','FLAT',100,'2026-12-31 23:59:59.000000',NULL,499,NULL),(11,_binary '','FREESHIP','2026-04-03 12:40:05.000000','Free shipping on any order','FLAT',49,'2026-12-31 23:59:59.000000',NULL,0,NULL),(12,_binary '\0','EXPIRED99','2026-04-03 12:40:05.000000','Expired coupon ÔÇô not usable','PERCENT',99,'2024-01-01 00:00:00.000000',NULL,0,NULL);
/*!40000 ALTER TABLE `coupons` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `delivery_options`
--

DROP TABLE IF EXISTS `delivery_options`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `delivery_options` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `active` bit(1) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `label` varchar(255) NOT NULL,
  `name` varchar(50) NOT NULL,
  `price` double NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_spvnv84ftfj8gujrg778tx9vj` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `delivery_options`
--

LOCK TABLES `delivery_options` WRITE;
/*!40000 ALTER TABLE `delivery_options` DISABLE KEYS */;
INSERT INTO `delivery_options` VALUES (1,_binary '','2026-04-01 08:07:02.427266','Delivered in 5-7 business days','Standard Delivery','standard',50),(2,_binary '','2026-04-01 08:07:02.472366','Delivered in 2-3 business days','Express Delivery','express',99),(15,_binary '','2026-04-20 21:10:40.000000','Delivered in 7-10 business days','Free Delivery','FREE',0);
/*!40000 ALTER TABLE `delivery_options` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notifications`
--

DROP TABLE IF EXISTS `notifications`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notifications` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `is_read` bit(1) NOT NULL,
  `message` varchar(500) NOT NULL,
  `type` varchar(20) NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK9y21adhxn0ayjhfocscqox7bh` (`user_id`),
  CONSTRAINT `FK9y21adhxn0ayjhfocscqox7bh` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notifications`
--

LOCK TABLES `notifications` WRITE;
/*!40000 ALTER TABLE `notifications` DISABLE KEYS */;
INSERT INTO `notifications` VALUES (1,'2026-04-15 09:26:36.248837',_binary '','Order ORD-6EC34EF7 status changed to processing.','ORDER',2),(2,'2026-04-15 09:26:47.505572',_binary '','Your account is now inactive.','SYSTEM',2),(3,'2026-04-15 09:27:17.867437',_binary '','Your account is now active.','SYSTEM',2),(4,'2026-04-15 14:10:57.756207',_binary '','Order ORD-6EC34EF7 status changed to shipped.','ORDER',2),(5,'2026-04-15 14:11:01.632207',_binary '','Order ORD-6EC34EF7 status changed to delivered.','ORDER',2),(6,'2026-04-15 14:12:53.212478',_binary '','Your account is now inactive.','SYSTEM',2),(7,'2026-04-15 14:12:55.152524',_binary '','Your account is now active.','SYSTEM',2),(8,'2026-04-15 15:05:59.506138',_binary '','Order ORD-6EC34EF7 status changed to delivered.','ORDER',2),(9,'2026-04-15 15:06:19.388371',_binary '','Order ORD-6EC34EF7 status changed to delivered.','ORDER',2),(10,'2026-04-15 15:24:41.050577',_binary '','Order ORD-6EC34EF7 status changed to delivered.','ORDER',2),(11,'2026-04-15 15:26:59.361250',_binary '','Support ticket TKT-81D9F633 has been submitted.','SUPPORT',2),(12,'2026-04-15 15:26:59.369258',_binary '\0','New support ticket TKT-81D9F633 requires attention.','SUPPORT',1),(13,'2026-04-15 15:30:24.760910',_binary '','Support ticket TKT-81D9F633 has a new admin response.','SUPPORT',2),(14,'2026-04-20 16:39:12.781010',_binary '','Your account is now inactive.','SYSTEM',7),(15,'2026-04-20 16:39:13.970989',_binary '','Your account is now active.','SYSTEM',7),(16,'2026-04-20 22:09:51.327177',_binary '','Order ORD-A519523D was placed successfully.','ORDER',7),(17,'2026-04-20 22:09:51.336950',_binary '\0','New order ORD-A519523D has been placed.','ORDER',1),(18,'2026-04-20 22:11:17.214526',_binary '\0','Order ORD-A519523D status changed to processing.','ORDER',7),(19,'2026-04-20 22:11:32.378117',_binary '\0','Order ORD-A519523D status changed to shipped.','ORDER',7),(20,'2026-04-21 09:45:54.765821',_binary '\0','Order ORD-09527A60 was placed successfully.','ORDER',7),(21,'2026-04-21 09:45:54.782035',_binary '\0','New order ORD-09527A60 has been placed.','ORDER',1),(22,'2026-04-21 11:10:31.495018',_binary '\0','Order ORD-90D3BE59 was placed successfully.','ORDER',4),(23,'2026-04-21 11:10:31.505076',_binary '\0','New order ORD-90D3BE59 has been placed.','ORDER',1),(24,'2026-04-22 14:34:09.327722',_binary '\0','Order ORD-A519523D status changed to delivered.','ORDER',7),(25,'2026-04-22 15:48:57.836329',_binary '\0','Order ORD-A651D33D was placed successfully.','ORDER',8),(26,'2026-04-22 15:48:57.846327',_binary '\0','New order ORD-A651D33D has been placed.','ORDER',1),(27,'2026-04-22 16:03:14.496126',_binary '\0','Order ORD-A651D33D status changed to processing.','ORDER',8),(28,'2026-04-22 16:03:39.229453',_binary '\0','Order ORD-A651D33D status changed to shipped.','ORDER',8),(29,'2026-04-25 12:39:28.152510',_binary '\0','Order ORD-E67E001A was placed successfully.','ORDER',2),(30,'2026-04-25 12:39:28.172633',_binary '\0','New order ORD-E67E001A has been placed.','ORDER',1),(31,'2026-04-25 12:43:58.346378',_binary '\0','Order ORD-E67E001A status changed to processing.','ORDER',2),(32,'2026-04-25 12:44:00.205286',_binary '\0','Order ORD-E67E001A status changed to shipped.','ORDER',2),(33,'2026-04-25 12:44:02.899493',_binary '\0','Order ORD-E67E001A status changed to delivered.','ORDER',2),(34,'2026-04-26 22:54:08.636873',_binary '\0','Order ORD-A651D33D status changed to delivered.','ORDER',8),(35,'2026-04-27 13:55:06.861129',_binary '\0','Return request for order ORD-6EC34EF7 has been submitted.','ORDER',2),(36,'2026-04-28 16:32:58.682016',_binary '\0','Order ORD-B6C03043 was placed successfully.','ORDER',2),(37,'2026-04-28 16:32:58.694096',_binary '\0','New order ORD-B6C03043 has been placed.','ORDER',1);
/*!40000 ALTER TABLE `notifications` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_items`
--

DROP TABLE IF EXISTS `order_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_items` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `product_image` varchar(255) DEFAULT NULL,
  `product_name` varchar(255) NOT NULL,
  `quantity` int DEFAULT NULL,
  `unit_price` double DEFAULT NULL,
  `order_id` bigint NOT NULL,
  `product_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKbioxgbv59vetrxe0ejfubep1w` (`order_id`),
  KEY `FKocimc7dtr037rh4ls4l95nlfi` (`product_id`),
  CONSTRAINT `FKbioxgbv59vetrxe0ejfubep1w` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`),
  CONSTRAINT `FKocimc7dtr037rh4ls4l95nlfi` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_items`
--

LOCK TABLES `order_items` WRITE;
/*!40000 ALTER TABLE `order_items` DISABLE KEYS */;
INSERT INTO `order_items` VALUES (1,NULL,'Wireless Headphones',2,199.99,1,1),(13,'https://images.unsplash.com/photo-1542272604-787c3835535d?w=400','Levi\'s 501 Original Jeans',1,3999,13,23),(14,'https://res.cloudinary.com/dzxhtibmi/image/upload/v1776699892/products/adxwjxkjdqwtbu2xxm2w.webp','SteelBird Helmet',1,1500,14,32),(15,'https://images.unsplash.com/photo-1517336714731-489689fd1ca8?w=400','Apple MacBook Air M3',1,124999,15,18),(16,'https://images.unsplash.com/photo-1542291026-7eec264c27ff?w=400','Women Running Sneakers Pro',1,3499,16,22),(17,'https://images.unsplash.com/photo-1542291026-7eec264c27ff?w=400','Women Running Sneakers Pro',1,3499,17,22),(18,'https://res.cloudinary.com/dzxhtibmi/image/upload/v1777101209/products/bimehgoxmxk1yy0kmwkz.webp','Pencil Case',1,150,18,34);
/*!40000 ALTER TABLE `order_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `coupon_code` varchar(255) DEFAULT NULL,
  `created_at` datetime(6) NOT NULL,
  `delivery_charge` double DEFAULT NULL,
  `delivery_type` varchar(255) DEFAULT NULL,
  `discount` double DEFAULT NULL,
  `order_number` varchar(255) NOT NULL,
  `payment_method` enum('CARD','UPI','NETBANKING','COD','RAZORPAY') NOT NULL,
  `shipping_addr1` varchar(255) DEFAULT NULL,
  `shipping_addr2` varchar(255) DEFAULT NULL,
  `shipping_city` varchar(255) DEFAULT NULL,
  `shipping_name` varchar(255) DEFAULT NULL,
  `shipping_phone` varchar(255) DEFAULT NULL,
  `shipping_pin` varchar(255) DEFAULT NULL,
  `shipping_state` varchar(255) DEFAULT NULL,
  `status` enum('PENDING','PROCESSING','SHIPPED','DELIVERED','CANCELLED','RETURNED') NOT NULL,
  `subtotal` double DEFAULT NULL,
  `total` double NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_nthkiu7pgmnqnu86i2jyoe2v7` (`order_number`),
  KEY `FK32ql8ubntj5uh44ph9659tiih` (`user_id`),
  CONSTRAINT `FK32ql8ubntj5uh44ph9659tiih` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,NULL,'2026-04-02 16:09:30.919831',0,'STANDARD',0,'ORD-6EC34EF7','COD','89 Old Pillayar Kovil Street',NULL,'Ammapet,Salem','Priyannga T','9361284505','638301','TamilNadu','RETURNED',399.98,399.98,'2026-04-27 13:55:06.887644',2),(13,'FASHION20','2026-04-20 22:09:51.277724',0,'STANDARD',400,'ORD-A519523D','UPI','101,Fairlands Road','','Salem','Riddhi Chawla','9480723973','636005','Tamil Nadu','DELIVERED',3999,3599,'2026-04-22 14:34:09.345455',7),(14,NULL,'2026-04-21 09:45:54.644695',0,'STANDARD',0,'ORD-09527A60','RAZORPAY','101,Fairlands Road','','Salem','Riddhi Chawla','9480723973','636005','Tamil Nadu','PENDING',1500,1500,'2026-04-21 09:45:54.644695',7),(15,'BIGBUY500','2026-04-21 11:10:31.439100',50,'standard',500,'ORD-90D3BE59','RAZORPAY','2A/27, Padmavathy Colony','Opp.to Sivaraj Inn','Salem','Sanjith R','9025656455','636005','Tamil Nadu','PENDING',124999,124549,'2026-04-21 11:10:31.439100',4),(16,'SUMMER25','2026-04-22 15:48:57.802333',0,'FREE',500,'ORD-A651D33D','RAZORPAY','89 Annanagar','Varnapuram','Bhvani','Pranav','9874563217','638301','Tamil Nadu','DELIVERED',3499,2999,'2026-04-26 22:54:08.649540',8),(17,'FLAT200','2026-04-25 12:39:28.091427',99,'express',200,'ORD-E67E001A','RAZORPAY','Old Pillayar Kovil Street ','Ammapet','Salem','25','9361284505','636005','Tamil Nadu','DELIVERED',3499,3398,'2026-04-25 12:44:02.908068',2),(18,NULL,'2026-04-28 16:32:58.616978',0,'FREE',0,'ORD-B6C03043','RAZORPAY','Old Pillayar Kovil Street ','Ammapet','Salem','25','9361284505','636005','Tamil Nadu','PENDING',150,150,'2026-04-28 16:32:58.616978',2);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `password_reset_tokens`
--

DROP TABLE IF EXISTS `password_reset_tokens`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `password_reset_tokens` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `expiry_date` datetime(6) NOT NULL,
  `token` varchar(255) NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_71lqwbwtklmljk3qlsugr1mig` (`token`),
  UNIQUE KEY `UK_la2ts67g4oh2sreayswhox1i6` (`user_id`),
  CONSTRAINT `FKk3ndxg5xp6v7wd4gjyusp15gq` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `password_reset_tokens`
--

LOCK TABLES `password_reset_tokens` WRITE;
/*!40000 ALTER TABLE `password_reset_tokens` DISABLE KEYS */;
INSERT INTO `password_reset_tokens` VALUES (8,'2026-04-27 13:06:31.368516','4fba3000-3091-47e9-a5f0-7d0e35658e8f',1);
/*!40000 ALTER TABLE `password_reset_tokens` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payments`
--

DROP TABLE IF EXISTS `payments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payments` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `amount` double NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `method` varchar(30) NOT NULL,
  `status` varchar(20) NOT NULL,
  `transaction_id` varchar(255) DEFAULT NULL,
  `order_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_8vo36cen604as7etdfwmyjsxt` (`order_id`),
  CONSTRAINT `FK81gagumt0r8y3rmudcgpbk42l` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payments`
--

LOCK TABLES `payments` WRITE;
/*!40000 ALTER TABLE `payments` DISABLE KEYS */;
INSERT INTO `payments` VALUES (1,399.98,'2026-04-02 16:09:30.949532','cod','SUCCESS','b2cafacf-fa0b-4392-a23a-4719163f4eb6',1),(13,3599,'2026-04-20 22:09:51.311114','upi','PENDING','4327475f-749b-4c1f-8d39-81d1c99cd14f',13),(14,1500,'2026-04-21 09:45:54.733668','razorpay','SUCCESS','pay_Sg0WEgearfVLUS',14),(15,124549,'2026-04-21 11:10:31.474105','razorpay','SUCCESS','pay_Sg1xc0PevXQxKa',15),(16,2999,'2026-04-22 15:48:57.824327','razorpay','SUCCESS','pay_SgVEpHObdboW5G',16),(17,3398,'2026-04-25 12:39:28.125946','razorpay','SUCCESS','pay_Shdc3fWhBYtUKL',17),(18,150,'2026-04-28 16:32:58.657844','razorpay','SUCCESS','pay_SitC1KpBsgQGyg',18);
/*!40000 ALTER TABLE `payments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `active` bit(1) NOT NULL,
  `brand` varchar(255) DEFAULT NULL,
  `created_at` datetime(6) NOT NULL,
  `description` varchar(2000) DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `original_price` double DEFAULT NULL,
  `price` double NOT NULL,
  `rating` double DEFAULT NULL,
  `review_count` int DEFAULT NULL,
  `stock` int DEFAULT NULL,
  `updated_at` datetime(6) NOT NULL,
  `category_id` bigint DEFAULT NULL,
  `image_public_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKog2rp4qthbtt2lfyhfo32lsw9` (`category_id`),
  CONSTRAINT `FKog2rp4qthbtt2lfyhfo32lsw9` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1,_binary '','AudioTech','2026-04-02 15:59:09.057027','Noise cancelling over-ear headphones',NULL,'Wireless Headphones',249.99,199.99,0,0,50,'2026-04-27 13:55:06.887644',1,NULL),(2,_binary '','Samsung','2026-04-03 12:40:05.000000','Flagship smartphone with 6.2\" Dynamic AMOLED, 50MP triple camera, Snapdragon 8 Gen 3, 4000mAh battery.','https://images.unsplash.com/photo-1610945265064-0e34e5519bbf?w=400','Samsung Galaxy S24',89999,74999,4.5,320,45,'2026-04-03 12:40:05.000000',1,NULL),(3,_binary '','Apple','2026-04-03 12:40:05.000000','13.6\" Liquid Retina display, M3 chip, 16 GB RAM, 512 GB SSD. Ultra-thin all-day battery life.','https://images.unsplash.com/photo-1517336714731-489689fd1ca8?w=400','Apple MacBook Air M3',134999,124999,4.8,512,20,'2026-04-03 12:40:05.000000',1,NULL),(4,_binary '','Sony','2026-04-03 12:40:05.000000','Industry-leading noise cancellation, 30-hour battery, multipoint connect, crystal-clear call quality.','https://images.unsplash.com/photo-1505740420928-5e560c06d30e?w=400','Sony WH-1000XM5 Headphones',34999,26999,4.7,890,80,'2026-04-03 12:40:05.000000',1,NULL),(5,_binary '','Logitech','2026-04-03 12:40:05.000000','Advanced wireless mouse with 8K DPI sensor, MagSpeed scrolling, ergonomic design, USB-C charging.','https://images.unsplash.com/photo-1527864550417-7fd91fc51a46?w=400','Logitech MX Master 3S Mouse',9999,8499,4.6,670,110,'2026-04-03 12:40:05.000000',1,NULL),(6,_binary '','Arrow','2026-04-03 12:40:05.000000','Premium cotton blend chinos with modern slim fit. Machine washable. Available in multiple colours.','https://images.unsplash.com/photo-1473966968600-fa801b869a1a?w=400','Men Classic Slim Fit Chinos',2999,1799,4.2,145,200,'2026-04-03 12:40:05.000000',26,NULL),(7,_binary '','Nike','2026-04-03 12:40:05.000000','Lightweight mesh upper with responsive cushioning. Ideal for daily runs and gym sessions.','https://images.unsplash.com/photo-1542291026-7eec264c27ff?w=400','Women Running Sneakers Pro',4999,3499,4.4,230,150,'2026-04-03 12:40:05.000000',26,NULL),(8,_binary '','Levis','2026-04-03 12:40:05.000000','The original jean since 1873. Straight fit, button fly, 100% cotton denim. Timeless and durable.','https://images.unsplash.com/photo-1542272604-787c3835535d?w=400','Levi\'s 501 Original Jeans',5499,3999,4.5,980,180,'2026-04-03 12:40:05.000000',26,NULL),(9,_binary '','Philips','2026-04-03 12:40:05.000000','1400W, 4.1L capacity, Rapid Air technology. Fry, bake, grill with up to 90% less fat.','https://images.unsplash.com/photo-1585325701956-60dd9c8553bc?w=400','Philips Air Fryer HD9252',9999,7499,4.6,410,60,'2026-04-03 12:40:05.000000',27,NULL),(10,_binary '','Penguin Random House','2026-04-03 12:40:05.000000','Build good habits and break bad ones. #1 New York Times bestseller. Practical step-by-step framework.','https://images.unsplash.com/photo-1544947950-fa07a98d237f?w=400','Atomic Habits ÔÇô James Clear',799,499,4.9,2100,500,'2026-04-03 12:40:05.000000',28,NULL),(11,_binary '','Jaico Publishing','2026-04-03 12:40:05.000000','Timeless lessons on wealth, greed and happiness by Morgan Housel. A must-read personal finance book.','https://images.unsplash.com/photo-1553729459-efe14ef6055d?w=400','The Psychology of Money',699,449,4.8,1560,450,'2026-04-03 12:40:05.000000',28,NULL),(12,_binary '','Boldfit','2026-04-03 12:40:05.000000','Rubber coated hex dumbbells with anti-roll, anti-slip grip. Perfect for home and gym workouts.','https://images.unsplash.com/photo-1534438327276-14e5300c3a48?w=400','Boldfit Hex Dumbbell Set 5kg x2',3999,2999,4.4,320,75,'2026-04-03 12:40:05.000000',29,NULL),(13,_binary '','Minimalist','2026-04-03 12:40:05.000000','30ml serum that reduces blemishes, acne marks, minimises pores. Suitable for all skin types.','https://images.unsplash.com/photo-1608248597279-f99d160bfcbc?w=400','Minimalist 10% Niacinamide Serum',1199,799,4.6,870,300,'2026-04-03 12:40:05.000000',30,NULL),(17,_binary '','Samsung','2026-04-03 12:40:26.000000','Flagship smartphone with 6.2\" Dynamic AMOLED, 50MP triple camera, Snapdragon 8 Gen 3, 4000mAh battery.','https://images.unsplash.com/photo-1610945265064-0e34e5519bbf?w=400','Samsung Galaxy S24',89999,74999,4.5,320,45,'2026-04-03 12:40:26.000000',1,NULL),(18,_binary '','Apple','2026-04-03 12:40:26.000000','13.6\" Liquid Retina display, M3 chip, 16 GB RAM, 512 GB SSD. Ultra-thin all-day battery life.','https://images.unsplash.com/photo-1517336714731-489689fd1ca8?w=400','Apple MacBook Air M3',134999,124999,4.8,512,19,'2026-04-21 11:10:31.507165',1,NULL),(19,_binary '','Sony','2026-04-03 12:40:26.000000','Industry-leading noise cancellation, 30-hour battery, multipoint connect, crystal-clear call quality.','https://images.unsplash.com/photo-1505740420928-5e560c06d30e?w=400','Sony WH-1000XM5 Headphones',34999,26999,4.7,890,80,'2026-04-03 12:40:26.000000',1,NULL),(20,_binary '','Logitech','2026-04-03 12:40:26.000000','Advanced wireless mouse with 8K DPI sensor, MagSpeed scrolling, ergonomic design, USB-C charging.','https://images.unsplash.com/photo-1527864550417-7fd91fc51a46?w=400','Logitech MX Master 3S Mouse',9999,8499,4.6,670,110,'2026-04-03 12:40:26.000000',1,NULL),(21,_binary '','Arrow','2026-04-03 12:40:26.000000','Premium cotton blend chinos with modern slim fit. Machine washable. Available in multiple colours.','https://images.unsplash.com/photo-1473966968600-fa801b869a1a?w=400','Men Classic Slim Fit Chinos',2999,1799,4.2,145,200,'2026-04-03 12:40:26.000000',26,NULL),(22,_binary '','Nike','2026-04-03 12:40:26.000000','Lightweight mesh upper with responsive cushioning. Ideal for daily runs and gym sessions.','https://images.unsplash.com/photo-1542291026-7eec264c27ff?w=400','Women Running Sneakers Pro',4999,3499,4.4,230,148,'2026-04-25 12:39:28.174447',26,NULL),(23,_binary '','Levis','2026-04-03 12:40:26.000000','The original jean since 1873. Straight fit, button fly, 100% cotton denim. Timeless and durable.','https://images.unsplash.com/photo-1542272604-787c3835535d?w=400','Levi\'s 501 Original Jeans',5499,3999,4.5,980,179,'2026-04-20 22:09:51.339498',26,NULL),(24,_binary '','Philips','2026-04-03 12:40:26.000000','1400W, 4.1L capacity, Rapid Air technology. Fry, bake, grill with up to 90% less fat.','https://images.unsplash.com/photo-1585325701956-60dd9c8553bc?w=400','Philips Air Fryer HD9252',9999,7499,4.6,410,60,'2026-04-03 12:40:26.000000',27,NULL),(25,_binary '','Penguin Random House','2026-04-03 12:40:26.000000','Build good habits and break bad ones. #1 New York Times bestseller. Practical step-by-step framework.','https://images.unsplash.com/photo-1544947950-fa07a98d237f?w=400','Atomic Habits ÔÇô James Clear',799,499,4.9,2100,500,'2026-04-03 12:40:26.000000',28,NULL),(26,_binary '','Jaico Publishing','2026-04-03 12:40:26.000000','Timeless lessons on wealth, greed and happiness by Morgan Housel. A must-read personal finance book.','https://images.unsplash.com/photo-1553729459-efe14ef6055d?w=400','The Psychology of Money',699,449,4.8,1560,450,'2026-04-03 12:40:26.000000',28,NULL),(27,_binary '','Boldfit','2026-04-03 12:40:26.000000','Rubber coated hex dumbbells with anti-roll, anti-slip grip. Perfect for home and gym workouts.','https://images.unsplash.com/photo-1534438327276-14e5300c3a48?w=400','Boldfit Hex Dumbbell Set 5kg x2',3999,2999,4.4,320,75,'2026-04-03 12:40:26.000000',29,NULL),(28,_binary '','Minimalist','2026-04-03 12:40:26.000000','30ml serum that reduces blemishes, acne marks, minimises pores. Suitable for all skin types.','https://images.unsplash.com/photo-1608248597279-f99d160bfcbc?w=400','Minimalist 10% Niacinamide Serum',1199,799,4.6,870,300,'2026-04-03 12:40:26.000000',30,NULL),(32,_binary '','SteelBird','2026-04-15 09:30:36.034164','An all black DOT certified high-durability helmet. It has proper ventilation,comes with an extra blacked out visor and inbuilt sunglasses.','https://res.cloudinary.com/dzxhtibmi/image/upload/v1776699892/products/adxwjxkjdqwtbu2xxm2w.webp','SteelBird Helmet',3000,1500,0,0,48,'2026-04-21 09:45:54.786652',32,'products/adxwjxkjdqwtbu2xxm2w'),(33,_binary '','F1','2026-04-20 20:53:43.285124','A stylish metal strap watch with a rotating dial.','https://res.cloudinary.com/dzxhtibmi/image/upload/v1776848866/products/npf4zvu9oxvjzelapsjn.webp','Watch',3499,1500,0,0,50,'2026-04-27 11:44:33.599489',26,'products/npf4zvu9oxvjzelapsjn'),(34,_binary '','Dmart','2026-04-25 12:43:30.499926','Plastic Pencil Case','https://res.cloudinary.com/dzxhtibmi/image/upload/v1777101209/products/bimehgoxmxk1yy0kmwkz.webp','Pencil Case',300,150,0,0,17,'2026-04-28 16:32:58.696658',34,'products/bimehgoxmxk1yy0kmwkz');
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reviews`
--

DROP TABLE IF EXISTS `reviews`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reviews` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `comment` varchar(2000) DEFAULT NULL,
  `created_at` datetime(6) NOT NULL,
  `rating` int NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `product_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKpl51cejpw4gy5swfar8br9ngi` (`product_id`),
  KEY `FKcgy7qjc1r99dp117y9en6lxye` (`user_id`),
  CONSTRAINT `FKcgy7qjc1r99dp117y9en6lxye` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKpl51cejpw4gy5swfar8br9ngi` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reviews`
--

LOCK TABLES `reviews` WRITE;
/*!40000 ALTER TABLE `reviews` DISABLE KEYS */;
/*!40000 ALTER TABLE `reviews` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `revoked_tokens`
--

DROP TABLE IF EXISTS `revoked_tokens`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `revoked_tokens` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `expires_at` datetime(6) NOT NULL,
  `revoked_at` datetime(6) NOT NULL,
  `token_hash` varchar(64) NOT NULL,
  `token_type` varchar(20) NOT NULL,
  `username` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_2ele31feocsuq970bht2vmy4j` (`token_hash`)
) ENGINE=InnoDB AUTO_INCREMENT=144 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `revoked_tokens`
--

LOCK TABLES `revoked_tokens` WRITE;
/*!40000 ALTER TABLE `revoked_tokens` DISABLE KEYS */;
INSERT INTO `revoked_tokens` VALUES (87,'2026-04-29 10:19:06.000000','2026-04-22 10:19:17.764703','ae834c3b8d496997ebc60c879d11ddba38505e85d63e23eb16ed5f5ea9650e93','refresh','pranavk105@gmail.com'),(89,'2026-04-29 10:19:31.000000','2026-04-22 10:25:36.145479','4ac3d93ce079ee84f00431f0ef7b45c2b2ed1792d04808f78b9325ccabb6a727','refresh','riddhi@gmail.com'),(91,'2026-04-29 10:26:51.000000','2026-04-22 10:33:26.400383','74e1ec08a0d146bd248e22f9fb9b0feccb9133480b691206bf67315728230bfe','refresh','pranavk105@gmail.com'),(93,'2026-04-29 12:19:24.000000','2026-04-22 12:20:01.039031','04d56917cbb91c59917f4cb328719a471a3de1bd4b6a30a0d49b0f892876b0a4','refresh','pranavk105@gmail.com'),(94,'2026-04-29 12:20:15.000000','2026-04-22 14:23:12.986333','606af9247734da856708e20c9202d27f0a5d3404b83032a4cf83bc4497249315','refresh','priya@gmail.com'),(96,'2026-04-29 14:23:45.000000','2026-04-22 14:24:28.149528','c4fca8034adeb9d8f09eef0a00bc953df6f26bc18b3179935312fa8b1b5ae975','refresh','priya@gmail.com'),(98,'2026-04-29 14:33:34.000000','2026-04-22 14:33:54.984171','062d8e9f3368f590337f6064bebacbb6e158b161dfdec2621e34834d9b01a7f0','refresh','riddhi@gmail.com'),(100,'2026-04-29 14:33:59.000000','2026-04-22 14:34:47.822727','2d777fa13b589aee091240e22af8e8b0c200da3a44f54fa5a641860357df926b','refresh','pranavk105@gmail.com'),(102,'2026-04-29 14:34:55.000000','2026-04-22 14:35:45.749920','ba4f0247f8b6d3d3098b0beccb1c7a035b3d4cc0537b4ca7ee25019afd7b5fee','refresh','riddhi@gmail.com'),(104,'2026-04-29 14:36:01.000000','2026-04-22 14:36:20.179751','b2a5adb947cbd49dc958fdd7dd5aecba2951276ceab4d71525d1dad4eb8adae7','refresh','pranavk105@gmail.com'),(106,'2026-04-29 14:36:28.000000','2026-04-22 14:37:24.059297','d4f3a5dcfc410151859bef48920e375040026e6cf892a4345c9960a4fc6b1c70','refresh','riddhi@gmail.com'),(108,'2026-04-29 14:37:30.000000','2026-04-22 14:38:05.455014','bccc0b8064146a548bbc8e23565c1bcf1c869f5b0373e2347c75953254d02801','refresh','pranavk105@gmail.com'),(110,'2026-04-29 15:31:14.000000','2026-04-22 15:56:02.242978','9c4d467b7b65f8039a9cd7c6fc8437ef49aa6a8345534210fe59c9acda5acf21','refresh','arya@gmail.com'),(112,'2026-04-29 15:58:03.000000','2026-04-22 16:04:34.101729','4f09e743bc79b143306be95f001ac1d384aef0e5ee4d540251877737b63efffa','refresh','pranavk105@gmail.com'),(114,'2026-05-02 12:37:33.000000','2026-04-25 12:39:56.066263','41567a7fed996f52fa465cf172e69a60ab9541b6506264f39a4199a1703ab643','refresh','priya@gmail.com'),(116,'2026-05-02 12:40:05.000000','2026-04-25 12:44:27.250423','1b3298d2b3e514369dba4c97d18cddd3dc31a90e23ca51c98f732d628142da7e','refresh','pranavk105@gmail.com'),(118,'2026-05-02 12:44:43.000000','2026-04-25 12:45:07.244673','4cb3f210391f382b8af6272c7d1d326a0d2d5892b6084ac318eb78e571dabd6f','refresh','priya@gmail.com'),(120,'2026-05-03 22:53:43.000000','2026-04-26 22:54:19.897830','7871ec6dbf2433114c6991f23fb52b3842a5ef121dc5859118c116c5f52144a2','refresh','pranavk105@gmail.com'),(122,'2026-05-04 09:54:00.000000','2026-04-27 09:54:04.860420','62beec8aeb86c1791da9314f327c7d5a35882424be711f6707629ec6fa9f1eb4','refresh','2k22it20@kiot.ac.in'),(124,'2026-05-04 11:35:19.000000','2026-04-27 11:43:41.647143','2333eb4edcbf04c0545852d09be6f5be958bb3a184c415e81bd344b31df7bb27','refresh','priya@gmail.com'),(126,'2026-05-04 11:43:47.000000','2026-04-27 11:47:47.219175','861501506eab1ec4d1b6f10691829d899eac2a24200270d6bd1fa8297592a6bb','refresh','pranavk105@gmail.com'),(128,'2026-05-04 11:47:55.000000','2026-04-27 11:51:25.945821','76081e32e89618d39788b7cd6195f3ebae2734d7feaef4b58c4df2ac89188880','refresh','priya@gmail.com'),(130,'2026-05-04 11:51:34.000000','2026-04-27 11:51:58.264134','bababd39239de0d4ba0285ad3ee0df7645a9f0159f50d27ffbbfd7793e0771f9','refresh','pranavk105@gmail.com'),(132,'2026-05-04 11:52:08.000000','2026-04-27 12:05:17.732371','18763e9eab47a4e0f90c0783ef73285c0cfa3aa66289c64fbec40570255d2267','refresh','priya@gmail.com'),(134,'2026-05-04 12:16:29.000000','2026-04-27 12:16:36.849547','a2e7f8689305c1a987c9c9c8bf7b95bef616ba5a56da9b7138311a035278a8cf','refresh','2k22it20@kiot.ac.in'),(136,'2026-05-04 13:54:28.000000','2026-04-27 13:55:21.310744','a73f068b046f5974ad1a0f8f2be7528669ef2bbd0bd388cacfcd4e87e72cefa8','refresh','priya@gmail.com'),(137,'2026-05-04 13:55:27.000000','2026-04-28 12:09:57.276524','6fe9ddc7388e6ec69094c0753c3a335c817da32c4f7b00c5dc38ce72a94fa652','refresh','pranavk105@gmail.com'),(139,'2026-05-05 14:23:14.000000','2026-04-28 14:23:31.789723','d6a03865a5cef2280f36bf4298d332f354c60eedc58b7f907a5b7d0bfacb14be','refresh','pranavk105@gmail.com'),(140,'2026-04-28 17:30:11.000000','2026-04-28 16:35:23.963404','8c5ca242983e79b70cf191e8b243db94589dd6363f8d2710f90e26ec63891d43','access','priya@gmail.com'),(141,'2026-05-05 16:30:11.000000','2026-04-28 16:35:23.982015','dcdeffa6fc8eda32a593ffc19e6007cc99ff52084cfa342e61b713bcd166ec8c','refresh','priya@gmail.com'),(142,'2026-04-28 17:35:30.000000','2026-04-28 16:42:06.473484','97a152213d54744f6040c1d08951defb6e7f2bb2849781f513079984f50a432f','access','pranavk105@gmail.com'),(143,'2026-05-05 16:35:30.000000','2026-04-28 16:42:06.485091','321e24bf707ee1ab71db1ac027c9598cdcf7d62602f7ce93eebdb20f61cf1c48','refresh','pranavk105@gmail.com');
/*!40000 ALTER TABLE `revoked_tokens` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tickets`
--

DROP TABLE IF EXISTS `tickets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tickets` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `admin_response` varchar(3000) DEFAULT NULL,
  `category` varchar(50) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `message` varchar(3000) NOT NULL,
  `priority` enum('LOW','MEDIUM','HIGH','URGENT') NOT NULL,
  `related_order_number` varchar(50) DEFAULT NULL,
  `status` enum('OPEN','IN_PROGRESS','RESOLVED','CLOSED') NOT NULL,
  `subject` varchar(255) NOT NULL,
  `ticket_number` varchar(255) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_4ks48wgrew48dpkh0wd1rbe2b` (`ticket_number`),
  KEY `FK4eqsebpimnjen0q46ja6fl2hl` (`user_id`),
  CONSTRAINT `FK4eqsebpimnjen0q46ja6fl2hl` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tickets`
--

LOCK TABLES `tickets` WRITE;
/*!40000 ALTER TABLE `tickets` DISABLE KEYS */;
INSERT INTO `tickets` VALUES (1,'It will be checked','Delivery Issue','2026-04-15 15:26:59.347236','My order status has not changed for a long time','MEDIUM',NULL,'RESOLVED','Order status','TKT-81D9F633','2026-04-15 15:30:24.764911',2);
/*!40000 ALTER TABLE `tickets` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `active` bit(1) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `email` varchar(100) NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `phone` varchar(10) NOT NULL,
  `role` enum('CUSTOMER','ADMIN') NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_6dotkott2kjsp8vw4d0m25fb7` (`email`),
  UNIQUE KEY `UK_du5v5sr43g5bfnji4vb8hg5s3` (`phone`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,_binary '','2026-04-01 08:14:56.872116','pranavk105@gmail.com','Pranav','Kumar','$2a$10$KQBDJhZgnXtkpZG5.TrE.OSgWwqJb9Lyg5cfzvhjdF9VqehhlaxFW','8072792461','ADMIN','2026-04-26 22:53:32.422902'),(2,_binary '','2026-04-02 16:02:00.643508','priya@gmail.com','Priyangga','Thangaraj','$2a$10$oiOsFiPL5OrnDN9.GTVDwuveCr.gM0Vu5bUVYs.PH0xRSqbKw7Ose','9361284505','CUSTOMER','2026-04-15 14:12:55.154524'),(3,_binary '','2026-04-02 16:57:38.736653','guhan@gmail.com','Guhan','S G','$2a$10$RX7DIE3Wh19ChcKbNPxsteT/LH0ILlVcgz.tGaJu/y6.wJm0R5Ri2','9443213229','CUSTOMER','2026-04-02 16:57:38.736653'),(4,_binary '','2026-04-13 10:14:55.455684','sanjith@gmail.com','Sanjith','Raja','$2a$10$HC54TFYH5AcIqVYcl774b.KA5tl7KplTuIDD4tz8oshNhasHOzpKm','9480703973','CUSTOMER','2026-04-13 10:14:55.455684'),(5,_binary '','2026-04-15 06:38:00.284560','santhosh@gmail.com','Santhosh','Selvam','$2a$10$V6dXeW1RC0qWXTzw/n1aZOETejd3qk.sH1dysmDSkbQpCMJ9sZhby','9025656455','CUSTOMER','2026-04-15 06:38:00.284560'),(6,_binary '','2026-04-15 16:43:06.782861','jeeva@gmail.com','Jeeva','Sugin','$2a$10$oBQvFaqIHFDTWbN20Fj5u.5rc50/BzQfu2eOYYh.dugX3pzmI7Dmq','9356487215','CUSTOMER','2026-04-15 16:43:06.782861'),(7,_binary '','2026-04-15 16:44:28.977423','riddhi@gmail.com','Riddhi','Chawla','$2a$10$kCB3Co/adr/hw5P.2gFE2uhkK6a02bhvMBGrx/AtaW1W/q/1MEONG','9874563214','CUSTOMER','2026-04-20 21:07:18.406426'),(8,_binary '','2026-04-22 15:31:14.566483','arya@gmail.com','Pratisha','Arya','$2a$10$GwMsKyCeWYDFoYogsUE.HeNmy6BJS3V.frLTtq0C55k49lKjXTECC','9999955555','CUSTOMER','2026-04-22 15:31:14.566483'),(9,_binary '','2026-04-27 09:54:00.432643','2k22it20@kiot.ac.in','Kavinkumar','Raja','$2a$10$TiKwUQui4UigCfvR3vdsPuJK0TW2P3nmHDxVSlvJBBarrvC76tRsm','8610715319','CUSTOMER','2026-04-27 12:16:09.530743');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wishlist_items`
--

DROP TABLE IF EXISTS `wishlist_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wishlist_items` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `product_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKqxj7lncd242b59fb78rqegyxj` (`product_id`),
  KEY `FKmmj2k1i459yu449k3h1vx5abp` (`user_id`),
  CONSTRAINT `FKmmj2k1i459yu449k3h1vx5abp` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKqxj7lncd242b59fb78rqegyxj` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wishlist_items`
--

LOCK TABLES `wishlist_items` WRITE;
/*!40000 ALTER TABLE `wishlist_items` DISABLE KEYS */;
INSERT INTO `wishlist_items` VALUES (1,'2026-04-20 16:36:46.161500',24,2);
/*!40000 ALTER TABLE `wishlist_items` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-04-29 10:50:55
