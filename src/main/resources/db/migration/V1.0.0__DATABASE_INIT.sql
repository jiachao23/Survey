CREATE TABLE `student` (
   `id` int NOT NULL AUTO_INCREMENT,
   `class_name` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
   `date` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
   `read_count` bigint DEFAULT NULL,
   `username` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
   `comment` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
   PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10674 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;