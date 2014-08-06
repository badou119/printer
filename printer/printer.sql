/*
Navicat MySQL Data Transfer

Source Server         : ROOT
Source Server Version : 50511
Source Host           : localhost:3306
Source Database       : printer

Target Server Type    : MYSQL
Target Server Version : 50511
File Encoding         : 65001

Date: 2014-08-06 12:45:15
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `enterprise`
-- ----------------------------
DROP TABLE IF EXISTS `enterprise`;
CREATE TABLE `enterprise` (
  `id` varchar(100) NOT NULL,
  `entercode` varchar(50) NOT NULL,
  `name` varchar(100) NOT NULL,
  `address` varchar(200) DEFAULT NULL,
  `linkperpon` varchar(20) DEFAULT NULL,
  `linkphone` varchar(20) DEFAULT NULL,
  `logo` varchar(100) DEFAULT NULL,
  `parentid` varchar(50) DEFAULT NULL,
  `remark` varchar(2000) DEFAULT NULL,
  `regtime` datetime DEFAULT NULL,
  `updatetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `deleted` int(1) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of enterprise
-- ----------------------------
INSERT INTO `enterprise` VALUES ('1', 'AAA', '潜江市疾病预防控制中心', null, '张三', '11111111111', 'file/enterlogo/testzh.jpg', '0', null, null, '2014-08-06 12:03:03', '1');
INSERT INTO `enterprise` VALUES ('2', 'BBB', '灵兽县', null, '李四', '11222222222', 'file/enterlogo/testzh.jpg', '1', null, null, '2014-08-06 12:03:43', '1');

-- ----------------------------
-- Table structure for `exampeople`
-- ----------------------------
DROP TABLE IF EXISTS `exampeople`;
CREATE TABLE `exampeople` (
  `id` varchar(100) NOT NULL,
  `name` varchar(50) NOT NULL,
  `code` varchar(100) NOT NULL,
  `sex` varchar(100) NOT NULL,
  `age` varchar(100) DEFAULT NULL,
  `jkzcode` varchar(20) DEFAULT NULL,
  `pic` varchar(100) DEFAULT NULL,
  `examtime` datetime DEFAULT NULL,
  `phone` varchar(50) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  `enterid` varchar(100) NOT NULL,
  `profession` varchar(20) DEFAULT NULL,
  `isprinted` varchar(10) DEFAULT '0',
  `tocenter` varchar(10) DEFAULT '0',
  `remark` varchar(2000) DEFAULT NULL,
  `regtime` datetime DEFAULT NULL,
  `updatetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `deleted` int(1) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of exampeople
-- ----------------------------

-- ----------------------------
-- Table structure for `jkzseq`
-- ----------------------------
DROP TABLE IF EXISTS `jkzseq`;
CREATE TABLE `jkzseq` (
  `id` varchar(50) NOT NULL,
  `ymd` varchar(10) NOT NULL,
  `jkzseq` varchar(10) NOT NULL,
  `enterid` varchar(50) NOT NULL,
  PRIMARY KEY (`ymd`,`enterid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jkzseq
-- ----------------------------

-- ----------------------------
-- Table structure for `profession`
-- ----------------------------
DROP TABLE IF EXISTS `profession`;
CREATE TABLE `profession` (
  `name` varchar(50) NOT NULL,
  `id` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of profession
-- ----------------------------
INSERT INTO `profession` VALUES ('食品', '1');
INSERT INTO `profession` VALUES ('药品', '2');
INSERT INTO `profession` VALUES ('卫生', '3');

-- ----------------------------
-- Table structure for `systemact`
-- ----------------------------
DROP TABLE IF EXISTS `systemact`;
CREATE TABLE `systemact` (
  `id` varchar(100) NOT NULL,
  `name` varchar(100) NOT NULL,
  `actdesc` varchar(1000) NOT NULL,
  `url` varchar(255) DEFAULT NULL,
  `parentid` varchar(50) NOT NULL,
  `sort` int(3) DEFAULT NULL,
  `icon` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of systemact
-- ----------------------------
INSERT INTO `systemact` VALUES ('10', '系统菜单', 'enterprise', '', '0', '10', 'icon-log');
INSERT INTO `systemact` VALUES ('1001', '体检人员信息', 'enterprise', 'jsp/exampeople/exampeopleList.jsp', '10', '0', 'icon-log');
INSERT INTO `systemact` VALUES ('1010', '体检信息导入', 'enterprise', 'jsp/ImportExcel.jsp', '10', '10', 'icon-log');
INSERT INTO `systemact` VALUES ('1020', '乡镇单位信息', 'center', 'jsp/enterprise/enterpriseList.jsp', '10', '20', 'icon-log');
INSERT INTO `systemact` VALUES ('1030', '乡镇单位帐号信息', 'center', 'jsp/user/userList.jsp', '10', '25', 'icon-log');
INSERT INTO `systemact` VALUES ('20', '证件打印', 'center', '', '0', '0', 'icon-log');
INSERT INTO `systemact` VALUES ('2010', '证件打印', 'center', 'toPrintPage.action', '20', '10', 'icon-log');

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` varchar(100) NOT NULL,
  `account` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `enterid` varchar(100) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `phone` varchar(50) DEFAULT NULL,
  `remark` varchar(2000) DEFAULT NULL,
  `regtime` datetime DEFAULT NULL,
  `updatetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `deleted` int(1) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'admin', '202cb962ac59075b964b07152d234b70', '1', '疾控中心管理员', '11111111111', null, null, '2014-08-06 12:03:43', '1');
INSERT INTO `user` VALUES ('2', 'lingshou', '202cb962ac59075b964b07152d234b70', '2', '灵兽县管理员', '22222222222', null, null, '2014-08-06 12:03:43', '1');
