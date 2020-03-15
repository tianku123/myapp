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

 Date: 15/03/2020 12:01:22
*/

-- ----------------------------
-- Table structure for prize_config
-- ----------------------------
DROP TABLE IF EXISTS `prize_config`;
CREATE TABLE `prize_config`  (
  `id` bigint(20) NOT NULL  AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `num` int(11) DEFAULT NULL,
  `img` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY USING BTREE (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'InnoDB free: 4096 kB' ROW_FORMAT = Compact;



