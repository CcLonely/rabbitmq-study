/*
 Navicat Premium Data Transfer

 Source Server         : 本地连接
 Source Server Type    : MySQL
 Source Server Version : 80015
 Source Host           : localhost:3306
 Source Schema         : rabbitmq_test

 Target Server Type    : MySQL
 Target Server Version : 80015
 File Encoding         : 65001

 Date: 22/07/2021 15:58:23
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for rabbit_mq_error_retry_log
-- ----------------------------
DROP TABLE IF EXISTS `rabbit_mq_error_retry_log`;
CREATE TABLE `rabbit_mq_error_retry_log`  (
  `message_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键,消息ID',
  `business_line` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '所属业务线',
  `message_content` varchar(1024) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '业务内容',
  `retry_count` int(2) NULL DEFAULT 0 COMMENT '重试次数',
  `remark` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '备注  运营处理备注',
  `retry_status` tinyint(1) NULL DEFAULT NULL COMMENT '重试处理状态',
  `process_status` tinyint(1) NULL DEFAULT NULL COMMENT '处理状态 消息投递使用',
  `mq_exchange` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '交换机',
  `mq_routing_key` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '路由key',
  `mq_queue` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '所属队列',
  `version` bigint(10) UNSIGNED ZEROFILL NULL DEFAULT NULL COMMENT '版本号',
  `create_by_user` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '创建人',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by_user` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '修改人',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`message_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '消息消费异常' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
