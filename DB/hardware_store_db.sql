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
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `date_client` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla hardware_store.client: ~3 rows (aproximadamente)
INSERT INTO `client` (`id`, `name`, `phone`, `address`, `email`, `date_client`) VALUES
	(2, 'Luis Martín', '3202222222', 'Carrera 15 #40-20', 'luis@email.com', '2025-08-08'),
	(3, 'Sofia Lópezz', '3203333333', 'Avenida 6 #30-45', 'sofia@email.com', '2024-02-05'),
	(7, 'nini', '318-283-2748', 'cra30#15-10', 'vanme2669@gmail.com', '2025-08-10');

-- Volcando estructura para tabla hardware_store.employee
CREATE TABLE IF NOT EXISTS `employee` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `position` varchar(255) DEFAULT NULL,
  `salary` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

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
  `price_buy` int DEFAULT NULL,
  `date_entry` datetime(6) DEFAULT NULL,
  `id_employee` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_entry_inventory` (`id_inventory`),
  KEY `FK_entry_supplier` (`id_supplier`),
  KEY `FK_entry_employee` (`id_employee`),
  CONSTRAINT `FK_entry_employee` FOREIGN KEY (`id_employee`) REFERENCES `employee` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_entry_inventory` FOREIGN KEY (`id_inventory`) REFERENCES `inventory` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_entry_supplier` FOREIGN KEY (`id_supplier`) REFERENCES `supplier` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla hardware_store.entry: ~5 rows (aproximadamente)
INSERT INTO `entry` (`id`, `id_inventory`, `id_supplier`, `amount`, `price_buy`, `date_entry`, `id_employee`) VALUES
	(1, 1, 1, 15, 40000, '2025-08-01 00:00:00.000000', 2),
	(2, 2, 3, 30, 25000, '2025-08-02 00:00:00.000000', 1),
	(3, 3, 2, 50, 16000, '2025-08-03 00:00:00.000000', 3),
	(4, 4, 1, 20, 10000, '2025-08-04 00:00:00.000000', 2),
	(5, 5, 3, 40, 7500, '2025-08-05 00:00:00.000000', 1);

-- Volcando estructura para tabla hardware_store.inventory
CREATE TABLE IF NOT EXISTS `inventory` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `category` varchar(255) DEFAULT NULL,
  `price` int DEFAULT NULL,
  `stock` int DEFAULT '0',
  `id_supplier` bigint DEFAULT NULL,
  `min_stock` int DEFAULT '10',
  PRIMARY KEY (`id`),
  KEY `idx_inventory_supplier` (`id_supplier`),
  CONSTRAINT `inventory_ibfk_1` FOREIGN KEY (`id_supplier`) REFERENCES `supplier` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla hardware_store.inventory: ~5 rows (aproximadamente)
INSERT INTO `inventory` (`id`, `name`, `category`, `price`, `stock`, `id_supplier`, `min_stock`) VALUES
	(1, 'Martillo 16oz', 'Herramientas', 45000, 27, 1, 10),
	(2, 'Pintura Blanca 1L', 'Pinturas', 28000, 50, 3, 10),
	(3, 'Cemento 50kg', 'Construcción', 18000, 100, 2, 10),
	(4, 'Destornillador Phillips', 'Herramientas', 12000, 30, 1, 10),
	(5, 'Brocha 3 pulgadas', 'Pinturas', 8500, 40, 3, 10);

-- Volcando estructura para tabla hardware_store.issue
CREATE TABLE IF NOT EXISTS `issue` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `id_inventory` bigint DEFAULT NULL,
  `amount` int DEFAULT NULL,
  `reason` varchar(255) DEFAULT NULL,
  `date_issue` datetime(6) DEFAULT NULL,
  `id_employee` bigint DEFAULT NULL,
  `observation` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_issue_date` (`date_issue`),
  KEY `issue_ibfk_1` (`id_inventory`),
  KEY `issue_ibfk_2` (`id_employee`),
  CONSTRAINT `issue_ibfk_1` FOREIGN KEY (`id_inventory`) REFERENCES `inventory` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `issue_ibfk_2` FOREIGN KEY (`id_employee`) REFERENCES `employee` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla hardware_store.issue: ~5 rows (aproximadamente)
INSERT INTO `issue` (`id`, `id_inventory`, `amount`, `reason`, `date_issue`, `id_employee`, `observation`) VALUES
	(1, 1, 5, 'Venta mostrador', '2025-08-06 00:00:00.000000', 1, 'Cliente compró martillos para construcción'),
	(2, 2, 10, 'Venta a cliente frecuente', '2025-08-06 00:00:00.000000', 3, 'Descuento aplicado por fidelidad'),
	(3, 3, 20, 'Venta mostrador', '2025-08-07 00:00:00.000000', 1, 'Cemento para obra residencial'),
	(4, 4, 3, 'Préstamo interno', '2025-08-08 00:00:00.000000', 2, 'Herramienta prestada para reparaciones internas'),
	(5, 5, 8, 'Venta mostrador', '2025-08-08 00:00:00.000000', 3, 'Brochas para pintura de fachada');

