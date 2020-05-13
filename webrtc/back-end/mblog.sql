/*
 Navicat Premium Data Transfer

 Source Server         : 139.224.135.121_3306
 Source Server Type    : MySQL
 Source Server Version : 50617
 Source Host           : 139.224.135.121:3306
 Source Schema         : mblog

 Target Server Type    : MySQL
 Target Server Version : 50617
 File Encoding         : 65001

 Date: 13/01/2019 13:10:07
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `u_id` int(8) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(32) NOT NULL,
  `name` varchar(8) DEFAULT NULL,
  `phone` varchar(16) DEFAULT NULL,
  `email` varchar(16) DEFAULT NULL,
  `signature` varchar(32) DEFAULT NULL COMMENT '个性签名',
  `avatar` varchar(255) DEFAULT 'https://www.diamondlife.top/Public/image/mikasa.jpg' COMMENT '头像路径',
  PRIMARY KEY (`u_id`)
) ENGINE=InnoDB AUTO_INCREMENT=718 DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
