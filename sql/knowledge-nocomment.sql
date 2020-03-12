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

 Date: 12/03/2020 11:46:37
*/

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
  `fc_num` int(11) DEFAULT NULL ,
  `yk_num` int(11) DEFAULT NULL ,
  `stat` tinyint(255) DEFAULT NULL ,
  `update_time` datetime DEFAULT NULL ,
  PRIMARY KEY USING BTREE (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'InnoDB free: 4096 kB' ROW_FORMAT = Compact;


-- ----------------------------
-- Table structure for jf_yk_log
-- ----------------------------
DROP TABLE IF EXISTS `jf_yk_log`;
CREATE TABLE `jf_yk_log`  (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `yk_id` bigint(10) DEFAULT NULL ,
  `jf_id` bigint(20) DEFAULT NULL ,
  `jp_num` int(11) DEFAULT 0 ,
  `openid` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL ,
  `jf_tp` tinyint(2) DEFAULT NULL ,
  `yx_tp` tinyint(2) DEFAULT NULL ,
  `online` tinyint(2) DEFAULT NULL ,
  `one` int(10) DEFAULT 0 ,
  `two` int(10) DEFAULT 0 ,
  `three` int(10) DEFAULT 0 ,
  `ord` int(10) DEFAULT 0 ,
  `total` int(10) DEFAULT 0 ,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY USING BTREE (`id`),
  UNIQUE INDEX `idx_jf_id_openid` USING BTREE(`jf_id`, `openid`)
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'InnoDB free: 4096 kB' ROW_FORMAT = Compact;


-- ----------------------------
-- Table structure for jiufang
-- ----------------------------
DROP TABLE IF EXISTS `jiufang`;
CREATE TABLE `jiufang`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fz_yk_id` bigint(20) DEFAULT NULL ,
  `openid` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `tp` tinyint(2) DEFAULT NULL ,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL ,
  `num` int(11) DEFAULT 0 ,
  `yx_tp` tinyint(2) DEFAULT NULL ,
  `per_num` int(11) DEFAULT 0 ,
  `stat` tinyint(2) DEFAULT 0 ,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY USING BTREE (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'InnoDB free: 4096 kB' ROW_FORMAT = Compact;


-- ----------------------------
-- Table structure for jp_config
-- ----------------------------
DROP TABLE IF EXISTS `jp_config`;
CREATE TABLE `jp_config`  (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `num` int(10) DEFAULT NULL ,
  `fc_num` int(10) DEFAULT NULL ,
  `sy_num` int(10) DEFAULT NULL ,
  PRIMARY KEY USING BTREE (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'InnoDB free: 4096 kB' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of jp_config
-- ----------------------------
INSERT INTO jp_config(num,fc_num,sy_num) VALUES (20000, 0, 20000);

-- ----------------------------
-- Table structure for yk_success
-- ----------------------------
DROP TABLE IF EXISTS `yk_success`;
CREATE TABLE `yk_success`  (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `yk_id` bigint(10) DEFAULT NULL ,
  `yk_openid` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
  `yx_tp` tinyint(2) NOT NULL ,
  `num` int(11) DEFAULT 0 ,
  PRIMARY KEY USING BTREE (`id`),
  UNIQUE INDEX `yk_success_yk_openid_tp` USING BTREE(`yk_openid`, `yx_tp`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'InnoDB free: 4096 kB' ROW_FORMAT = Compact;

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
  `address` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL ,
  `sh_phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL ,
  `yx_num` int(11) DEFAULT 0 ,
  `jp_num` int(11) DEFAULT 0 ,
  `free_num` int(11) DEFAULT 0 ,
  PRIMARY KEY USING BTREE (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'InnoDB free: 4096 kB' ROW_FORMAT = Compact;


-- ----------------------------
-- Table structure for yx_log
-- ----------------------------
DROP TABLE IF EXISTS `yx_log`;
CREATE TABLE `yx_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `jf_yk_log_id` bigint(10) DEFAULT NULL,
  `score` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `create_time` datetime DEFAULT NULL ,
  `yx_tp` tinyint(2) DEFAULT NULL ,
  `ord` int(10) DEFAULT NULL ,
  PRIMARY KEY USING BTREE (`id`),
  INDEX `idx_yx_log_tzz` USING BTREE(`jf_yk_log_id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'InnoDB free: 4096 kB' ROW_FORMAT = Compact;

