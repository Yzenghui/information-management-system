-- 创建数据库（如果不存在）
CREATE DATABASE IF NOT EXISTS ims_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;

-- 切换到该数据库
USE ims_db;

CREATE TABLE `user`
(
    `id`          int          NOT NULL AUTO_INCREMENT,
    `username`    varchar(50)  NOT NULL UNIQUE COMMENT '用户名',
    `password`    varchar(100) NOT NULL COMMENT '密码（加密后）',
    `role`        varchar(20) DEFAULT 'USER' COMMENT '角色：ADMIN, USER',
    `status`      tinyint     DEFAULT '1' COMMENT '状态：1正常，0禁用',
    `create_time` datetime    DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='用户表';

-- 插入测试用户（密码都是 123456 的BCrypt加密结果）
INSERT INTO `user` (`username`, `password`, `role`)
VALUES ('admin', '$2a$10$0ccIzO53Jb7qp24U.ew5sOyWTp09JPNSxVxFD9buhLSuf26mT1r.q', 'ADMIN'),
       ('teacher_li', '$2a$10$0ccIzO53Jb7qp24U.ew5sOyWTp09JPNSxVxFD9buhLSuf26mT1r.q', 'TEACHER'),
       ('student_zhang', '$2a$10$0ccIzO53Jb7qp24U.ew5sOyWTp09JPNSxVxFD9buhLSuf26mT1r.q', 'STUDENT');

CREATE TABLE `student`
(
    `id`          int         NOT NULL AUTO_INCREMENT,
    `student_id`  varchar(20) NOT NULL UNIQUE COMMENT '学号',
    `name`        varchar(50) NOT NULL COMMENT '姓名',
    `gender`      varchar(10)  DEFAULT NULL COMMENT '性别：男,女',
    `major`       varchar(100) DEFAULT NULL COMMENT '专业',
    `address`     varchar(200) DEFAULT NULL COMMENT '籍贯',
    `create_time` datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='学生表';

-- 插入一些学生测试数据
INSERT INTO `student` (`student_id`, `name`, `gender`, `major`, `address`)
VALUES ('20230001', '张三', '男', '计算机科学与技术', '北京市海淀区'),
       ('20230002', '李四', '女', '软件工程', '上海市浦东新区'),
       ('20230003', '王五', '男', '电子信息工程', '广东省深圳市');

CREATE TABLE `teacher`
(
    `id`          int         NOT NULL AUTO_INCREMENT,
    `teacher_id`  varchar(20) NOT NULL UNIQUE COMMENT '工号',
    `name`        varchar(50) NOT NULL COMMENT '姓名',
    `gender`      varchar(10)  DEFAULT NULL COMMENT '性别：男,女',
    `subject`     varchar(100) DEFAULT NULL COMMENT '所授学科',
    `address`     varchar(200) DEFAULT NULL COMMENT '籍贯',
    `create_time` datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='教师表';

-- 插入一些教师测试数据
INSERT INTO `teacher` (`teacher_id`, `name`, `gender`, `subject`, `address`)
VALUES ('T1001', '李教授', '男', '计算机科学', '北京市海淀区'),
       ('T1002', '王教授', '女', '软件工程', '上海市浦东新区');