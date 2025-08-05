-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versión del servidor:         8.0.41 - MySQL Community Server - GPL
-- SO del servidor:              Win64
-- HeidiSQL Versión:             12.11.0.7065
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Volcando estructura de base de datos para hardware_store
CREATE DATABASE IF NOT EXISTS `hardware_store` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `hardware_store`;

-- Volcando estructura para tabla hardware_store.client
CREATE TABLE IF NOT EXISTS `client` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `phone` varchar(15) DEFAULT NULL,
  `address` varchar(200) DEFAULT NULL,
  `email` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `date_client` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla hardware_store.client: ~3 rows (aproximadamente)
INSERT INTO `client` (`id`, `name`, `phone`, `address`, `email`, `date_client`) VALUES
	(1, 'Ana Torres', '3201111111', 'Calle 50 #25-30', 'ana@email.com', '2024-01-15'),
	(2, 'Luis Martín', '3202222222', 'Carrera 15 #40-20', 'luis@email.com', '2024-02-10'),
	(3, 'Sofia López', '3203333333', 'Avenida 6 #30-45', 'sofia@email.com', '2024-03-05');

-- Volcando estructura para tabla hardware_store.employee
CREATE TABLE IF NOT EXISTS `employee` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `position` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `salary` float DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla hardware_store.employee: ~3 rows (aproximadamente)
INSERT INTO `employee` (`id`, `name`, `position`, `salary`) VALUES
	(1, 'Juan Pérez', 'Vendedor', 2500000),
	(2, 'María González', 'Administrador', 3500000),
	(3, 'Carlos Rodríguez', 'Cajero', 2200000);

-- Volcando estructura para tabla hardware_store.entry
CREATE TABLE IF NOT EXISTS `entry` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `id_inventory` bigint DEFAULT NULL,
  `id_supplier` bigint DEFAULT NULL,
  `amount` int DEFAULT NULL,
  `price_buy` float DEFAULT NULL,
  `date_entry` date DEFAULT NULL,
  `id_employee` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_entry_inventory` (`id_inventory`),
  KEY `FK_entry_supplier` (`id_supplier`),
  KEY `FK_entry_employee` (`id_employee`),
  CONSTRAINT `FK_entry_employee` FOREIGN KEY (`id_employee`) REFERENCES `employee` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_entry_inventory` FOREIGN KEY (`id_inventory`) REFERENCES `inventory` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_entry_supplier` FOREIGN KEY (`id_supplier`) REFERENCES `supplier` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla hardware_store.entry: ~0 rows (aproximadamente)

-- Volcando estructura para tabla hardware_store.inventory
CREATE TABLE IF NOT EXISTS `inventory` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `category` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `price` float DEFAULT NULL,
  `stock` int DEFAULT '0',
  `id_supplier` bigint DEFAULT NULL,
  `min_stock` int DEFAULT '10',
  PRIMARY KEY (`id`),
  KEY `idx_inventory_supplier` (`id_supplier`),
  CONSTRAINT `inventory_ibfk_1` FOREIGN KEY (`id_supplier`) REFERENCES `supplier` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla hardware_store.inventory: ~5 rows (aproximadamente)
INSERT INTO `inventory` (`id`, `name`, `category`, `price`, `stock`, `id_supplier`, `min_stock`) VALUES
	(1, 'Martillo 16oz', 'Herramientas', 45000, 25, 1, 10),
	(2, 'Pintura Blanca 1L', 'Pinturas', 28000, 50, 3, 10),
	(3, 'Cemento 50kg', 'Construcción', 18000, 100, 2, 10),
	(4, 'Destornillador Phillips', 'Herramientas', 12000, 30, 1, 10),
	(5, 'Brocha 3 pulgadas', 'Pinturas', 8500, 40, 3, 10);

-- Volcando estructura para tabla hardware_store.issue
CREATE TABLE IF NOT EXISTS `issue` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `id_inventory` bigint DEFAULT NULL,
  `amount` int DEFAULT NULL,
  `reason` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `date_issue` date DEFAULT NULL,
  `id_employee` bigint DEFAULT NULL,
  `observation` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_issue_date` (`date_issue`),
  KEY `issue_ibfk_1` (`id_inventory`),
  KEY `issue_ibfk_2` (`id_employee`),
  CONSTRAINT `issue_ibfk_1` FOREIGN KEY (`id_inventory`) REFERENCES `inventory` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `issue_ibfk_2` FOREIGN KEY (`id_employee`) REFERENCES `employee` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla hardware_store.issue: ~0 rows (aproximadamente)

