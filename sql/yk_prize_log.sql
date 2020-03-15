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

 Date: 15/03/2020 13:32:34
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for yk_prize_log
-- ----------------------------
DROP TABLE IF EXISTS `yk_prize_log`;
CREATE TABLE `yk_prize_log`  (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `yk_id` bigint(10) DEFAULT NULL COMMENT '游客id',
  `yk_openid` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '游客openid',
  `num` int(11) DEFAULT 0 COMMENT '兑奖数量',
  `create_time` datetime DEFAULT NULL,
  `prize_config_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY USING BTREE (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'InnoDB free: 3072 kB' ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
