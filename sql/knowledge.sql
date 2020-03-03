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

 Date: 03/03/2020 20:17:07
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
  `num` int(11) DEFAULT NULL ,
  `fc_num` int(11) DEFAULT NULL ,
  `yk_num` int(11) DEFAULT NULL ,
  `stat` tinyint(255) DEFAULT NULL ,
  `update_time` datetime DEFAULT NULL ,
  PRIMARY KEY USING BTREE (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'InnoDB free: 3072 kB' ROW_FORMAT = Compact;

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
  `yk_openid` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL ,
  `fz_openid` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL ,
  PRIMARY KEY USING BTREE (`id`),
  INDEX `idx_yk_id` USING BTREE(`yk_id`),
  INDEX `idx_fz_id` USING BTREE(`fz_id`),
  UNIQUE INDEX `unique_jdx_fz_id_yk_id` USING BTREE(`yk_id`, `fz_id`),
  INDEX `idx_yk_openid` USING BTREE(`yk_openid`),
  INDEX `idx_fz_openid` USING BTREE(`fz_openid`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'InnoDB free: 3072 kB' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for youke
-- ----------------------------
DROP TABLE IF EXISTS `youke`;
CREATE TABLE `youke`  (
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
  `update_time` datetime DEFAULT NULL ,
  PRIMARY KEY USING BTREE (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'InnoDB free: 3072 kB' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for yx_log
-- ----------------------------
DROP TABLE IF EXISTS `yx_log`;
CREATE TABLE `yx_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tzz` bigint(255) DEFAULT NULL ,
  `tzz_openid` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL ,
  `btz` bigint(255) DEFAULT NULL ,
  `btz_openid` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL ,
  `btz_type` tinyint(1) DEFAULT NULL ,
  `success` tinyint(1) DEFAULT NULL ,
  `create_time` datetime DEFAULT NULL ,
  PRIMARY KEY USING BTREE (`id`),
  INDEX `idx_yx_log_tzz` USING BTREE(`tzz`),
  INDEX `idx_yx_log_btz` USING BTREE(`btz`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'InnoDB free: 3072 kB' ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
