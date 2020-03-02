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
  `openid` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '微信ID，单个应用中唯一',
  `unionid` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '不同小程序或应用中都相同',
  `nick_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '微信昵称',
  `avatar_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '微信头像',
  `gender` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '性别',
  `province` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '省份',
  `city` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '城市',
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '手机号',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '酒坊名称',
  `num` int(11) DEFAULT NULL COMMENT '分配的总酒票数',
  `fc_num` int(11) DEFAULT NULL COMMENT '发出的酒票数',
  `yk_num` int(11) DEFAULT NULL COMMENT '坊主邀请人数',
  `stat` tinyint(255) DEFAULT NULL COMMENT '状态：0删除，1创建状态，2微信认证过',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
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
  `yk_id` bigint(10) DEFAULT NULL COMMENT '游客id',
  `fz_id` bigint(20) DEFAULT NULL COMMENT '坊主id',
  `jp_num` int(11) DEFAULT 0 COMMENT '赢得酒票数',
  `fx_num` int(11) DEFAULT 0 COMMENT '分享次数，分享后点击开始游戏才记录次数',
  `yx_num` int(11) DEFAULT 5 COMMENT '剩余游戏次数，初始5次',
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
  `openid` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '微信ID，单个应用中唯一',
  `unionid` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '不同小程序或应用中都相同',
  `nick_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '微信昵称',
  `avatar_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '微信头像',
  `gender` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '性别',
  `province` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '省份',
  `city` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '城市',
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '手机号',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY USING BTREE (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'InnoDB free: 4096 kB' ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
