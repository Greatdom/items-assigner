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

 Date: 23/11/2025 19:40:34
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for after_sale
-- ----------------------------
DROP TABLE IF EXISTS `after_sale`;
CREATE TABLE `after_sale`  (
  `id` bigint NOT NULL COMMENT '主键（售后单 ID）',
  `after_sale_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '售后单号（业务标识，如 AS20240520xxxx）',
  `order_id` bigint NOT NULL COMMENT '关联 order.id（主订单 ID）',
  `order_item_id` bigint NOT NULL COMMENT '关联 order_item.id（关联具体订单项，支持部分售后）',
  `user_id` bigint NOT NULL COMMENT '申请人 ID（关联 user.id）',
  `type` tinyint UNSIGNED NOT NULL COMMENT '售后类型（0 - 仅退款 1 - 退货退款 2 - 换货）',
  `reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '售后原因',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '详细描述',
  `images` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '凭证图片 URL（多图用逗号分隔）',
  `amount` decimal(10, 2) NOT NULL COMMENT '申请金额（退款 / 赔偿金额）',
  `status` tinyint UNSIGNED NOT NULL COMMENT '处理状态（0 - 待审核 1 - 审核通过 2 - 审核拒绝 3 - 处理中 4 - 已完成 5 - 已取消）',
  `handler_id` bigint NULL DEFAULT 0 COMMENT '处理人 ID（0 - 系统 其他为管理员 / 商户 ID）',
  `handle_time` datetime NULL DEFAULT NULL COMMENT '处理完成时间',
  `handle_remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '处理备注（如拒绝原因）',
  `is_deleted` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_after_sale_no`(`after_sale_no` ASC) USING BTREE,
  INDEX `idx_order_id`(`order_id` ASC) USING BTREE,
  INDEX `idx_order_item_id`(`order_item_id` ASC) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '售后单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of after_sale
-- ----------------------------

-- ----------------------------
-- Table structure for coupon
-- ----------------------------
DROP TABLE IF EXISTS `coupon`;
CREATE TABLE `coupon`  (
  `id` bigint NOT NULL COMMENT '主键',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '优惠券名称',
  `get_type` tinyint UNSIGNED NOT NULL COMMENT '0-满减 1-折扣',
  `target_type` tinyint UNSIGNED NOT NULL COMMENT '0-全场通用 1-指定商户可用 2-指定商品可用',
  `is_discount` tinyint UNSIGNED NULL DEFAULT 0 COMMENT '0-通用 1-商品优惠时不可用',
  `target_id` bigint NOT NULL COMMENT '优惠券目标ID，无指定为0',
  `threshold` decimal(10, 2) NOT NULL DEFAULT 0.00 COMMENT '使用门槛',
  `value` decimal(10, 2) NOT NULL COMMENT '优惠值（满减为金额，折扣为百分比）',
  `start_time` datetime NOT NULL COMMENT '生效时间',
  `end_time` datetime NOT NULL COMMENT '失效时间',
  `stock` int NOT NULL DEFAULT 0 COMMENT '发放总量',
  `remaining_stock` int NOT NULL DEFAULT 0 COMMENT '剩余数量',
  `status` tinyint UNSIGNED NOT NULL COMMENT '0-未生效 1-生效中 2-已过期',
  `is_deleted` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_target_id`(`target_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '优惠券表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of coupon
-- ----------------------------

-- ----------------------------
-- Table structure for financial_flow
-- ----------------------------
DROP TABLE IF EXISTS `financial_flow`;
CREATE TABLE `financial_flow`  (
  `id` bigint NOT NULL COMMENT '主键',
  `user_id` bigint NOT NULL COMMENT '用户ID --所有角色通用',
  `trade_type` tinyint UNSIGNED NOT NULL COMMENT '0-充值 1-提现 2-订单支付 3-退款 4-平台佣金',
  `amount` decimal(10, 2) NOT NULL COMMENT '正数收入，负数支出',
  `trade_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '交易单号',
  `status` tinyint UNSIGNED NOT NULL COMMENT '0-处理中 1-成功 2-失败',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '交易备注',
  `is_deleted` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_trade_no`(`trade_no` ASC) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '财务流水表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of financial_flow
-- ----------------------------

-- ----------------------------
-- Table structure for merchant_supplement
-- ----------------------------
DROP TABLE IF EXISTS `merchant_supplement`;
CREATE TABLE `merchant_supplement`  (
  `id` bigint NOT NULL COMMENT '主键',
  `user_id` bigint NOT NULL COMMENT '关联user.id',
  `shop_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '店铺地址',
  `shop_license` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '营业执照ID',
  `shop_license_image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '营业执照图片ID',
  `shop_star` tinyint UNSIGNED NULL DEFAULT 0 COMMENT '开始时为0代表空，1~5',
  `shop_status` tinyint UNSIGNED NULL DEFAULT 0 COMMENT '0-开张 1-关闭',
  `is_deleted` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_user_id`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '商户补强表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of merchant_supplement
-- ----------------------------

-- ----------------------------
-- Table structure for order_address
-- ----------------------------
DROP TABLE IF EXISTS `order_address`;
CREATE TABLE `order_address`  (
  `id` bigint NOT NULL COMMENT '主键',
  `order_id` bigint NOT NULL COMMENT '订单ID',
  `user_id` bigint NOT NULL COMMENT '下单用户ID',
  `receiver` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '收货人姓名',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '收货人手机',
  `province` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '省份',
  `city` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '城市',
  `district` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '区县',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '详细地址',
  `is_deleted` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_order_id`(`order_id` ASC) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '订单地址表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_address
-- ----------------------------

-- ----------------------------
-- Table structure for order_comment
-- ----------------------------
DROP TABLE IF EXISTS `order_comment`;
CREATE TABLE `order_comment`  (
  `id` bigint NOT NULL COMMENT '主键',
  `is_follow_comment` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '0 - 不是追评 1 - 是追评',
  `comment_id` bigint NOT NULL COMMENT '被跟评的ID-启用时order_item_id失效',
  `order_item_id` bigint NOT NULL COMMENT '关联 order.id（确保评价对应具体子订单）',
  `product_id` bigint NOT NULL COMMENT '关联 product.id（商品 ID）',
  `sku_id` bigint NOT NULL COMMENT '关联 product_sku.id（规格 ID，无规格为 0）',
  `user_id` bigint NOT NULL COMMENT '评价用户 ID（关联 user.id）',
  `score` tinyint UNSIGNED NOT NULL COMMENT '评分（1-5 分）',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '评价内容',
  `images` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '评价图片-支持单图 URL',
  `is_anonymous` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '0 - 非匿名 1 - 匿名',
  `is_deleted` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_order_item_id`(`order_item_id` ASC) USING BTREE,
  INDEX `idx_product_id`(`product_id` ASC) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '订单评价表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_comment
-- ----------------------------

-- ----------------------------
-- Table structure for order_item
-- ----------------------------
DROP TABLE IF EXISTS `order_item`;
CREATE TABLE `order_item`  (
  `id` bigint NOT NULL COMMENT '主键',
  `order_id` bigint NOT NULL COMMENT '订单ID',
  `product_id` bigint NOT NULL COMMENT '商品ID',
  `sku_id` bigint NOT NULL COMMENT '规格ID，无规格为0',
  `quantity` int NOT NULL DEFAULT 1 COMMENT '购买数量',
  `unit_price` decimal(10, 2) NOT NULL COMMENT '下单单价',
  `total_price` decimal(10, 2) NOT NULL COMMENT '小计金额',
  `product_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商品名称快照',
  `sku_specs` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '规格快照',
  `is_deleted` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_order_id`(`order_id` ASC) USING BTREE,
  INDEX `idx_product_id`(`product_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '订单详情表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_item
-- ----------------------------

-- ----------------------------
-- Table structure for order_main
-- ----------------------------
DROP TABLE IF EXISTS `order_main`;
CREATE TABLE `order_main`  (
  `id` bigint NOT NULL COMMENT '主键',
  `user_id` bigint NOT NULL COMMENT '下单用户ID',
  `total_price` decimal(10, 2) NOT NULL COMMENT '订单总金额',
  `pay_price` decimal(10, 2) NOT NULL COMMENT '实际支付金额',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '订单备注(比如优惠明细，发货要求等)',
  `is_deleted` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '订单主表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_main
-- ----------------------------

-- ----------------------------
-- Table structure for order_status_log
-- ----------------------------
DROP TABLE IF EXISTS `order_status_log`;
CREATE TABLE `order_status_log`  (
  `id` bigint NOT NULL COMMENT '主键',
  `order_id` bigint NOT NULL COMMENT '订单ID',
  `operator_id` bigint NULL DEFAULT 0 COMMENT '0-系统，其他为用户/商户/管理员ID',
  `status` tinyint UNSIGNED NOT NULL COMMENT '0-待付款 1-待发货 2-待收货 3-已完成 4-已取消',
  `pay_method` tinyint UNSIGNED NOT NULL COMMENT '0-未选择 1-微信支付 2-支付宝支付',
  `ship_time` datetime NULL DEFAULT NULL COMMENT '发货时间',
  `receive_time` datetime NULL DEFAULT NULL COMMENT '收货时间',
  `cancel_time` datetime NULL DEFAULT NULL COMMENT '取消时间',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '状态变更说明',
  `is_deleted` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_order_id`(`order_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '订单状态日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_status_log
-- ----------------------------

-- ----------------------------
-- Table structure for permissions
-- ----------------------------
DROP TABLE IF EXISTS `permissions`;
CREATE TABLE `permissions`  (
  `id` bigint NOT NULL COMMENT '编号',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '名称',
  `permission_value` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '权限值',
  `is_deleted` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '权限' ROW_FORMAT = Dynamic;

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
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product`  (
  `id` bigint NOT NULL COMMENT '主键',
  `product_sku_id` bigint NOT NULL COMMENT '默认样式',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商品名称',
  `stock` int NOT NULL DEFAULT 0 COMMENT '库存',
  `category_id` bigint NOT NULL COMMENT '关联分类表',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '商品描述',
  `user_id` bigint NOT NULL COMMENT '上架商户ID',
  `status` tinyint UNSIGNED NOT NULL COMMENT '0-下架 1-上架',
  `is_deleted` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_category_id`(`category_id` ASC) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '商品表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of product
-- ----------------------------

-- ----------------------------
-- Table structure for product_category
-- ----------------------------
DROP TABLE IF EXISTS `product_category`;
CREATE TABLE `product_category`  (
  `id` bigint NOT NULL COMMENT '主键',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '分类名称',
  `code` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '分类标识',
  `is_deleted` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_code`(`code` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '商品分类表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of product_category
-- ----------------------------

-- ----------------------------
-- Table structure for product_sku
-- ----------------------------
DROP TABLE IF EXISTS `product_sku`;
CREATE TABLE `product_sku`  (
  `id` bigint NOT NULL COMMENT '主键',
  `product_id` bigint NOT NULL COMMENT '关联商品ID',
  `specs` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '规格描述，如\"红色-XL\"',
  `price` decimal(10, 2) NOT NULL COMMENT '规格售价',
  `stock` int NOT NULL DEFAULT 0 COMMENT '规格库存',
  `sku_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '规格编码',
  `logo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品图片',
  `is_deleted` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_sku_code`(`sku_code` ASC) USING BTREE,
  INDEX `idx_product_id`(`product_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '商品规格表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of product_sku
-- ----------------------------

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` bigint NOT NULL COMMENT '角色id',
  `role_name` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '' COMMENT '角色名称',
  `role_code` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '角色编码',
  `remark` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '备注',
  `is_deleted` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

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
  `id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  `permissions_id` bigint NOT NULL,
  `is_deleted` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_role_id`(`role_id` ASC) USING BTREE,
  INDEX `idx_permission_id`(`permissions_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '角色权限' ROW_FORMAT = Dynamic;

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
-- Table structure for shopping_cart
-- ----------------------------
DROP TABLE IF EXISTS `shopping_cart`;
CREATE TABLE `shopping_cart`  (
  `id` bigint NOT NULL COMMENT '主键',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `product_id` bigint NOT NULL COMMENT '商品ID',
  `sku_id` bigint NOT NULL COMMENT '规格ID，无规格为0',
  `quantity` int NOT NULL DEFAULT 1 COMMENT '商品数量',
  `is_selected` tinyint UNSIGNED NOT NULL DEFAULT 1 COMMENT '0-未选中 1-选中',
  `is_deleted` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE,
  INDEX `idx_product_id`(`product_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '购物车表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of shopping_cart
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint NOT NULL COMMENT 'id',
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
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'wddyxd', '15626448742', 'www778005729@qq.com', '202cb962ac59075b964b07152d234b70', 'wddyxd', '', 0, '2025-10-21 20:17:56', '2025-10-21 20:18:00');
INSERT INTO `user` VALUES (2, 'abcdef', '13433384487', '778005728@qq.com', '202cb962ac59075b964b07152d234b70', 'Iamgood', '', 0, '2025-10-21 23:05:38', '2025-10-21 23:08:27');
INSERT INTO `user` VALUES (1984531291470573570, 'alibaba', '13415108248', '13415108248@qq.com', 'ffccc52b393d6a9b366eb7fe3dff9ab5', 'alibaba', '', 0, '2025-11-01 16:01:45', '2025-11-01 16:01:45');

-- ----------------------------
-- Table structure for user_address
-- ----------------------------
DROP TABLE IF EXISTS `user_address`;
CREATE TABLE `user_address`  (
  `id` bigint NOT NULL COMMENT '主键',
  `user_id` bigint NOT NULL COMMENT '关联user.id',
  `receiver` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '收货人姓名',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '收货人手机',
  `province` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '省份',
  `city` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '城市',
  `district` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '区县',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '详细地址',
  `is_default` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '0-非默认 1-默认',
  `is_deleted` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户地址表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_address
-- ----------------------------

-- ----------------------------
-- Table structure for user_coupon
-- ----------------------------
DROP TABLE IF EXISTS `user_coupon`;
CREATE TABLE `user_coupon`  (
  `id` bigint NOT NULL COMMENT '主键',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `coupon_id` bigint NOT NULL COMMENT '优惠券ID',
  `get_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '领取时间',
  `use_time` datetime NULL DEFAULT NULL COMMENT '使用时间',
  `order_id` bigint NULL DEFAULT NULL COMMENT '使用订单ID',
  `status` tinyint UNSIGNED NOT NULL COMMENT '0-未使用 1-已使用 2-已过期',
  `is_deleted` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE,
  INDEX `idx_coupon_id`(`coupon_id` ASC) USING BTREE,
  INDEX `idx_order_id`(`order_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户优惠券表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_coupon
-- ----------------------------

-- ----------------------------
-- Table structure for user_detail
-- ----------------------------
DROP TABLE IF EXISTS `user_detail`;
CREATE TABLE `user_detail`  (
  `id` bigint NOT NULL COMMENT '主键',
  `user_id` bigint NOT NULL COMMENT '关联user.id',
  `real_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '真实姓名',
  `gender` tinyint NULL DEFAULT 0 COMMENT '0-未知 1-男 2-女',
  `birthday` date NULL DEFAULT NULL COMMENT '生日',
  `region` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '地区',
  `id_card` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '身份证号',
  `is_id_card_verified` tinyint UNSIGNED NOT NULL COMMENT '0-未认证 1-已认证',
  `last_login_ip` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '上次登录IP',
  `last_login_time` datetime NULL DEFAULT NULL COMMENT '上次登录时间',
  `money` decimal(10, 2) NULL DEFAULT 0.00 COMMENT '账户余额-不适用管理员',
  `is_deleted` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_user_id`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户明细表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_detail
-- ----------------------------

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `id` bigint NOT NULL COMMENT '主键id',
  `role_id` bigint NOT NULL DEFAULT 0 COMMENT '角色id',
  `user_id` bigint NOT NULL DEFAULT 0 COMMENT '用户id',
  `is_deleted` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_role_id`(`role_id` ASC) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES (1, 1984518164557385728, 1, 0, '2025-10-21 23:38:44', '2025-10-21 23:38:44');
INSERT INTO `user_role` VALUES (2, 1984518164557385729, 2, 0, '2025-10-25 21:46:26', '2025-10-25 21:46:29');
INSERT INTO `user_role` VALUES (1984531291629957122, 1984518164557385730, 1984531291470573570, 0, '2025-11-01 16:01:45', '2025-11-01 16:01:45');

SET FOREIGN_KEY_CHECKS = 1;