-- Volcando estructura para tabla hardware_store.order_buy
CREATE TABLE IF NOT EXISTS `order_buy` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `id_client` bigint DEFAULT NULL,
  `id_employee` bigint DEFAULT NULL,
  `date_order` date DEFAULT NULL,
  `total` int DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_order_client` (`id_client`),
  KEY `idx_order_employee` (`id_employee`),
  CONSTRAINT `order_buy_ibfk_1` FOREIGN KEY (`id_client`) REFERENCES `client` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `order_buy_ibfk_2` FOREIGN KEY (`id_employee`) REFERENCES `employee` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla hardware_store.order_buy: ~2 rows (aproximadamente)
INSERT INTO `order_buy` (`id`, `id_client`, `id_employee`, `date_order`, `total`, `status`) VALUES
	(1, 2, 2, '2025-08-01', 600000, 'RECEIVED'),
	(2, 3, 1, '2025-08-03', 800000, 'ENVIADO');

-- Volcando estructura para tabla hardware_store.order_detail
CREATE TABLE IF NOT EXISTS `order_detail` (
  `id_order_buy` bigint DEFAULT NULL,
  `id_inventory` bigint DEFAULT NULL,
  `amount` int DEFAULT NULL,
  `price_unit` int DEFAULT NULL,
  KEY `order_detail_ibfk_1` (`id_order_buy`),
  KEY `order_detail_ibfk_2` (`id_inventory`),
  CONSTRAINT `order_detail_ibfk_1` FOREIGN KEY (`id_order_buy`) REFERENCES `order_buy` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `order_detail_ibfk_2` FOREIGN KEY (`id_inventory`) REFERENCES `inventory` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla hardware_store.order_detail: ~4 rows (aproximadamente)
INSERT INTO `order_detail` (`id_order_buy`, `id_inventory`, `amount`, `price_unit`) VALUES
	(1, 1, 15, 40000),
	(1, 4, 20, 10000),
	(2, 3, 50, 16000),
	(2, 2, 30, 25000);

-- Volcando estructura para tabla hardware_store.password_reset_tokens
CREATE TABLE IF NOT EXISTS `password_reset_tokens` (
  `email` varchar(255) NOT NULL,
  `token` varchar(255) NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla hardware_store.password_reset_tokens: ~0 rows (aproximadamente)

-- Volcando estructura para tabla hardware_store.personal_access_tokens
CREATE TABLE IF NOT EXISTS `personal_access_tokens` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `tokenable_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `tokenable_id` bigint unsigned NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `token` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `abilities` text,
  `last_used_at` timestamp NULL DEFAULT NULL,
  `expires_at` timestamp NULL DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `update_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `token` (`token`),
  KEY `tokenable_type` (`tokenable_type`),
  KEY `tokenable_id` (`tokenable_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla hardware_store.personal_access_tokens: ~0 rows (aproximadamente)

-- Volcando estructura para tabla hardware_store.role
CREATE TABLE IF NOT EXISTS `role` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla hardware_store.role: ~3 rows (aproximadamente)
INSERT INTO `role` (`id`, `name`, `description`) VALUES
	(1, 'ADMIN', 'Administrador del sistema'),
	(2, 'EMPLOYEE', 'Empleado vendedor'),
	(3, 'MANAGER', 'Gerente de la ferretería');

-- Volcando estructura para tabla hardware_store.sale
CREATE TABLE IF NOT EXISTS `sale` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `id_client` bigint DEFAULT NULL,
  `id_employee` bigint DEFAULT NULL,
  `date_sale` date DEFAULT NULL,
  `total` int DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_sale_client` (`id_client`),
  KEY `idx_sale_employee` (`id_employee`),
  CONSTRAINT `sale_ibfk_1` FOREIGN KEY (`id_client`) REFERENCES `client` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `sale_ibfk_2` FOREIGN KEY (`id_employee`) REFERENCES `employee` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla hardware_store.sale: ~2 rows (aproximadamente)
INSERT INTO `sale` (`id`, `id_client`, `id_employee`, `date_sale`, `total`, `status`) VALUES
	(16, 2, 1, '2025-08-06', 325000, 'PENDIENTE'),
	(17, 3, 3, '2025-08-07', 360000, 'COMPLETADA');

-- Volcando estructura para tabla hardware_store.sale_detail
CREATE TABLE IF NOT EXISTS `sale_detail` (
  `id_sale` bigint DEFAULT NULL,
  `id_inventory` bigint DEFAULT NULL,
  `amount` int DEFAULT NULL,
  `price_unit` int DEFAULT NULL,
  KEY `sale_detail_ibfk_1` (`id_sale`),
  KEY `sale_detail_ibfk_2` (`id_inventory`),
  CONSTRAINT `sale_detail_ibfk_1` FOREIGN KEY (`id_sale`) REFERENCES `sale` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `sale_detail_ibfk_2` FOREIGN KEY (`id_inventory`) REFERENCES `inventory` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla hardware_store.sale_detail: ~0 rows (aproximadamente)
INSERT INTO `sale_detail` (`id_sale`, `id_inventory`, `amount`, `price_unit`) VALUES
	(16, 3, 5, 1500);

-- Volcando estructura para tabla hardware_store.supplier
CREATE TABLE IF NOT EXISTS `supplier` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `supplied_product` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla hardware_store.supplier: ~3 rows (aproximadamente)
INSERT INTO `supplier` (`id`, `name`, `phone`, `supplied_product`) VALUES
	(1, 'Ferretería Central', '3001234567', 'Herramientas'),
	(2, 'Distribuidora Norte', '3009876543', 'Materiales de construcción'),
	(3, 'Pinturas del Valle', '3005555555', 'Pinturas y químicos');

-- Volcando estructura para tabla hardware_store.users
CREATE TABLE IF NOT EXISTS `users` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `role_id` bigint unsigned NOT NULL,
  `remember_token` varchar(100) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `update_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`),
  KEY `FK_users_role` (`role_id`),
  CONSTRAINT `FK_users_role` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla hardware_store.users: ~0 rows (aproximadamente)

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
