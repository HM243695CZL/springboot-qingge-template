/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : localhost:3306
 Source Schema         : hl

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : 65001

 Date: 05/06/2022 19:56:42
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '课程名称',
  `score` int(255) NULL DEFAULT NULL COMMENT '学分',
  `times` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '上课时间',
  `state` tinyint(1) NULL DEFAULT 1 COMMENT '是否开课',
  `teacher_id` int(11) NULL DEFAULT NULL COMMENT '授课老师id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of course
-- ----------------------------
INSERT INTO `course` VALUES (1, '高等数学', 20, '80', 1, 30);
INSERT INTO `course` VALUES (2, '大学物理', 10, '45', 1, 32);
INSERT INTO `course` VALUES (3, '线性代数', 30, '50', 1, 30);

-- ----------------------------
-- Table structure for student_course
-- ----------------------------
DROP TABLE IF EXISTS `student_course`;
CREATE TABLE `student_course`  (
  `student_id` int(11) NOT NULL,
  `course_id` int(11) NOT NULL,
  PRIMARY KEY (`student_id`, `course_id`) USING BTREE
) ENGINE = MyISAM CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Fixed;

-- ----------------------------
-- Records of student_course
-- ----------------------------
INSERT INTO `student_course` VALUES (29, 1);
INSERT INTO `student_course` VALUES (29, 3);

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict`  (
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '名称',
  `value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '内容',
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '类型'
) ENGINE = MyISAM CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES ('user', 'fa fa-user', 'icon');
INSERT INTO `sys_dict` VALUES ('home', 'fa fa-home', 'icon');
INSERT INTO `sys_dict` VALUES ('bars', 'fa fa-bars', 'icon');
INSERT INTO `sys_dict` VALUES ('book', 'fa fa-book', 'icon');
INSERT INTO `sys_dict` VALUES ('file-o', 'fa fa-file-o', 'icon');

-- ----------------------------
-- Table structure for sys_file
-- ----------------------------
DROP TABLE IF EXISTS `sys_file`;
CREATE TABLE `sys_file`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '文件名称',
  `origin_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '文件原名',
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '文件类型',
  `size` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '文件大小(kb)',
  `download_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '下载链接',
  `is_delete` tinyint(1) NULL DEFAULT 0 COMMENT '是否删除',
  `enable` tinyint(1) NULL DEFAULT 1 COMMENT '是否禁用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 24 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_file
-- ----------------------------
INSERT INTO `sys_file` VALUES (21, 'f10c4f32325a4a27812004d58abeb784.png', 'iphone12.png', 'png', '181KB', 'http://localhost:9090/file/download/f10c4f32325a4a27812004d58abeb784.png', 0, 1);
INSERT INTO `sys_file` VALUES (22, '1de6d60d005b4c64abc255f6c1b1f7a3.jpg', '微信图片_20220329230321.jpg', 'jpg', '57KB', 'http://localhost:9090/file/download/1de6d60d005b4c64abc255f6c1b1f7a3.jpg', 0, 1);
INSERT INTO `sys_file` VALUES (18, '2f82375b2d034189a8d36e2fff6c237e.jpg', '春物.jpg', 'jpg', '150KB', 'http://localhost:9090/file/download/2f82375b2d034189a8d36e2fff6c237e.jpg', 0, 1);
INSERT INTO `sys_file` VALUES (23, '4dc06be25f71453fa397d8f4411bbe25.png', 'iphone12pro.png', 'png', '222KB', 'http://localhost:9090/file/download/4dc06be25f71453fa397d8f4411bbe25.png', 0, 1);

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '菜单名称',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '组件名称',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '路由路径',
  `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '组件路径',
  `is_link` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '是否超链接菜单',
  `is_hide` tinyint(1) NULL DEFAULT NULL COMMENT '是否隐藏 0：隐藏 1: 显示',
  `is_keep_alive` tinyint(1) NULL DEFAULT NULL COMMENT '是否缓存 0: 否 1：是',
  `is_affix` tinyint(1) NULL DEFAULT NULL COMMENT '是否固定标签 0：否 1：是',
  `is_iframe` tinyint(1) NULL DEFAULT NULL COMMENT '是否内嵌窗口 0：否 1: 是',
  `icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '图标',
  `pid` int(11) NULL DEFAULT 0 COMMENT '父级id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (3, '系统设置', 'system', '/system', 'layout', '', 0, 1, 0, 0, 'fa fa-home', 0);
INSERT INTO `sys_menu` VALUES (4, '菜单管理', 'systemMenu', '/system/menu', '/system/menu', '', 0, 1, 0, 0, 'fa fa-bars', 3);
INSERT INTO `sys_menu` VALUES (7, '用户管理', 'systemUser', '/system/user', '/system/user', '', 0, 1, 0, 0, 'fa fa-user', 3);
INSERT INTO `sys_menu` VALUES (8, '角色管理', 'systemRole', '/system/role', '/system/role', '', 0, 1, 0, 0, 'fa fa-book', 3);
INSERT INTO `sys_menu` VALUES (11, '文件管理', 'systemFile', '/system/file', '/system/file', '', 0, 1, 0, 0, 'fa fa-file-o', 3);
INSERT INTO `sys_menu` VALUES (12, '课程管理', 'systemCourse', '/system/course', '/system/course', '', 0, 1, 0, 0, 'fa fa-bars', 3);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '名称',
  `key_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '唯一标识',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (6, '管理员', 'admin', '拥有所有权限');
INSERT INTO `sys_role` VALUES (8, 'super', 'super', '超级管理员');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `menu_id` int(11) NOT NULL COMMENT '菜单id',
  PRIMARY KEY (`role_id`, `menu_id`) USING BTREE
) ENGINE = MyISAM CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Fixed;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (6, 3);
INSERT INTO `sys_role_menu` VALUES (6, 4);
INSERT INTO `sys_role_menu` VALUES (6, 7);
INSERT INTO `sys_role_menu` VALUES (6, 8);
INSERT INTO `sys_role_menu` VALUES (6, 11);
INSERT INTO `sys_role_menu` VALUES (6, 12);
INSERT INTO `sys_role_menu` VALUES (8, 3);
INSERT INTO `sys_role_menu` VALUES (8, 4);
INSERT INTO `sys_role_menu` VALUES (8, 7);
INSERT INTO `sys_role_menu` VALUES (8, 8);
INSERT INTO `sys_role_menu` VALUES (8, 11);
INSERT INTO `sys_role_menu` VALUES (8, 12);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '密码',
  `nickname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '昵称',
  `email` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '电话',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '地址',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 35 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (28, 'admin', '123456', 'admin', '1224311695@qq.com', '17812345678', '贞丰县', '2022-06-01 22:25:24');
INSERT INTO `sys_user` VALUES (34, 'test', '123456', 'test', '222', '111', NULL, '2022-06-03 23:48:35');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `role_id` int(11) NOT NULL COMMENT '角色id',
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = MyISAM CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Fixed;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (28, 6);

SET FOREIGN_KEY_CHECKS = 1;
