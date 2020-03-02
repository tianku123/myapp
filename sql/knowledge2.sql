/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50022
 Source Host           : localhost:3306
 Source Schema         : knowledge

 Target Server Type    : MySQL
 Target Server Version : 50022
 File Encoding         : 65001

 Date: 02/03/2020 15:43:04
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for fangzhu
-- ----------------------------
DROP TABLE IF EXISTS `fangzhu`;
CREATE TABLE `fangzhu`  (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `openid` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL ,
  `unionid` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL ,
  `nick_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL ,
  `avatar_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL ,
  `gender` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL ,
  `province` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL ,
  `city` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL ,
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL ,
  `create_time` datetime DEFAULT NULL ,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL ,
  `num` int(11) DEFAULT NULL,
  `fc_num` int(11) DEFAULT NULL,
  `yk_num` int(11) DEFAULT NULL,
  `stat` tinyint(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY USING BTREE (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'InnoDB free: 4096 kB' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of fangzhu
-- ----------------------------
INSERT INTO `fangzhu` VALUES (1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '18512548285', '2020-03-01 17:04:02', '石头酒坊', 101, NULL, NULL, 1, '2020-03-01 18:02:47');
INSERT INTO `fangzhu` VALUES (2, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '15955807165', '2020-03-01 18:00:56', '酒坊2', 1, NULL, NULL, 0, NULL);
INSERT INTO `fangzhu` VALUES (3, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '15955807165', '2020-03-01 18:55:05', '11', 111, NULL, NULL, 1, NULL);
INSERT INTO `fangzhu` VALUES (4, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '18512345678', '2020-03-01 22:50:52', '22', 11, NULL, NULL, 1, NULL);

-- ----------------------------
-- Table structure for fz_yk_rel
-- ----------------------------
DROP TABLE IF EXISTS `fz_yk_rel`;
CREATE TABLE `fz_yk_rel`  (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `yk_id` bigint(10) DEFAULT NULL ,
  `fz_id` bigint(20) DEFAULT NULL ,
  `jp_num` int(11) DEFAULT 0 ,
  `fx_num` int(11) DEFAULT 0 ,
  `yx_num` int(11) DEFAULT 5 ,
  PRIMARY KEY USING BTREE (`id`),
  INDEX `idx_yk_id` USING BTREE(`yk_id`),
  INDEX `idx_fz_id` USING BTREE(`fz_id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'InnoDB free: 4096 kB' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for youke
-- ----------------------------
DROP TABLE IF EXISTS `youke`;
CREATE TABLE `youke`  (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `openid` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL ,
  `unionid` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL ,
  `nick_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `avatar_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL ,
  `gender` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `province` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `city` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY USING BTREE (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'InnoDB free: 4096 kB' ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
