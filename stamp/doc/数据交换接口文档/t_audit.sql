/*
Navicat MySQL Data Transfer

Source Server         : 本地
Source Server Version : 50717
Source Host           : 127.0.0.1:3306
Source Database       : bb

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-07-18 09:15:37
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_audit
-- ----------------------------
DROP TABLE IF EXISTS `t_audit`;
CREATE TABLE `t_audit` (
  `id` int(64) NOT NULL AUTO_INCREMENT,
  `user` varchar(64) DEFAULT NULL COMMENT '操作者（制章者/签章者/验章者）',
  `seal` varchar(64) DEFAULT NULL COMMENT '印章唯一标识',
  `audit_type` char(1) NOT NULL COMMENT '日志类型（1制章，2签章，3撤章，4验章）',
  `audit_date` datetime DEFAULT NULL COMMENT '日志产生时间',
  `file_name` varchar(255) DEFAULT NULL COMMENT '文件名（签/撤章有值）',
  `file_signedData` longtext COMMENT '文件摘要（签/撤章有值）',
  `audit_result` char(1) DEFAULT NULL COMMENT '操作结果（0成功，1失败）',
  `reason` varchar(255) DEFAULT NULL COMMENT '原因（若操作失败有值）',
  `cert_serial_number` varchar(100) DEFAULT NULL COMMENT '证书序列号',
  `IP` varchar(64) DEFAULT NULL COMMENT 'IP地址',
  `MAC` varchar(64) DEFAULT NULL COMMENT 'MAC地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='印章日志表（用于审计）';
