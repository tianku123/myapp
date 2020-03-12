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
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'InnoDB free: 4096 kB' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of fangzhu
-- ----------------------------
INSERT INTO `fangzhu` VALUES (1, 'rh_openid', NULL, NULL, NULL, NULL, NULL, NULL, '18512548285', '2020-03-01 17:04:02', '阮家经销商', 101, NULL, NULL, 2, '2020-03-03 21:46:11');
INSERT INTO `fangzhu` VALUES (2, NULL, NULL, NULL, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJ3FD4hxxFHJZndfHFW7GNhlEJODRBJC7bbYbfT6wPoGe282Cz90YiahyMSiabwN0OcPysY7hbVkOPg/132', NULL, NULL, NULL, '15955807165', '2020-03-01 18:00:56', '酒坊2', 1, NULL, NULL, 0, NULL);
INSERT INTO `fangzhu` VALUES (3, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '15955807165', '2020-03-01 18:55:05', '11', 111, NULL, NULL, 1, NULL);
INSERT INTO `fangzhu` VALUES (4, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '18512345678', '2020-03-01 22:50:52', '22', 11, NULL, NULL, 1, NULL);
INSERT INTO `fangzhu` VALUES (5, 'Jiangsu', NULL, '冬来', NULL, NULL, NULL, NULL, '15900005555', '2020-03-03 22:52:42', '22', 11, NULL, NULL, 0, '2020-03-03 23:27:18');
INSERT INTO `fangzhu` VALUES (6, 'Jiangsu', NULL, '冬来', NULL, NULL, NULL, NULL, '15900005555', '2020-03-03 23:27:27', '2222', 111, NULL, NULL, 2, '2020-03-03 23:31:19');
INSERT INTO `fangzhu` VALUES (7, 'ov9XU5IY3-4gAs1ul6RD8t1Zd7QA', NULL, '冬来', 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJ3FD4hxxFHJZndfHFW7GNhlEJODRBJC7bbYbfT6wPoGe282Cz90YiahyMSiabwN0OcPysY7hbVkOPg/132', '1', 'Jiangsu', 'Nanjing', '18762091440', '2020-03-04 12:01:09', '冬来酒馆', 100, NULL, NULL, 2, '2020-03-04 12:01:22');

-- ----------------------------
-- Table structure for jf_yk_log
-- ----------------------------
DROP TABLE IF EXISTS `jf_yk_log`;
CREATE TABLE `jf_yk_log`  (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `yk_id` bigint(10) DEFAULT NULL COMMENT '游客id',
  `jf_id` bigint(20) DEFAULT NULL COMMENT '酒坊id',
  `jp_num` int(11) DEFAULT 0 COMMENT '赢得酒票数',
  `openid` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '游客openid',
  `jf_tp` tinyint(2) DEFAULT NULL COMMENT '酒坊创建人类型，1:坊主；2:游客',
  `yx_tp` tinyint(2) DEFAULT NULL COMMENT '游戏类型，1：筛子，2：游戏大作战',
  `online` tinyint(2) DEFAULT NULL COMMENT '0:未完游戏，掉线或者自己跑了，1:玩游戏了',
  `one` int(10) DEFAULT 0 COMMENT '第一局结果',
  `two` int(10) DEFAULT 0 COMMENT '第二局结果',
  `three` int(10) DEFAULT 0 COMMENT '第三局结果',
  `ord` int(10) DEFAULT 0 COMMENT '第几局游戏',
  `total` int(10) DEFAULT 0 COMMENT '每局汇总',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY USING BTREE (`id`),
  UNIQUE INDEX `idx_jf_id_openid` USING BTREE(`jf_id`, `openid`)
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'InnoDB free: 4096 kB' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of jf_yk_log
-- ----------------------------
INSERT INTO `jf_yk_log` VALUES (1, NULL, 1, 0, 'yk_openid2', NULL, NULL, NULL, 0, 0, 0, NULL, 0, NULL);
INSERT INTO `jf_yk_log` VALUES (2, NULL, 1, 0, 'yk_openid3', NULL, NULL, NULL, 5, 6, 3, 3, 14, NULL);
INSERT INTO `jf_yk_log` VALUES (3, NULL, 3, 0, 'yk_openid3', 1, 2, NULL, 3, 0, 0, 1, 3, '2020-03-11 21:49:06');

-- ----------------------------
-- Table structure for jiufang
-- ----------------------------
DROP TABLE IF EXISTS `jiufang`;
CREATE TABLE `jiufang`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fz_yk_id` bigint(20) DEFAULT NULL COMMENT '坊主供应商、或游客id',
  `openid` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `tp` tinyint(2) DEFAULT NULL COMMENT '1:坊主；2:游客',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '酒坊名称',
  `num` int(11) DEFAULT 0 COMMENT '酒票数',
  `yx_tp` tinyint(2) DEFAULT NULL COMMENT '1:筛子;2:病毒大作战',
  `per_num` int(11) DEFAULT 0 COMMENT '酒坊进入的游客人数',
  `stat` tinyint(2) DEFAULT 0 COMMENT '游戏状态，0：准备中，1：已开始，2：游戏结束',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY USING BTREE (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'InnoDB free: 4096 kB' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of jiufang
-- ----------------------------
INSERT INTO `jiufang` VALUES (1, NULL, 'rh_openid', 1, '阮家酒坊', 1, 1, 0, 2, '2020-03-11 19:53:42');
INSERT INTO `jiufang` VALUES (2, NULL, 'rh_openid', 1, '阮家酒坊', 1, 1, 0, 0, '2020-03-11 19:57:53');
INSERT INTO `jiufang` VALUES (3, NULL, 'yk_openid1', 1, '游客酒坊', 1, 2, 1, 0, '2020-03-11 20:02:05');

-- ----------------------------
-- Table structure for jp_config
-- ----------------------------
DROP TABLE IF EXISTS `jp_config`;
CREATE TABLE `jp_config`  (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `num` int(10) DEFAULT NULL COMMENT '总票数',
  `fc_num` int(10) DEFAULT NULL COMMENT '已发出票数',
  `sy_num` int(10) DEFAULT NULL COMMENT '剩余票数',
  PRIMARY KEY USING BTREE (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'InnoDB free: 4096 kB' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of jp_config
-- ----------------------------
INSERT INTO `jp_config` VALUES (1, 20000, 0, 20000);

-- ----------------------------
-- Table structure for yk_success
-- ----------------------------
DROP TABLE IF EXISTS `yk_success`;
CREATE TABLE `yk_success`  (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `yk_id` bigint(10) DEFAULT NULL COMMENT '游客id',
  `yk_openid` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '游客openid',
  `yx_tp` tinyint(2) NOT NULL DEFAULT '' COMMENT '游戏类型：1:筛子;2:病毒大作战',
  `num` int(11) DEFAULT 0 COMMENT '赢得酒票数',
  PRIMARY KEY USING BTREE (`id`),
  UNIQUE INDEX `yk_success_yk_openid_tp` USING BTREE(`yk_openid`, `yx_tp`)
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
  `address` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '收货地址',
  `sh_phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '收货手机号码',
  `yx_num` int(11) DEFAULT 0 COMMENT '游戏次数',
  `jp_num` int(11) DEFAULT 0 COMMENT '酒票数',
  `free_num` int(11) DEFAULT 0 COMMENT '被邀请进入酒坊免费获取的酒票数',
  PRIMARY KEY USING BTREE (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'InnoDB free: 4096 kB' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of youke
-- ----------------------------
INSERT INTO `youke` VALUES (1, 'yk_openid1', NULL, '冬来', 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJ3FD4hxxFHJZndfHFW7GNhlEJODRBJC7bbYbfT6wPoGe282Cz90YiahyMSiabwN0OcPysY7hbVkOPg/132', NULL, 'Jiangsu', 'Nanjing', '18762091440', NULL, '2020-03-11 19:59:17', NULL, NULL, 0, 1, 1);
INSERT INTO `youke` VALUES (2, 'yk_openid2', NULL, '游客2', 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJ3FD4hxxFHJZndfHFW7GNhlEJODRBJC7bbYbfT6wPoGe282Cz90YiahyMSiabwN0OcPysY7hbVkOPg/132', NULL, 'Jiangsu', 'Nanjing', '18762091440', NULL, '2020-03-11 20:05:11', NULL, NULL, 0, 1, 1);
INSERT INTO `youke` VALUES (3, 'yk_openid3', NULL, '游客3', 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJ3FD4hxxFHJZndfHFW7GNhlEJODRBJC7bbYbfT6wPoGe282Cz90YiahyMSiabwN0OcPysY7hbVkOPg/132', NULL, 'Jiangsu', 'Nanjing', '18762091440', NULL, '2020-03-11 20:08:05', NULL, NULL, 0, 1, 1);

-- ----------------------------
-- Table structure for yx_log
-- ----------------------------
DROP TABLE IF EXISTS `yx_log`;
CREATE TABLE `yx_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `jf_yk_log_id` bigint(10) DEFAULT NULL COMMENT '酒坊玩家id',
  `score` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '分数',
  `create_time` datetime DEFAULT NULL COMMENT '时间',
  `yx_tp` tinyint(2) DEFAULT NULL COMMENT '酒坊创建人类型，1:坊主；2:游客',
  `ord` int(10) DEFAULT NULL COMMENT '第几局',
  PRIMARY KEY USING BTREE (`id`),
  INDEX `idx_yx_log_tzz` USING BTREE(`jf_yk_log_id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'InnoDB free: 4096 kB' ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
