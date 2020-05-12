/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50727
 Source Host           : localhost:3306
 Source Schema         : library

 Target Server Type    : MySQL
 Target Server Version : 50727
 File Encoding         : 65001

 Date: 12/05/2020 21:38:57
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for book
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book` (
  `b_id` varchar(32) NOT NULL,
  `isbn` varchar(32) NOT NULL,
  `location` varchar(32) NOT NULL,
  `status` varchar(8) NOT NULL DEFAULT '未借出',
  PRIMARY KEY (`b_id`) USING BTREE,
  KEY `ISBN` (`isbn`) USING BTREE,
  CONSTRAINT `book_ibfk_1` FOREIGN KEY (`isbn`) REFERENCES `bookinfo` (`isbn`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of book
-- ----------------------------
BEGIN;
INSERT INTO `book` VALUES ('', 's', '', '未借出');
INSERT INTO `book` VALUES ('12', 's', '图书流通室', '未借出');
INSERT INTO `book` VALUES ('123', 's', '', '未借出');
COMMIT;

-- ----------------------------
-- Table structure for bookinfo
-- ----------------------------
DROP TABLE IF EXISTS `bookinfo`;
CREATE TABLE `bookinfo` (
  `isbn` varchar(32) NOT NULL,
  `b_name` varchar(16) NOT NULL,
  `author` varchar(16) NOT NULL,
  `publish` varchar(16) NOT NULL,
  `pubdate` varchar(16) NOT NULL,
  `number` int(2) NOT NULL DEFAULT '0',
  `agent_id` int(8) DEFAULT NULL,
  PRIMARY KEY (`isbn`) USING BTREE,
  KEY `agent_id` (`agent_id`) USING BTREE,
  KEY `isbn` (`isbn`) USING BTREE,
  KEY `isbn_2` (`isbn`) USING BTREE,
  CONSTRAINT `bookinfo_ibfk_1` FOREIGN KEY (`agent_id`) REFERENCES `user` (`u_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of bookinfo
-- ----------------------------
BEGIN;
INSERT INTO `bookinfo` VALUES ('s', '书本1', '我', '某某', '202001', 0, 1);
COMMIT;

-- ----------------------------
-- Table structure for borrow_return
-- ----------------------------
DROP TABLE IF EXISTS `borrow_return`;
CREATE TABLE `borrow_return` (
  `br_id` int(8) NOT NULL AUTO_INCREMENT,
  `u_id` int(8) NOT NULL,
  `b_id` varchar(32) NOT NULL,
  `btime` varchar(16) NOT NULL,
  `rtime` varchar(16) DEFAULT NULL COMMENT '为空时表示还未还',
  PRIMARY KEY (`br_id`) USING BTREE,
  KEY `u_id` (`u_id`) USING BTREE,
  KEY `b_id` (`b_id`) USING BTREE,
  CONSTRAINT `borrow_return_ibfk_1` FOREIGN KEY (`u_id`) REFERENCES `user` (`u_id`),
  CONSTRAINT `borrow_return_ibfk_2` FOREIGN KEY (`b_id`) REFERENCES `book` (`b_id`)
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `p_id` int(8) NOT NULL AUTO_INCREMENT,
  `permissionname` varchar(16) NOT NULL,
  PRIMARY KEY (`p_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of permission
-- ----------------------------
BEGIN;
INSERT INTO `permission` VALUES (1, 'publish');
INSERT INTO `permission` VALUES (2, 'praise');
INSERT INTO `permission` VALUES (3, 'charge');
INSERT INTO `permission` VALUES (4, 'chargeall');
COMMIT;

-- ----------------------------
-- Table structure for reserve
-- ----------------------------
DROP TABLE IF EXISTS `reserve`;
CREATE TABLE `reserve` (
  `u_id` int(8) NOT NULL,
  `isbn` varchar(32) NOT NULL,
  `reservetime` varchar(16) NOT NULL,
  `deadline` varchar(16) DEFAULT NULL,
  KEY `u_id` (`u_id`) USING BTREE,
  KEY `ISBN` (`isbn`) USING BTREE,
  CONSTRAINT `reserve_ibfk_1` FOREIGN KEY (`u_id`) REFERENCES `user` (`u_id`),
  CONSTRAINT `reserve_ibfk_2` FOREIGN KEY (`isbn`) REFERENCES `bookinfo` (`isbn`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `r_id` int(8) NOT NULL AUTO_INCREMENT,
  `rolename` varchar(16) NOT NULL,
  `mask` varchar(32) NOT NULL,
  PRIMARY KEY (`r_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of role
-- ----------------------------
BEGIN;
INSERT INTO `role` VALUES (1, 'admin', '网站管理员');
INSERT INTO `role` VALUES (2, 'officer', '图书管理员');
INSERT INTO `role` VALUES (3, 'ordinary', '普通用户');
COMMIT;

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission` (
  `r_id` int(8) DEFAULT NULL,
  `p_id` int(8) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of role_permission
-- ----------------------------
BEGIN;
INSERT INTO `role_permission` VALUES (1, 1);
INSERT INTO `role_permission` VALUES (1, 2);
INSERT INTO `role_permission` VALUES (1, 3);
INSERT INTO `role_permission` VALUES (1, 4);
COMMIT;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `u_id` int(8) NOT NULL AUTO_INCREMENT,
  `username` varchar(16) NOT NULL,
  `password` varchar(32) NOT NULL,
  `name` varchar(8) DEFAULT NULL,
  `phone` varchar(16) DEFAULT NULL,
  `email` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`u_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of user
-- ----------------------------
BEGIN;
INSERT INTO `user` VALUES (1, 'admin', 'a9ff9a99a2a241c7884890fa4828e8d2', NULL, NULL, NULL);
INSERT INTO `user` VALUES (22, 'test1', 'a9ff9a99a2a241c7884890fa4828e8d2', NULL, NULL, NULL);
INSERT INTO `user` VALUES (23, 'test2', 'a9ff9a99a2a241c7884890fa4828e8d2', NULL, NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `u_id` int(8) DEFAULT NULL,
  `r_id` int(8) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of user_role
-- ----------------------------
BEGIN;
INSERT INTO `user_role` VALUES (23, 3);
INSERT INTO `user_role` VALUES (1, 1);
INSERT INTO `user_role` VALUES (1, 2);
INSERT INTO `user_role` VALUES (1, 3);
INSERT INTO `user_role` VALUES (22, 2);
INSERT INTO `user_role` VALUES (22, 3);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
