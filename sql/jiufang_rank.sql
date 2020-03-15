/*
 Navicat Premium Data Transfer

 Source Server         : 106.14.213.164
 Source Server Type    : MySQL
 Source Server Version : 50717
 Source Host           : 106.14.213.164:3306
 Source Schema         : knowledge

 Target Server Type    : MySQL
 Target Server Version : 50717
 File Encoding         : 65001

 Date: 15/03/2020 02:59:03
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for jiufang_rank
-- ----------------------------
DROP TABLE IF EXISTS `jiufang_rank`;
CREATE TABLE `jiufang_rank`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `jf_id` bigint(20) DEFAULT NULL,
  `rank_json` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
