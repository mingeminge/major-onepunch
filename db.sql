/*
 Navicat Premium Data Transfer

 Source Server         : 我的MAC
 Source Server Type    : MySQL
 Source Server Version : 50724
 Source Host           : localhost:3306
 Source Schema         : yc

 Target Server Type    : MySQL
 Target Server Version : 50724
 File Encoding         : 65001

 Date: 20/06/2019 20:39:38
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_authority
-- ----------------------------
DROP TABLE IF EXISTS `tb_authority`;
CREATE TABLE "tb_authority" (
  "id" int(11) NOT NULL AUTO_INCREMENT,
  "authority_name" varchar(20) DEFAULT NULL,
  "authority_url" varchar(100) DEFAULT NULL,
  "remark" varchar(255) DEFAULT NULL,
  "parent_id" int(11) DEFAULT NULL,
  PRIMARY KEY ("id")
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_authority
-- ----------------------------
BEGIN;
INSERT INTO `tb_authority` VALUES (1, '系统管理-用户查询', 'sysUser:get', '系统管理查询功能', 18);
INSERT INTO `tb_authority` VALUES (2, '系统用户修改', 'sysUser:update', NULL, 18);
INSERT INTO `tb_authority` VALUES (3, '系统用户添加', 'sysUser:add', NULL, 18);
INSERT INTO `tb_authority` VALUES (4, '系统用户删除', 'sysUser:delete', NULL, 18);
INSERT INTO `tb_authority` VALUES (5, '系统角色查询', 'sysRole:get', NULL, 19);
INSERT INTO `tb_authority` VALUES (6, '系统角色修改', 'sysRole:update', NULL, 19);
INSERT INTO `tb_authority` VALUES (7, '系统角色添加', 'sysRole:add', NULL, 19);
INSERT INTO `tb_authority` VALUES (8, '系统角色删除', 'sysRole:delete', NULL, 19);
INSERT INTO `tb_authority` VALUES (9, '系统权限查询', 'sysAuthority:get', NULL, 20);
INSERT INTO `tb_authority` VALUES (10, '系统权限添加', 'sysAuthority:add', NULL, 20);
INSERT INTO `tb_authority` VALUES (11, '系统权限修改', 'sysAuthority:update', NULL, 20);
INSERT INTO `tb_authority` VALUES (12, '系统权限删除', 'sysAuthority:delete', NULL, 20);
INSERT INTO `tb_authority` VALUES (13, '系统日志查询', 'sysLog:get', NULL, 21);
INSERT INTO `tb_authority` VALUES (14, '系统日志删除', 'sysLog:delete', NULL, 21);
INSERT INTO `tb_authority` VALUES (18, '用户管理', NULL, NULL, 0);
INSERT INTO `tb_authority` VALUES (19, '角色管理', NULL, NULL, 0);
INSERT INTO `tb_authority` VALUES (20, '权限管理', NULL, NULL, 0);
INSERT INTO `tb_authority` VALUES (21, '日志管理', NULL, NULL, 0);
INSERT INTO `tb_authority` VALUES (22, '日志修改', 'sysLog:update', NULL, 21);
INSERT INTO `tb_authority` VALUES (23, '测试', 'sys:test', NULL, NULL);
INSERT INTO `tb_authority` VALUES (24, '测试', 'aa:bb', NULL, 0);
INSERT INTO `tb_authority` VALUES (25, '在线用户', 'sysOnlineUser', NULL, 0);
INSERT INTO `tb_authority` VALUES (26, '查询', 'sysOnlineUser:get', NULL, 25);
INSERT INTO `tb_authority` VALUES (27, '强制下线', 'sysOnlineUser:delete', NULL, 25);
INSERT INTO `tb_authority` VALUES (28, '头像管理', 'sysImage', NULL, 0);
INSERT INTO `tb_authority` VALUES (29, '头像查看', 'sysImage:get', NULL, 28);
INSERT INTO `tb_authority` VALUES (30, '头像编辑', 'sysImage:update', NULL, 28);
INSERT INTO `tb_authority` VALUES (31, '头像删除', 'sysImage:delete', NULL, 28);
INSERT INTO `tb_authority` VALUES (32, '头像上传', 'sysImage:upload', NULL, 28);
COMMIT;

-- ----------------------------
-- Table structure for tb_image
-- ----------------------------
DROP TABLE IF EXISTS `tb_image`;
CREATE TABLE "tb_image" (
  "id" int(11) NOT NULL AUTO_INCREMENT,
  "img_url" varchar(255) DEFAULT NULL,
  "description" varchar(255) DEFAULT NULL,
  "type" int(10) DEFAULT NULL,
  "name" varchar(50) DEFAULT NULL,
  PRIMARY KEY ("id")
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_image
-- ----------------------------
BEGIN;
INSERT INTO `tb_image` VALUES (2, 'IMG_2.jpg', '呀屎啦累', 0, NULL);
INSERT INTO `tb_image` VALUES (3, 'IMG_3.jpg', NULL, 0, NULL);
INSERT INTO `tb_image` VALUES (4, 'IMG_4.jpg', NULL, 0, NULL);
INSERT INTO `tb_image` VALUES (5, 'IMG_5.jpg', NULL, 0, NULL);
INSERT INTO `tb_image` VALUES (6, 'IMG_6.jpg', NULL, 0, NULL);
INSERT INTO `tb_image` VALUES (7, 'IMG_7.jpg', NULL, 0, NULL);
INSERT INTO `tb_image` VALUES (8, 'IMG_8.jpg', NULL, 0, NULL);
INSERT INTO `tb_image` VALUES (9, 'IMG_9.jpg', NULL, 0, NULL);
INSERT INTO `tb_image` VALUES (10, 'IMG_10.jpg', NULL, 0, NULL);
INSERT INTO `tb_image` VALUES (11, 'IMG_11.jpg', NULL, 0, NULL);
INSERT INTO `tb_image` VALUES (12, 'IMG_12.jpg', NULL, 0, NULL);
INSERT INTO `tb_image` VALUES (13, 'IMG_13.jpg', NULL, 0, NULL);
INSERT INTO `tb_image` VALUES (14, 'IMG_14.jpg', NULL, 0, NULL);
INSERT INTO `tb_image` VALUES (15, 'IMG_15.jpg', NULL, 0, NULL);
INSERT INTO `tb_image` VALUES (16, 'IMG_16.jpg', NULL, 0, NULL);
INSERT INTO `tb_image` VALUES (17, 'IMG_17.jpg', NULL, 0, NULL);
INSERT INTO `tb_image` VALUES (18, 'IMG_18.png', NULL, 0, NULL);
INSERT INTO `tb_image` VALUES (19, 'IMG_19.jpg', NULL, 0, NULL);
INSERT INTO `tb_image` VALUES (28, '1560525321028.png', 'fff', 1, NULL);
INSERT INTO `tb_image` VALUES (29, '1560608657069.png', 'asad', 1, NULL);
INSERT INTO `tb_image` VALUES (30, '1560608690916.png', '11111', 1, NULL);
INSERT INTO `tb_image` VALUES (31, '1560697302070.png', 'sadsa', 1, NULL);
INSERT INTO `tb_image` VALUES (35, '1560781807449.png', '1321', 1, NULL);
INSERT INTO `tb_image` VALUES (39, '1560782199696.png', '2321', 1, NULL);
COMMIT;

-- ----------------------------
-- Table structure for tb_role
-- ----------------------------
DROP TABLE IF EXISTS `tb_role`;
CREATE TABLE "tb_role" (
  "id" int(11) NOT NULL AUTO_INCREMENT,
  "role_name" varchar(50) DEFAULT NULL,
  "description" varchar(50) DEFAULT NULL,
  "available" bit(1) DEFAULT NULL,
  PRIMARY KEY ("id"),
  UNIQUE KEY "UK_c9lijtmr0x68iu1vxftbu2u33" ("role_name")
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_role
-- ----------------------------
BEGIN;
INSERT INTO `tb_role` VALUES (1, 'ROLE_ADMIN', '最强的男人', b'1');
INSERT INTO `tb_role` VALUES (2, 'ROLE_S', 'S级英雄', b'1');
INSERT INTO `tb_role` VALUES (3, 'ROLE_A', 'A级英雄', b'1');
INSERT INTO `tb_role` VALUES (4, 'ROLE_B', 'B级英雄', b'1');
INSERT INTO `tb_role` VALUES (5, 'ROLE_C', 'C级英雄', b'1');
INSERT INTO `tb_role` VALUES (6, 'WEIRDO_GOD', '神级怪人', b'1');
INSERT INTO `tb_role` VALUES (7, 'WEIRDO_DRAGON', '龙级怪人', b'1');
INSERT INTO `tb_role` VALUES (8, 'WEIRDO_GHOST', '鬼级怪人', b'1');
INSERT INTO `tb_role` VALUES (9, 'WEIRDO_TOIGER', '虎级怪人1', b'1');
COMMIT;

-- ----------------------------
-- Table structure for tb_role_authority
-- ----------------------------
DROP TABLE IF EXISTS `tb_role_authority`;
CREATE TABLE "tb_role_authority" (
  "role_id" int(11) NOT NULL,
  "authority_id" int(11) NOT NULL,
  KEY "FKsvb5medi73lyl2kpda1a0vnve" ("authority_id"),
  KEY "FK7xaciludyipdeg7da7orll4di" ("role_id"),
  CONSTRAINT "FK7xaciludyipdeg7da7orll4di" FOREIGN KEY ("role_id") REFERENCES "tb_role" ("id"),
  CONSTRAINT "FKsvb5medi73lyl2kpda1a0vnve" FOREIGN KEY ("authority_id") REFERENCES "tb_authority" ("id")
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_role_authority
-- ----------------------------
BEGIN;
INSERT INTO `tb_role_authority` VALUES (1, 18);
INSERT INTO `tb_role_authority` VALUES (1, 1);
INSERT INTO `tb_role_authority` VALUES (1, 2);
INSERT INTO `tb_role_authority` VALUES (1, 3);
INSERT INTO `tb_role_authority` VALUES (1, 4);
INSERT INTO `tb_role_authority` VALUES (1, 19);
INSERT INTO `tb_role_authority` VALUES (1, 5);
INSERT INTO `tb_role_authority` VALUES (1, 6);
INSERT INTO `tb_role_authority` VALUES (1, 7);
INSERT INTO `tb_role_authority` VALUES (1, 8);
INSERT INTO `tb_role_authority` VALUES (1, 20);
INSERT INTO `tb_role_authority` VALUES (1, 9);
INSERT INTO `tb_role_authority` VALUES (1, 10);
INSERT INTO `tb_role_authority` VALUES (1, 11);
INSERT INTO `tb_role_authority` VALUES (1, 12);
INSERT INTO `tb_role_authority` VALUES (1, 21);
INSERT INTO `tb_role_authority` VALUES (1, 13);
INSERT INTO `tb_role_authority` VALUES (1, 14);
INSERT INTO `tb_role_authority` VALUES (1, 22);
INSERT INTO `tb_role_authority` VALUES (1, 25);
INSERT INTO `tb_role_authority` VALUES (1, 26);
INSERT INTO `tb_role_authority` VALUES (1, 27);
INSERT INTO `tb_role_authority` VALUES (1, 28);
INSERT INTO `tb_role_authority` VALUES (1, 29);
INSERT INTO `tb_role_authority` VALUES (1, 30);
INSERT INTO `tb_role_authority` VALUES (1, 31);
INSERT INTO `tb_role_authority` VALUES (1, 32);
INSERT INTO `tb_role_authority` VALUES (2, 1);
INSERT INTO `tb_role_authority` VALUES (2, 5);
INSERT INTO `tb_role_authority` VALUES (2, 9);
INSERT INTO `tb_role_authority` VALUES (2, 13);
INSERT INTO `tb_role_authority` VALUES (2, 26);
INSERT INTO `tb_role_authority` VALUES (2, 29);
INSERT INTO `tb_role_authority` VALUES (2, 32);
COMMIT;


-- ----------------------------
-- Table structure for tb_system_log
-- ----------------------------
DROP TABLE IF EXISTS `tb_system_log`;
CREATE TABLE "tb_system_log" (
  "id" int(11) NOT NULL AUTO_INCREMENT,
  "username" varchar(20) DEFAULT NULL,
  "module_name" varchar(20) DEFAULT NULL,
  "method_name" varchar(20) DEFAULT NULL,
  "request_url" varchar(255) DEFAULT NULL,
  "ip_address" varchar(100) DEFAULT NULL,
  "use_time" int(11) DEFAULT NULL,
  "is_error" int(11) DEFAULT NULL,
  "error_msg" text,
  "create_time" datetime DEFAULT NULL,
  PRIMARY KEY ("id")
) ENGINE=InnoDB AUTO_INCREMENT=1017 DEFAULT CHARSET=utf8;
-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE "tb_user" (
  "id" int(11) NOT NULL AUTO_INCREMENT,
  "username" varchar(50) DEFAULT NULL,
  "password" varchar(64) DEFAULT NULL,
  "phone" varchar(11) DEFAULT NULL,
  "email" varchar(50) DEFAULT NULL,
  "sex" int(11) DEFAULT NULL,
  "nick_name" varchar(20) DEFAULT NULL,
  "img_url" varchar(255) DEFAULT NULL,
  "signature" varchar(255) DEFAULT NULL,
  "create_time" datetime DEFAULT NULL,
  "update_time" datetime DEFAULT NULL,
  PRIMARY KEY ("id"),
  UNIQUE KEY "UK_4wv83hfajry5tdoamn8wsqa6x" ("username")
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
BEGIN;
INSERT INTO `tb_user` VALUES (1, 'admin', '$2a$10$ceJLxMSzdYlR5NDJbAZPLe/gWvdBcMwD6JPA2H.fj7VDqyOHRyp2C', '13066376564', '76589@qq.com', 1, '秃头披风侠', 'IMG_19.jpg', 'select * from girls where age between 18 and 24 and boyfriend is null', '2019-04-23 03:57:37', '2019-06-19 22:16:29');
INSERT INTO `tb_user` VALUES (9, 'king', '$2a$10$dVPRCmJTTJwc3oz84OLga.JCLOtQhEq6UYY6T3sFXn3aRAMyVgl6W', '13066376563', '2@qq.com', 1, 'king', 'IMG_13.jpg', NULL, '2019-05-21 04:52:10', '2019-06-15 17:21:37');
INSERT INTO `tb_user` VALUES (12, 'janos', '$2a$10$JgRV/1gyBXpUTJz48w/MDu7lD1PAeG2H0tKLWml2vdpK9oKbIj/N.', '12300000000', '2233@qq.com', 1, '魔鬼改造人', 'IMG_11.jpg', NULL, '2019-06-07 09:29:14', '2019-06-19 21:14:41');
INSERT INTO `tb_user` VALUES (13, 'HungryWolf', '$2a$10$fHDejLfI784ysvDfKBn1pewsJeyYGm7D3ggUq9LeL.XVByRn/Qbhm', '12122224444', '2222@qq.com', 1, '英雄狩猎者', NULL, NULL, '2019-06-14 00:02:37', '2019-06-16 08:03:40');
INSERT INTO `tb_user` VALUES (14, 'BangGu', '$2a$10$yA9ljqDEK0rsA19P/BQaAO83pkI.1LDR87d7HG6ZCZ3NeT/K8a8Jm', '11133334444', 'qqq@qq.com', 1, '银色獠牙', NULL, NULL, '2019-06-17 19:46:19', '2019-06-17 19:46:19');
INSERT INTO `tb_user` VALUES (15, 'LongJuan', '$2a$10$9WrqIFvFXNITAp6.VHoQPOxRiE8qYPOAWnSimnj.FFWLkRRPzVz4C', '11122223333', '111@qq.com', 0, '战栗的龙卷', NULL, NULL, '2019-06-17 19:49:33', '2019-06-17 19:49:34');
INSERT INTO `tb_user` VALUES (16, 'wqewq', '$2a$10$OwqUAScqFUj74OkA8mvw6OnG.W5dyG5U4ZTGTxgnZ5jhU/dNOxA9K', '22233334444', '432@qq.com', 0, 'wqewq', NULL, NULL, '2019-06-17 19:57:56', '2019-06-19 21:16:09');
COMMIT;

-- ----------------------------
-- Table structure for tb_user_role
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_role`;
CREATE TABLE "tb_user_role" (
  "user_id" int(11) NOT NULL,
  "role_id" int(11) NOT NULL,
  KEY "FKea2ootw6b6bb0xt3ptl28bymv" ("role_id"),
  KEY "FK7vn3h53d0tqdimm8cp45gc0kl" ("user_id"),
  CONSTRAINT "FK7vn3h53d0tqdimm8cp45gc0kl" FOREIGN KEY ("user_id") REFERENCES "tb_user" ("id"),
  CONSTRAINT "FKea2ootw6b6bb0xt3ptl28bymv" FOREIGN KEY ("role_id") REFERENCES "tb_role" ("id")
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_user_role
-- ----------------------------
BEGIN;
INSERT INTO `tb_user_role` VALUES (1, 1);
INSERT INTO `tb_user_role` VALUES (9, 2);
INSERT INTO `tb_user_role` VALUES (13, 6);
INSERT INTO `tb_user_role` VALUES (12, 2);
COMMIT;

-- ----------------------------
-- Table structure for tb_visit_log
-- ----------------------------
DROP TABLE IF EXISTS `tb_visit_log`;
CREATE TABLE "tb_visit_log" (
  "id" int(11) NOT NULL AUTO_INCREMENT,
  "ip" varchar(255) DEFAULT NULL,
  "login_date" datetime DEFAULT NULL,
  "user_id" int(11) DEFAULT NULL,
  "region" varchar(32) DEFAULT NULL,
  "city" varchar(50) DEFAULT NULL,
  "country" varchar(32) DEFAULT NULL,
  "isp" varchar(32) DEFAULT NULL,
  PRIMARY KEY ("id")
) ENGINE=InnoDB AUTO_INCREMENT=193 DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
