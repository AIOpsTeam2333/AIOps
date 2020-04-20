/*
 Navicat Premium Data Transfer

 Source Server         : mysql-connection
 Source Server Type    : MySQL
 Source Server Version : 80013
 Source Host           : localhost:3306
 Source Schema         : aiops

 Target Server Type    : MySQL
 Target Server Version : 80013
 File Encoding         : 65001

 Date: 19/04/2020 13:57:10
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for process_metadata_endpoint
-- ----------------------------
DROP TABLE IF EXISTS `process_metadata_endpoint`;
CREATE TABLE `process_metadata_endpoint`  (
  `service_endpoint_id` int(11) NOT NULL,
  `pre_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  INDEX `service_endpoint_id`(`service_endpoint_id`) USING BTREE,
  CONSTRAINT `service_endpoint_id` FOREIGN KEY (`service_endpoint_id`) REFERENCES `metadata_service_endpoint` (`service_endpoint_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for process_metadata_service
-- ----------------------------
DROP TABLE IF EXISTS `process_metadata_service`;
CREATE TABLE `process_metadata_service`  (
  `service_id` int(11) NOT NULL,
  `pre_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  INDEX `service_node_id`(`service_id`) USING BTREE,
  CONSTRAINT `service_node_id` FOREIGN KEY (`service_id`) REFERENCES `metadata_service_node` (`node_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for process_trace_segment
-- ----------------------------
DROP TABLE IF EXISTS `process_trace_segment`;
CREATE TABLE `process_trace_segment`  (
  `segment_id` int(11) NOT NULL,
  `pre_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`segment_id`) USING BTREE,
  CONSTRAINT `process_segment_id` FOREIGN KEY (`segment_id`) REFERENCES `trace_segment` (`segment_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for process_trace_trace
-- ----------------------------
DROP TABLE IF EXISTS `process_trace_trace`;
CREATE TABLE `process_trace_trace`  (
  `trace_id` int(11) NOT NULL,
  `pre_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`trace_id`) USING BTREE,
  CONSTRAINT `process_trace_id` FOREIGN KEY (`trace_id`) REFERENCES `trace_trace` (`trace_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