-- Volcando estructura para tabla hardware_store.order_buy
CREATE TABLE IF NOT EXISTS `order_buy` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `id_client` bigint DEFAULT NULL,
  `id_employee` bigint DEFAULT NULL,
  `date_order` date DEFAULT NULL,
  `total` float DEFAULT NULL,
  `status` varchar(20) DEFAULT 'PENDING',
  PRIMARY KEY (`id`),
  KEY `idx_order_client` (`id_client`),
  KEY `idx_order_employee` (`id_employee`),
  CONSTRAINT `order_buy_ibfk_1` FOREIGN KEY (`id_client`) REFERENCES `client` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `order_buy_ibfk_2` FOREIGN KEY (`id_employee`) REFERENCES `employee` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla hardware_store.order_buy: ~0 rows (aproximadamente)

-- Volcando estructura para tabla hardware_store.order_detail
CREATE TABLE IF NOT EXISTS `order_detail` (
  `id_order_buy` bigint DEFAULT NULL,
  `id_inventory` bigint DEFAULT NULL,
  `amount` int DEFAULT NULL,
  `price_unit` float DEFAULT NULL,
  KEY `order_detail_ibfk_1` (`id_order_buy`),
  KEY `order_detail_ibfk_2` (`id_inventory`),
  CONSTRAINT `order_detail_ibfk_1` FOREIGN KEY (`id_order_buy`) REFERENCES `order_buy` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `order_detail_ibfk_2` FOREIGN KEY (`id_inventory`) REFERENCES `inventory` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla hardware_store.order_detail: ~0 rows (aproximadamente)

-- Volcando estructura para tabla hardware_store.sale
CREATE TABLE IF NOT EXISTS `sale` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `id_client` bigint DEFAULT NULL,
  `id_employee` bigint DEFAULT NULL,
  `date_sale` date DEFAULT NULL,
  `total` float DEFAULT NULL,
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_sale_client` (`id_client`),
  KEY `idx_sale_employee` (`id_employee`),
  CONSTRAINT `sale_ibfk_1` FOREIGN KEY (`id_client`) REFERENCES `client` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `sale_ibfk_2` FOREIGN KEY (`id_employee`) REFERENCES `employee` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla hardware_store.sale: ~0 rows (aproximadamente)

-- Volcando estructura para tabla hardware_store.sale_detail
CREATE TABLE IF NOT EXISTS `sale_detail` (
  `id_sale` bigint DEFAULT NULL,
  `id_inventory` bigint DEFAULT NULL,
  `amount` int DEFAULT NULL,
  `price_unit` float DEFAULT NULL,
  KEY `sale_detail_ibfk_1` (`id_sale`),
  KEY `sale_detail_ibfk_2` (`id_inventory`),
  CONSTRAINT `sale_detail_ibfk_1` FOREIGN KEY (`id_sale`) REFERENCES `sale` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `sale_detail_ibfk_2` FOREIGN KEY (`id_inventory`) REFERENCES `inventory` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla hardware_store.sale_detail: ~0 rows (aproximadamente)

-- Volcando estructura para tabla hardware_store.supplier
CREATE TABLE IF NOT EXISTS `supplier` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `phone` varchar(15) DEFAULT NULL,
  `supplied_product` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla hardware_store.supplier: ~3 rows (aproximadamente)
INSERT INTO `supplier` (`id`, `name`, `phone`, `supplied_product`) VALUES
	(1, 'Ferretería Central', 3001234567, 'Herramientas'),
	(2, 'Distribuidora Norte', 3009876543, 'Materiales de construcción'),
	(3, 'Pinturas del Valle', 3005555555, 'Pinturas y químicos');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;

