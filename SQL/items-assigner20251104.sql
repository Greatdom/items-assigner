/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80040 (8.0.40)
 Source Host           : localhost:3306
 Source Schema         : items-assigner

 Target Server Type    : MySQL
 Target Server Version : 80040 (8.0.40)
 File Encoding         : 65001

 Date: 04/11/2025 11:34:54
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `user_id` bigint NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders` VALUES (1, 'hello', 1);

-- ----------------------------
-- Table structure for permissions
-- ----------------------------
DROP TABLE IF EXISTS `permissions`;
CREATE TABLE `permissions`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '名称',
  `permission_value` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '权限值',
  `is_deleted` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '权限' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of permissions
-- ----------------------------
INSERT INTO `permissions` VALUES (1, '增加用户', 'user.add', 0, '2025-10-25 16:00:09', '2025-10-25 16:00:09');
INSERT INTO `permissions` VALUES (2, '更新用户', 'user.update', 0, '2025-10-25 16:00:24', '2025-10-25 16:00:24');
INSERT INTO `permissions` VALUES (3, '查询用户', 'user.list', 0, '2025-10-25 16:01:02', '2025-10-25 16:01:02');
INSERT INTO `permissions` VALUES (4, '删除用户', 'user.remove', 0, '2025-10-25 16:01:15', '2025-10-25 16:01:15');
INSERT INTO `permissions` VALUES (5, '增加角色', 'role.add', 0, '2025-10-25 16:01:30', '2025-10-25 16:01:30');
INSERT INTO `permissions` VALUES (6, '查询角色', 'role.list', 0, '2025-10-25 16:01:42', '2025-10-25 16:01:42');
INSERT INTO `permissions` VALUES (7, '更新角色', 'role.update', 0, '2025-10-25 16:01:55', '2025-10-25 16:01:55');
INSERT INTO `permissions` VALUES (8, '删除角色', 'role.delete', 0, '2025-10-25 16:02:04', '2025-10-25 16:02:04');
INSERT INTO `permissions` VALUES (9, '增加权限', 'permissions.add', 0, '2025-10-25 16:02:32', '2025-10-25 16:02:32');
INSERT INTO `permissions` VALUES (10, '更新权限', 'permissions.update', 0, '2025-10-25 16:02:46', '2025-10-25 16:02:46');
INSERT INTO `permissions` VALUES (11, '查询权限', 'permissions.list', 0, '2025-10-25 16:02:56', '2025-10-25 16:02:56');
INSERT INTO `permissions` VALUES (12, '删除权限', 'permissions.remove', 0, '2025-10-25 16:03:04', '2025-10-25 16:03:04');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `role_name` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '' COMMENT '角色名称',
  `role_code` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '角色编码',
  `remark` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '备注',
  `is_deleted` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1984521457576759299 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1984518164557385728, 'ROLE_SUPER_ADMIN', NULL, NULL, 0, '2025-10-21 23:28:05', '2025-10-21 23:28:05');
INSERT INTO `role` VALUES (1984518164557385729, 'ROLE_ADMIN', NULL, NULL, 0, '2025-10-21 23:29:16', '2025-10-21 23:29:16');
INSERT INTO `role` VALUES (1984518164557385730, 'ROLE_NEW_CUSTOM_MEN', NULL, NULL, 0, '2025-11-01 15:09:36', '2025-11-01 15:09:36');
INSERT INTO `role` VALUES (1984521457576759298, 'ROLE_NEW_STUDENT', NULL, NULL, 0, '2025-11-01 15:22:41', '2025-11-01 15:22:41');

-- ----------------------------
-- Table structure for role_permissions
-- ----------------------------
DROP TABLE IF EXISTS `role_permissions`;
CREATE TABLE `role_permissions`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `role_id` bigint NOT NULL,
  `permissions_id` bigint NOT NULL,
  `is_deleted` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_role_id`(`role_id` ASC) USING BTREE,
  INDEX `idx_permission_id`(`permissions_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '角色权限' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_permissions
-- ----------------------------
INSERT INTO `role_permissions` VALUES (1, 1984518164557385728, 1, 0, '2025-10-22 09:20:18', '2025-10-22 09:20:18');
INSERT INTO `role_permissions` VALUES (2, 1984518164557385728, 2, 0, '2025-10-22 09:20:28', '2025-10-22 09:20:28');
INSERT INTO `role_permissions` VALUES (3, 1984518164557385728, 3, 0, '2025-10-22 09:20:28', '2025-10-22 09:20:28');
INSERT INTO `role_permissions` VALUES (4, 1984518164557385728, 4, 0, '2025-10-22 09:20:45', '2025-10-22 09:20:45');
INSERT INTO `role_permissions` VALUES (5, 1984518164557385728, 5, 0, '2025-10-22 09:20:45', '2025-10-22 09:20:45');
INSERT INTO `role_permissions` VALUES (6, 1984518164557385728, 6, 0, '2025-10-22 09:20:45', '2025-10-22 09:20:45');
INSERT INTO `role_permissions` VALUES (7, 1984518164557385728, 7, 0, '2025-10-22 09:20:45', '2025-10-22 09:20:45');
INSERT INTO `role_permissions` VALUES (8, 1984518164557385728, 8, 0, '2025-10-22 09:20:45', '2025-10-22 09:20:45');
INSERT INTO `role_permissions` VALUES (9, 1984518164557385728, 9, 0, '2025-10-22 09:20:45', '2025-10-22 09:20:45');
INSERT INTO `role_permissions` VALUES (10, 1984518164557385728, 10, 0, '2025-10-25 16:06:53', '2025-10-25 16:06:53');
INSERT INTO `role_permissions` VALUES (11, 1984518164557385728, 11, 0, '2025-10-25 16:06:53', '2025-10-25 16:06:53');
INSERT INTO `role_permissions` VALUES (12, 1984518164557385728, 12, 0, '2025-10-25 16:06:53', '2025-10-25 16:06:53');
INSERT INTO `role_permissions` VALUES (13, 1984518164557385729, 1, 0, '2025-10-25 16:07:56', '2025-10-25 16:07:56');
INSERT INTO `role_permissions` VALUES (14, 1984518164557385729, 2, 0, '2025-10-25 16:07:56', '2025-10-25 16:07:56');
INSERT INTO `role_permissions` VALUES (15, 1984518164557385729, 3, 0, '2025-10-25 16:07:56', '2025-10-25 16:07:56');
INSERT INTO `role_permissions` VALUES (16, 1984518164557385729, 4, 0, '2025-10-25 16:07:56', '2025-10-25 16:07:56');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `username` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '用户名',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '手机号',
  `email` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '邮箱',
  `password` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '密码',
  `nick_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '昵称',
  `salt` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '用户头像',
  `is_deleted` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_username`(`username` ASC) USING BTREE,
  UNIQUE INDEX `idx_phone`(`phone` ASC) USING BTREE,
  UNIQUE INDEX `idx_email`(`email` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1984531291470573571 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'wddyxd', '15626448742', 'www778005729@qq.com', '202cb962ac59075b964b07152d234b70', 'wddyxd', '', 0, '2025-10-21 20:17:56', '2025-10-21 20:18:00');
INSERT INTO `user` VALUES (2, 'abcdef', '13433384487', '778005728@qq.com', '202cb962ac59075b964b07152d234b70', 'Iamgood', '', 0, '2025-10-21 23:05:38', '2025-10-21 23:08:27');
INSERT INTO `user` VALUES (1984531291470573570, 'alibaba', '13415108248', '13415108248@qq.com', 'ffccc52b393d6a9b366eb7fe3dff9ab5', 'alibaba', '', 0, '2025-11-01 16:01:45', '2025-11-01 16:01:45');

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `role_id` bigint NOT NULL DEFAULT 0 COMMENT '角色id',
  `user_id` bigint NOT NULL DEFAULT 0 COMMENT '用户id',
  `is_deleted` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_role_id`(`role_id` ASC) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1984531291629957123 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES (1, 1984518164557385728, 1, 0, '2025-10-21 23:38:44', '2025-10-21 23:38:44');
INSERT INTO `user_role` VALUES (2, 1984518164557385729, 2, 0, '2025-10-25 21:46:26', '2025-10-25 21:46:29');
INSERT INTO `user_role` VALUES (1984531291629957122, 1984518164557385730, 1984531291470573570, 0, '2025-11-01 16:01:45', '2025-11-01 16:01:45');

SET FOREIGN_KEY_CHECKS = 1;
