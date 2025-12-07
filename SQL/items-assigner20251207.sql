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

 Date: 07/12/2025 17:58:56
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
  `target_id` bigint NOT NULL COMMENT '优惠券目标ID，拥有优惠券为0,也可指向商品或商户',
  `threshold` decimal(10, 2) NOT NULL DEFAULT 0.00 COMMENT '使用门槛',
  `value` decimal(10, 2) NOT NULL COMMENT '优惠值（满减为金额，折扣为百分比）',
  `start_time` datetime NOT NULL COMMENT '生效时间',
  `end_time` datetime NOT NULL COMMENT '失效时间',
  `stock` int NOT NULL DEFAULT 0 COMMENT '发放总量',
  `sending_stock` int NOT NULL DEFAULT 0 COMMENT '领取数量',
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
  `status` tinyint UNSIGNED NOT NULL COMMENT '0-处理中 1-成功 2-失败',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '交易备注',
  `is_deleted` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
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
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '店铺名字',
  `shop_category_id` bigint NOT NULL COMMENT '商品分类id',
  `shop_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '店铺地址',
  `shop_license` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '营业执照ID',
  `shop_license_image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '营业执照图片ID',
  `positiveComment` int NOT NULL DEFAULT 0 COMMENT '好评数',
  `negativeComment` int NOT NULL DEFAULT 0 COMMENT '差评数',
  `shop_status` tinyint UNSIGNED NULL DEFAULT 1 COMMENT '0-开张 1-关闭',
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
-- Table structure for order_main
-- ----------------------------
DROP TABLE IF EXISTS `order_main`;
CREATE TABLE `order_main`  (
  `id` bigint NOT NULL COMMENT '主键',
  `user_id` bigint NOT NULL COMMENT '下单者ID',
  `product_id` bigint NOT NULL COMMENT '商品ID',
  `sku_id` bigint NOT NULL COMMENT '规格ID',
  `quantity` int NOT NULL DEFAULT 1 COMMENT '购买数量',
  `total_price` decimal(10, 2) NOT NULL COMMENT '订单总金额',
  `pay_price` decimal(10, 2) NOT NULL COMMENT '实际支付金额',
  `product_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商品名称快照',
  `sku_specs` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '规格快照',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '订单备注(比如优惠明细，发货要求等)',
  `pay_method` tinyint NOT NULL COMMENT '0-未选择 1-微信支付 2-支付宝支付',
  `status` tinyint NOT NULL COMMENT '0-待付款 1-待发货 2-待收货 3-已完成 4-已取消',
  `is_deleted` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_product_id`(`product_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '订单详情表' ROW_FORMAT = Dynamic;

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
  `operate_time` datetime NOT NULL COMMENT '操作时间',
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '0-待付款 1-待发货 2-待收货 3-已完成 4-已取消',
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
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission`  (
  `id` bigint NOT NULL COMMENT '编号',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '名称',
  `permission_value` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '权限值',
  `is_deleted` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '权限' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES (1996964801054089217, '查询用户', 'user.list', 0, '2025-12-05 23:28:05', '2025-12-05 23:28:05');
INSERT INTO `permission` VALUES (1996964801700012033, '增加用户', 'user.add', 0, '2025-12-05 23:28:05', '2025-12-05 23:28:05');
INSERT INTO `permission` VALUES (1996964801700012034, '更新用户', 'user.update', 0, '2025-12-05 23:28:05', '2025-12-05 23:28:05');
INSERT INTO `permission` VALUES (1996964801700012035, '删除用户', 'user.delete', 0, '2025-12-05 23:28:05', '2025-12-05 23:28:05');
INSERT INTO `permission` VALUES (1996964801700012036, '查询角色', 'role.list', 0, '2025-12-05 23:28:05', '2025-12-05 23:28:05');
INSERT INTO `permission` VALUES (1996964801700012037, '增加角色', 'role.add', 0, '2025-12-05 23:28:05', '2025-12-05 23:28:05');
INSERT INTO `permission` VALUES (1996964801767120897, '更新角色', 'role.update', 0, '2025-12-05 23:28:05', '2025-12-05 23:28:05');
INSERT INTO `permission` VALUES (1996964801767120898, '删除角色', 'role.delete', 0, '2025-12-05 23:28:05', '2025-12-05 23:28:05');
INSERT INTO `permission` VALUES (1996964801767120899, '查询权限', 'permission.list', 0, '2025-12-05 23:28:05', '2025-12-05 23:28:05');
INSERT INTO `permission` VALUES (1996964801767120900, '增加权限', 'permission.add', 0, '2025-12-05 23:28:05', '2025-12-05 23:28:05');
INSERT INTO `permission` VALUES (1996964801767120901, '更新权限', 'permission.update', 0, '2025-12-05 23:28:05', '2025-12-05 23:28:05');
INSERT INTO `permission` VALUES (1996964801767120902, '删除权限', 'permission.delete', 0, '2025-12-05 23:28:05', '2025-12-05 23:28:05');
INSERT INTO `permission` VALUES (1996964801767120903, '查询店铺分类', 'shopCategory.list', 0, '2025-12-05 23:28:05', '2025-12-05 23:28:05');
INSERT INTO `permission` VALUES (1996964801834229762, '增加店铺分类', 'shopCategory.add', 0, '2025-12-05 23:28:05', '2025-12-05 23:28:05');
INSERT INTO `permission` VALUES (1996964801834229763, '更新店铺分类', 'shopCategory.update', 0, '2025-12-05 23:28:05', '2025-12-05 23:28:05');
INSERT INTO `permission` VALUES (1996964801834229764, '删除店铺分类', 'shopCategory.delete', 0, '2025-12-05 23:28:05', '2025-12-05 23:28:05');
INSERT INTO `permission` VALUES (1996964801834229765, '查询商品分类', 'productCategory.list', 0, '2025-12-05 23:28:05', '2025-12-05 23:28:05');
INSERT INTO `permission` VALUES (1996964801834229766, '增加商品分类', 'productCategory.add', 0, '2025-12-05 23:28:05', '2025-12-05 23:28:05');
INSERT INTO `permission` VALUES (1996964801834229767, '更新商品分类', 'productCategory.update', 0, '2025-12-05 23:28:05', '2025-12-05 23:28:05');
INSERT INTO `permission` VALUES (1996964801901338626, '删除商品分类', 'productCategory.delete', 0, '2025-12-05 23:28:05', '2025-12-05 23:28:05');
INSERT INTO `permission` VALUES (1996964801901338627, '查询商品', 'product.list', 0, '2025-12-05 23:28:05', '2025-12-05 23:28:05');
INSERT INTO `permission` VALUES (1996964801901338628, '增加商品', 'product.add', 0, '2025-12-05 23:28:05', '2025-12-05 23:28:05');
INSERT INTO `permission` VALUES (1996964801901338629, '更新商品', 'product.update', 0, '2025-12-05 23:28:05', '2025-12-05 23:28:05');
INSERT INTO `permission` VALUES (1996964801901338630, '删除商品', 'product.delete', 0, '2025-12-05 23:28:05', '2025-12-05 23:28:05');
INSERT INTO `permission` VALUES (1996964801901338631, '查询订单', 'order.list', 0, '2025-12-05 23:28:05', '2025-12-05 23:28:05');
INSERT INTO `permission` VALUES (1996964801901338632, '增加订单', 'order.add', 0, '2025-12-05 23:28:05', '2025-12-05 23:28:05');
INSERT INTO `permission` VALUES (1996964801964253186, '更新订单', 'order.update', 0, '2025-12-05 23:28:05', '2025-12-05 23:28:05');
INSERT INTO `permission` VALUES (1996964801964253187, '查询优惠券', 'coupon.list', 0, '2025-12-05 23:28:05', '2025-12-05 23:28:05');
INSERT INTO `permission` VALUES (1996964801964253188, '增加优惠券', 'coupon.add', 0, '2025-12-05 23:28:05', '2025-12-05 23:28:05');
INSERT INTO `permission` VALUES (1996964801964253189, '更新优惠券', 'coupon.update', 0, '2025-12-05 23:28:05', '2025-12-05 23:28:05');
INSERT INTO `permission` VALUES (1996964801964253190, '删除优惠券', 'coupon.delete', 0, '2025-12-05 23:28:05', '2025-12-05 23:28:05');
INSERT INTO `permission` VALUES (1996964801964253191, '查询订单状态日志', 'orderStatusLog.list', 0, '2025-12-05 23:28:05', '2025-12-05 23:28:05');
INSERT INTO `permission` VALUES (1996964801964253192, '增加订单状态日志', 'orderStatusLog.add', 0, '2025-12-05 23:28:05', '2025-12-05 23:28:05');
INSERT INTO `permission` VALUES (1996964801964253193, '更新订单状态日志', 'orderStatusLog.update', 0, '2025-12-05 23:28:05', '2025-12-05 23:28:05');
INSERT INTO `permission` VALUES (1996964802031362050, '查询用户优惠券', 'userCoupon.list', 0, '2025-12-05 23:28:05', '2025-12-05 23:28:05');
INSERT INTO `permission` VALUES (1996964802031362051, '增加用户优惠券', 'userCoupon.add', 0, '2025-12-05 23:28:05', '2025-12-05 23:28:05');
INSERT INTO `permission` VALUES (1996964802031362052, '更新用户优惠券', 'userCoupon.update', 0, '2025-12-05 23:28:05', '2025-12-05 23:28:05');
INSERT INTO `permission` VALUES (1996964802031362053, '删除用户优惠券', 'userCoupon.delete', 0, '2025-12-05 23:28:05', '2025-12-05 23:28:05');
INSERT INTO `permission` VALUES (1996964802031362054, '查询财务流水', 'financialFlow.list', 0, '2025-12-05 23:28:05', '2025-12-05 23:28:05');

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product`  (
  `id` bigint NOT NULL COMMENT '主键',
  `product_sku_id` bigint NOT NULL COMMENT '默认样式',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商品名称',
  `sales` int NOT NULL COMMENT '总销量',
  `stock` int NOT NULL DEFAULT 0 COMMENT '库存',
  `category_id` bigint NOT NULL COMMENT '关联分类表',
  `positiveComment` int NOT NULL COMMENT '好评数',
  `negativeComment` int NOT NULL COMMENT '差评数',
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
  `is_deleted` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
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
  `sales` int NOT NULL COMMENT '规格销量',
  `stock` int NOT NULL DEFAULT 0 COMMENT '规格库存',
  `logo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品图片',
  `is_deleted` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
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
  `name` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '' COMMENT '角色名称',
  `group` tinyint NOT NULL COMMENT '0-user 1-merchant 2-admin',
  `is_deleted` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1996956488421613570, 'ROLE_SUPER_ADMIN', 2, 0, '2025-12-05 22:55:03', '2025-12-05 22:55:03');
INSERT INTO `role` VALUES (1996956489071730690, 'ROLE_CUSTOM_ADMIN', 2, 0, '2025-12-05 22:55:03', '2025-12-05 22:55:03');
INSERT INTO `role` VALUES (1996956489071730691, 'ROLE_NEW_ADMIN', 2, 0, '2025-12-05 22:55:03', '2025-12-05 22:55:03');
INSERT INTO `role` VALUES (1996956489134645249, 'ROLE_CUSTOM_MERCHANT', 1, 0, '2025-12-05 22:55:03', '2025-12-05 22:55:03');
INSERT INTO `role` VALUES (1996956489134645250, 'ROLE_NEW_MERCHANT', 1, 0, '2025-12-05 22:55:03', '2025-12-05 22:55:03');
INSERT INTO `role` VALUES (1996956489134645251, 'ROLE_CUSTOM_USER', 0, 0, '2025-12-05 22:55:03', '2025-12-05 22:55:03');
INSERT INTO `role` VALUES (1996956489134645252, 'ROLE_NEW_USER', 0, 0, '2025-12-05 22:55:03', '2025-12-05 22:55:03');

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission`  (
  `id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  `permission_id` bigint NOT NULL,
  `is_deleted` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_role_id`(`role_id` ASC) USING BTREE,
  INDEX `idx_permission_id`(`permission_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '角色权限' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_permission
-- ----------------------------
INSERT INTO `role_permission` VALUES (1996965884560240641, 1996956488421613570, 1996964801054089217, 0, '2025-12-05 23:32:23', '2025-12-05 23:32:23');
INSERT INTO `role_permission` VALUES (1996965884560240642, 1996956488421613570, 1996964801700012033, 0, '2025-12-05 23:32:23', '2025-12-05 23:32:23');
INSERT INTO `role_permission` VALUES (1996965884560240643, 1996956488421613570, 1996964801700012034, 0, '2025-12-05 23:32:23', '2025-12-05 23:32:23');
INSERT INTO `role_permission` VALUES (1996965884560240644, 1996956488421613570, 1996964801700012035, 0, '2025-12-05 23:32:23', '2025-12-05 23:32:23');
INSERT INTO `role_permission` VALUES (1996965884560240645, 1996956488421613570, 1996964801700012036, 0, '2025-12-05 23:32:23', '2025-12-05 23:32:23');
INSERT INTO `role_permission` VALUES (1996965884560240646, 1996956488421613570, 1996964801700012037, 0, '2025-12-05 23:32:23', '2025-12-05 23:32:23');
INSERT INTO `role_permission` VALUES (1996965884560240647, 1996956488421613570, 1996964801767120897, 0, '2025-12-05 23:32:23', '2025-12-05 23:32:23');
INSERT INTO `role_permission` VALUES (1996965884560240648, 1996956488421613570, 1996964801767120898, 0, '2025-12-05 23:32:23', '2025-12-05 23:32:23');
INSERT INTO `role_permission` VALUES (1996965884560240649, 1996956488421613570, 1996964801767120899, 0, '2025-12-05 23:32:23', '2025-12-05 23:32:23');
INSERT INTO `role_permission` VALUES (1996965884560240650, 1996956488421613570, 1996964801767120900, 0, '2025-12-05 23:32:23', '2025-12-05 23:32:23');
INSERT INTO `role_permission` VALUES (1996965884560240651, 1996956488421613570, 1996964801767120901, 0, '2025-12-05 23:32:23', '2025-12-05 23:32:23');
INSERT INTO `role_permission` VALUES (1996965884560240652, 1996956488421613570, 1996964801767120902, 0, '2025-12-05 23:32:23', '2025-12-05 23:32:23');
INSERT INTO `role_permission` VALUES (1996965884560240653, 1996956488421613570, 1996964801767120903, 0, '2025-12-05 23:32:23', '2025-12-05 23:32:23');
INSERT INTO `role_permission` VALUES (1996965884560240654, 1996956488421613570, 1996964801834229762, 0, '2025-12-05 23:32:23', '2025-12-05 23:32:23');
INSERT INTO `role_permission` VALUES (1996965884560240655, 1996956488421613570, 1996964801834229763, 0, '2025-12-05 23:32:23', '2025-12-05 23:32:23');
INSERT INTO `role_permission` VALUES (1996965884560240656, 1996956488421613570, 1996964801834229764, 0, '2025-12-05 23:32:23', '2025-12-05 23:32:23');
INSERT INTO `role_permission` VALUES (1996965884560240657, 1996956488421613570, 1996964801834229765, 0, '2025-12-05 23:32:23', '2025-12-05 23:32:23');
INSERT INTO `role_permission` VALUES (1996965884560240658, 1996956488421613570, 1996964801834229766, 0, '2025-12-05 23:32:23', '2025-12-05 23:32:23');
INSERT INTO `role_permission` VALUES (1996965884560240659, 1996956488421613570, 1996964801834229767, 0, '2025-12-05 23:32:23', '2025-12-05 23:32:23');
INSERT INTO `role_permission` VALUES (1996965884560240660, 1996956488421613570, 1996964801901338626, 0, '2025-12-05 23:32:23', '2025-12-05 23:32:23');
INSERT INTO `role_permission` VALUES (1996965884560240661, 1996956488421613570, 1996964801901338627, 0, '2025-12-05 23:32:23', '2025-12-05 23:32:23');
INSERT INTO `role_permission` VALUES (1996965884560240662, 1996956488421613570, 1996964801901338628, 0, '2025-12-05 23:32:23', '2025-12-05 23:32:23');
INSERT INTO `role_permission` VALUES (1996965884560240663, 1996956488421613570, 1996964801901338629, 0, '2025-12-05 23:32:23', '2025-12-05 23:32:23');
INSERT INTO `role_permission` VALUES (1996965884560240664, 1996956488421613570, 1996964801901338630, 0, '2025-12-05 23:32:23', '2025-12-05 23:32:23');
INSERT INTO `role_permission` VALUES (1996965884560240665, 1996956488421613570, 1996964801901338631, 0, '2025-12-05 23:32:23', '2025-12-05 23:32:23');
INSERT INTO `role_permission` VALUES (1996965884560240666, 1996956488421613570, 1996964801901338632, 0, '2025-12-05 23:32:23', '2025-12-05 23:32:23');
INSERT INTO `role_permission` VALUES (1996965884560240667, 1996956488421613570, 1996964801964253186, 0, '2025-12-05 23:32:23', '2025-12-05 23:32:23');
INSERT INTO `role_permission` VALUES (1996965884560240668, 1996956488421613570, 1996964801964253187, 0, '2025-12-05 23:32:23', '2025-12-05 23:32:23');
INSERT INTO `role_permission` VALUES (1996965884560240669, 1996956488421613570, 1996964801964253188, 0, '2025-12-05 23:32:23', '2025-12-05 23:32:23');
INSERT INTO `role_permission` VALUES (1996965884560240670, 1996956488421613570, 1996964801964253189, 0, '2025-12-05 23:32:23', '2025-12-05 23:32:23');
INSERT INTO `role_permission` VALUES (1996965884560240671, 1996956488421613570, 1996964801964253190, 0, '2025-12-05 23:32:23', '2025-12-05 23:32:23');
INSERT INTO `role_permission` VALUES (1996965884560240672, 1996956488421613570, 1996964801964253191, 0, '2025-12-05 23:32:23', '2025-12-05 23:32:23');
INSERT INTO `role_permission` VALUES (1996965884560240673, 1996956488421613570, 1996964801964253192, 0, '2025-12-05 23:32:23', '2025-12-05 23:32:23');
INSERT INTO `role_permission` VALUES (1996965884560240674, 1996956488421613570, 1996964801964253193, 0, '2025-12-05 23:32:23', '2025-12-05 23:32:23');
INSERT INTO `role_permission` VALUES (1996965884560240675, 1996956488421613570, 1996964802031362050, 0, '2025-12-05 23:32:23', '2025-12-05 23:32:23');
INSERT INTO `role_permission` VALUES (1996965884560240676, 1996956488421613570, 1996964802031362051, 0, '2025-12-05 23:32:23', '2025-12-05 23:32:23');
INSERT INTO `role_permission` VALUES (1996965884560240677, 1996956488421613570, 1996964802031362052, 0, '2025-12-05 23:32:23', '2025-12-05 23:32:23');
INSERT INTO `role_permission` VALUES (1996965884560240678, 1996956488421613570, 1996964802031362053, 0, '2025-12-05 23:32:23', '2025-12-05 23:32:23');
INSERT INTO `role_permission` VALUES (1996965884560240679, 1996956488421613570, 1996964802031362054, 0, '2025-12-05 23:32:23', '2025-12-05 23:32:23');

-- ----------------------------
-- Table structure for shop_category
-- ----------------------------
DROP TABLE IF EXISTS `shop_category`;
CREATE TABLE `shop_category`  (
  `id` bigint NOT NULL COMMENT 'id',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '分类名',
  `is_deleted` tinyint NOT NULL DEFAULT 0 COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of shop_category
-- ----------------------------
INSERT INTO `shop_category` VALUES (1996954588037107713, '默认分类', 0, '2025-12-05 22:47:30', '2025-12-05 22:47:30');
INSERT INTO `shop_category` VALUES (1996954588678836226, '书店', 0, '2025-12-05 22:47:30', '2025-12-05 22:47:30');
INSERT INTO `shop_category` VALUES (1996954588678836227, '食品', 0, '2025-12-05 22:47:30', '2025-12-05 22:47:30');

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
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '用户头像',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '状态 0-正常 1-被封禁',
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
INSERT INTO `user` VALUES (1996953140859625474, 'ADMIN', '15626448742', 'www778005729@outlook.com', 'e10adc3949ba59abbe56e057f20f883e', 'SUPER_ADMIN', '', 0, 0, '2025-12-05 22:41:45', '2025-12-05 22:41:45');

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
INSERT INTO `user_role` VALUES (1996959873661231106, 1996956488421613570, 1996953140859625474, 0, '2025-12-05 23:08:30', '2025-12-05 23:08:30');

SET FOREIGN_KEY_CHECKS = 1;
