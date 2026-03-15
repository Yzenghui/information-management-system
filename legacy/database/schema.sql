--Create_accounts_table
CREATE TABLE `accounts` (
  `username` varchar(50) DEFAULT NULL COMMENT '用户名',
  `password` varchar(50) DEFAULT NULL COMMENT '密码',
  UNIQUE KEY `UNIQUE` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户表'

--Create_students_table
CREATE TABLE `students` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '序号',
  `stu_id` varchar(30) DEFAULT NULL COMMENT '学号',
  `name` varchar(50) DEFAULT NULL COMMENT '姓名',
  `gender` varchar(10) DEFAULT NULL COMMENT '性别',
  `major` varchar(50) DEFAULT NULL COMMENT '专业',
  `profession` varchar(30) DEFAULT NULL COMMENT '职业',
  `address` varchar(20) DEFAULT NULL COMMENT '地址',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UNIQUE` (`stu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='信息表'

--Create_teachers_table
CREATE TABLE `teachers` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '序号',
  `tea_id` varchar(30) DEFAULT NULL COMMENT '工号',
  `name` varchar(50) DEFAULT NULL COMMENT '姓名',
  `gender` varchar(10) DEFAULT NULL COMMENT '性别',
  `subject` varchar(50) DEFAULT NULL COMMENT '所授学科',
  `profession` varchar(30) DEFAULT NULL COMMENT '职业',
  `address` varchar(50) DEFAULT NULL COMMENT '地址',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UNIQUE` (`tea_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci