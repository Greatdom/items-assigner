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

 Date: 24/12/2025 11:29:24
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
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '售后单表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of after_sale
-- ----------------------------

-- ----------------------------
-- Table structure for coupon
-- ----------------------------
DROP TABLE IF EXISTS `coupon`;
CREATE TABLE `coupon`  (
  `id` bigint NOT NULL COMMENT '主键',
  `pointer` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '优惠券指向对象,如果指向商户则是商户用户名,如果指向商品则指向商品名,否则标注全场通用',
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
  `version` bigint NOT NULL DEFAULT 0 COMMENT '版本号',
  `status` tinyint UNSIGNED NOT NULL DEFAULT 1 COMMENT '0-不可用 1-可用',
  `is_deleted` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_target_id`(`target_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '优惠券表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of coupon
-- ----------------------------
INSERT INTO `coupon` VALUES (2003658193305096194, '全场通用', '全场优惠券满二百打七折三年期二零二五年十二月中旬开始', 1, 0, 0, 0, 200.00, 30.00, '2025-12-16 14:01:28', '2028-12-16 14:01:28', 100, 1, 0, 1, 0, '2025-12-24 10:45:14', '2025-12-24 11:23:24');
INSERT INTO `coupon` VALUES (2003666159022485505, 'ADMIN_SHOP', '超级管理员商店优惠券满二百减五十三年期二零二五年十二月中旬开始', 0, 1, 0, 1996953140859625474, 200.00, 50.00, '2025-12-16 14:01:28', '2028-12-16 14:01:28', 100, 0, 0, 1, 0, '2025-12-24 11:16:53', '2025-12-24 11:16:53');

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
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '财务流水表' ROW_FORMAT = DYNAMIC;

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
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '店铺名字',
  `shop_category_id` bigint NOT NULL DEFAULT 1996954588037107713 COMMENT '商品分类id',
  `shop_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '店铺地址',
  `shop_license` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '营业执照ID',
  `shop_license_image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '营业执照图片ID',
  `positive_comment` int NOT NULL DEFAULT 0 COMMENT '好评数',
  `negative_comment` int NOT NULL DEFAULT 0 COMMENT '差评数',
  `shop_status` tinyint UNSIGNED NULL DEFAULT 1 COMMENT '0-开张 1-关闭',
  `is_deleted` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_user_id`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '商户补强表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of merchant_supplement
-- ----------------------------
INSERT INTO `merchant_supplement` VALUES (1, 1996953140859625474, 'ADMIN_SHOP', 1996954588037107713, 'WEBSIZE', '00X00X00X', '000x0x000x0x0x000x', 0, 0, 1, 0, '2025-12-09 21:25:36', '2025-12-24 00:29:58');
INSERT INTO `merchant_supplement` VALUES (2001626552999690242, 2001626552919998466, '', 1996954588037107713, '远在天边,近在眼前', '', '', 0, 0, 1, 0, '2025-12-18 20:12:13', '2025-12-18 20:12:13');
INSERT INTO `merchant_supplement` VALUES (2001973055194243074, 2001625299854921729, '', 1996954588037107713, '', '', '', 0, 0, 1, 0, '2025-12-19 19:09:06', '2025-12-19 19:09:06');

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
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '订单地址表' ROW_FORMAT = DYNAMIC;

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
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '订单评价表' ROW_FORMAT = DYNAMIC;

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
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '订单详情表' ROW_FORMAT = DYNAMIC;

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
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '订单状态日志表' ROW_FORMAT = DYNAMIC;

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
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '权限' ROW_FORMAT = DYNAMIC;

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
  `username` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商户用户名',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商品名称',
  `sales` int NOT NULL DEFAULT 0 COMMENT '总销量',
  `stock` int NOT NULL DEFAULT 0 COMMENT '库存',
  `category_id` bigint NOT NULL COMMENT '关联分类表',
  `positive_comment` int NOT NULL DEFAULT 0 COMMENT '好评数',
  `negative_comment` int NOT NULL DEFAULT 0 COMMENT '差评数',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '商品描述',
  `user_id` bigint NOT NULL COMMENT '上架商户ID',
  `version` bigint NOT NULL DEFAULT 0 COMMENT '版本号',
  `status` tinyint UNSIGNED NOT NULL DEFAULT 1 COMMENT '0-下架 1-上架',
  `is_deleted` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_category_id`(`category_id` ASC) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '商品表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of product
-- ----------------------------
INSERT INTO `product` VALUES (2000808472433655809, 2000808472433655810, 'ADMIN', '小丑是怎么炼成的', 0, 10, 2000803576611430401, 0, 0, 'good book!', 1996953140859625474, 0, 1, 0, '2025-12-16 14:01:28', '2025-12-16 14:01:28');
INSERT INTO `product` VALUES (2000808873962766337, 2003647779074756609, 'ADMIN', '布莱克凯特是由石油提炼的吗?', 0, 50, 2000803576611430401, 0, 0, 'BlackCat!', 1996953140859625474, 0, 1, 0, '2025-12-16 14:03:03', '2025-12-24 10:03:51');

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
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '商品分类表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of product_category
-- ----------------------------
INSERT INTO `product_category` VALUES (2000803576611430401, '基础分类', 0, '2025-12-16 13:42:00', '2025-12-16 13:42:00');
INSERT INTO `product_category` VALUES (2000803968233545730, '书籍', 0, '2025-12-16 13:43:34', '2025-12-16 13:43:34');
INSERT INTO `product_category` VALUES (2000804395725475841, '家具', 0, '2025-12-16 13:45:16', '2025-12-16 13:45:16');

-- ----------------------------
-- Table structure for product_sku
-- ----------------------------
DROP TABLE IF EXISTS `product_sku`;
CREATE TABLE `product_sku`  (
  `id` bigint NOT NULL COMMENT '主键',
  `product_id` bigint NOT NULL COMMENT '关联商品ID',
  `specs` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '规格描述，如\"红色-XL\"',
  `price` decimal(10, 2) NOT NULL COMMENT '规格售价',
  `sales` int NOT NULL DEFAULT 0 COMMENT '规格销量',
  `stock` int NOT NULL DEFAULT 0 COMMENT '规格库存',
  `logo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品图片',
  `version` bigint NOT NULL DEFAULT 0 COMMENT '版本号',
  `is_deleted` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_product_id`(`product_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '商品规格表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of product_sku
-- ----------------------------
INSERT INTO `product_sku` VALUES (2000808472433655810, 2000808472433655809, '基础款', 19.99, 0, 10, '', 0, 0, '2025-12-16 14:01:28', '2025-12-16 14:01:28');
INSERT INTO `product_sku` VALUES (2000808873962766338, 2000808873962766337, '豪华款', 39.99, 0, 5, '', 0, 0, '2025-12-16 14:03:03', '2025-12-16 14:03:03');
INSERT INTO `product_sku` VALUES (2000808873962766339, 2000808873962766337, '基础款', 29.99, 0, 20, '', 0, 0, '2025-12-16 14:03:03', '2025-12-16 14:03:03');
INSERT INTO `product_sku` VALUES (2003646702854123521, 2000808873962766337, '二手款', 9.99, 0, 20, '', 0, 0, '2025-12-24 09:59:35', '2025-12-24 09:59:35');
INSERT INTO `product_sku` VALUES (2003647779074756609, 2000808873962766337, '典藏款', 59.99, 0, 5, '', 0, 0, '2025-12-24 10:03:51', '2025-12-24 10:03:51');

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
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

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
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '角色权限' ROW_FORMAT = DYNAMIC;

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
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

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
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1996953140859625474, 'ADMIN', '15626448742', 'www778005729@outlook.com', 'e10adc3949ba59abbe56e057f20f883e', 'WDD', '1996953140859625474_ea461f826d094a5fabe8f2b474283693.jpeg', 0, 0, '2025-12-05 22:41:45', '2025-12-23 19:32:53');
INSERT INTO `user` VALUES (2001625299854921729, 'firstUser', '13809711111', '778005722@qq.com', 'e10adc3949ba59abbe56e057f20f883e', '', '', 0, 0, '2025-12-18 20:07:15', '2025-12-18 20:07:15');
INSERT INTO `user` VALUES (2001626552919998466, 'secondUser', '13809711112', '778005723@qq.com', 'e10adc3949ba59abbe56e057f20f883e', '', '', 0, 0, '2025-12-18 20:12:13', '2025-12-18 20:24:42');

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
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户地址表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_address
-- ----------------------------
INSERT INTO `user_address` VALUES (2003505105655685122, 1996953140859625474, '布莱克凯特', '15626448742', '广东', '广州', '番禺', '广东工业大学', 1, 0, '2025-12-24 00:36:55', '2025-12-24 00:36:55');
INSERT INTO `user_address` VALUES (2003505705483100161, 1996953140859625474, '布莱克凯特', '15626448742', '广东', '汕头', '达濠', '西泽园', 0, 0, '2025-12-24 00:39:18', '2025-12-24 00:41:50');

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
  `status` tinyint(1) NOT NULL COMMENT '0-未使用 1-已使用',
  `is_deleted` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE,
  INDEX `idx_coupon_id`(`coupon_id` ASC) USING BTREE,
  INDEX `idx_order_id`(`order_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户优惠券表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_coupon
-- ----------------------------
INSERT INTO `user_coupon` VALUES (2003667795715403778, 1996953140859625474, 2003658193305096194, '2025-12-24 11:23:24', NULL, NULL, 0, 0, '2025-12-24 11:23:24', '2025-12-24 11:23:24');

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
  `is_id_card_verified` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '0-未认证 1-已认证',
  `money` decimal(10, 2) NULL DEFAULT 0.00 COMMENT '账户余额-不适用管理员',
  `is_deleted` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_user_id`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户明细表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_detail
-- ----------------------------
INSERT INTO `user_detail` VALUES (1, 1996953140859625474, '布莱克凯特', 1, '2005-07-31', '广东工业大学', '000x0x000x0x0x000x', 1, 0.00, 0, '2025-12-09 20:21:56', '2025-12-23 20:00:02');
INSERT INTO `user_detail` VALUES (2001625299854921730, 2001625299854921729, '', 0, NULL, '', '', 0, 0.00, 0, '2025-12-18 20:07:15', '2025-12-18 20:07:15');
INSERT INTO `user_detail` VALUES (2001626552936775681, 2001626552919998466, '', 0, NULL, '', '', 0, 0.00, 0, '2025-12-18 20:12:13', '2025-12-18 20:12:13');

-- ----------------------------
-- Table structure for user_file_bind
-- ----------------------------
DROP TABLE IF EXISTS `user_file_bind`;
CREATE TABLE `user_file_bind`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `user_id` bigint NOT NULL COMMENT '用户唯一标识',
  `file_type` tinyint NOT NULL COMMENT '文件类型,0-压缩文件 1-非压缩文件',
  `file_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '绑定的文件唯一名称',
  `is_valid` tinyint NULL DEFAULT 1 COMMENT '是否有效（1-有效，0-解绑）',
  `create_time` datetime NOT NULL COMMENT '绑定时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `is_deleted` tinyint NULL DEFAULT 0 COMMENT '逻辑删除标识',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户文件绑定表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_file_bind
-- ----------------------------
INSERT INTO `user_file_bind` VALUES (2003387409710575617, 1996953140859625474, 1, '1996953140859625474_a92bf18e28cc459db9aa037963e79843.jpg', 0, '2025-12-23 16:49:14', '2025-12-23 16:49:14', 1);
INSERT INTO `user_file_bind` VALUES (2003387514383626242, 1996953140859625474, 1, '1996953140859625474_ea461f826d094a5fabe8f2b474283693.jpeg', 1, '2025-12-23 16:49:39', '2025-12-23 16:49:39', 0);

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
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES (1996959873661231106, 1996956488421613570, 1996953140859625474, 0, '2025-12-05 23:08:30', '2025-12-05 23:08:30');
INSERT INTO `user_role` VALUES (2001626552999690241, 1996956489134645252, 2001626552919998466, 0, '2025-12-18 20:12:13', '2025-12-18 20:12:13');
INSERT INTO `user_role` VALUES (2001626552999690243, 1996956489134645250, 2001626552919998466, 0, '2025-12-18 20:12:13', '2025-12-18 20:12:13');
INSERT INTO `user_role` VALUES (2001973570972000257, 1996956489071730690, 2001625299854921729, 0, '2025-12-19 19:11:09', '2025-12-19 19:11:09');
INSERT INTO `user_role` VALUES (2001973570972000258, 1996956489134645250, 2001625299854921729, 0, '2025-12-19 19:11:09', '2025-12-19 19:11:09');
INSERT INTO `user_role` VALUES (2001973570972000259, 1996956489134645252, 2001625299854921729, 0, '2025-12-19 19:11:09', '2025-12-19 19:11:09');
INSERT INTO `user_role` VALUES (2003432016213274625, 1996956489134645251, 1996953140859625474, 1, '2025-12-23 19:46:29', '2025-12-23 19:46:29');
INSERT INTO `user_role` VALUES (2003435425649659906, 1996956489134645251, 1996953140859625474, 0, '2025-12-23 20:00:02', '2025-12-23 20:00:02');
INSERT INTO `user_role` VALUES (2003503354747674626, 1996956489134645249, 1996953140859625474, 0, '2025-12-24 00:29:58', '2025-12-24 00:29:58');

SET FOREIGN_KEY_CHECKS = 1;
