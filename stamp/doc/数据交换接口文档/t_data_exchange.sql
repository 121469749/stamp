/*
Navicat MySQL Data Transfer

Source Server         : 本地
Source Server Version : 50717
Source Host           : 127.0.0.1:3306
Source Database       : prev_stamp

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-07-12 10:26:22
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_data_exchange
-- ----------------------------
DROP TABLE IF EXISTS `t_data_exchange`;
CREATE TABLE `t_data_exchange` (
  `id` int(100) NOT NULL COMMENT '自增id',
  `business_id` varchar(100) NOT NULL COMMENT '业务流水号',
  `seal_type` int(3) NOT NULL COMMENT '印章类型',
  `seal_style` char(2) NOT NULL COMMENT '印章属性（物理/电子）',
  `seal_material` varchar(20) DEFAULT NULL COMMENT '印章材料',
  `seal_count` int(3) NOT NULL COMMENT '刻制数量',
  `business_type` varchar(100) NOT NULL COMMENT '业务部门名称',
  `make_comp_area` varchar(6) NOT NULL COMMENT '刻章点行政区划',
  `make_comp_socialcreditcode` varchar(18) NOT NULL COMMENT '刻章点统一社会信用代码',
  `use_comp_area` varchar(6) NOT NULL COMMENT '企业行政区划',
  `use_comp_socialcreditcode` varchar(18) NOT NULL COMMENT '企业统一社会信用代码',
  `use_comp_name` varchar(100) NOT NULL COMMENT '企业名称',
  `use_comp_type` varchar(100) NOT NULL COMMENT '企业类型',
  `use_comp_address` varchar(255) NOT NULL COMMENT '企业住所',
  `use_foundingDate` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '企业创建日期',
  `legal_name` varchar(64) NOT NULL COMMENT '法人名称',
  `legal_phone` varchar(64) NOT NULL COMMENT '法人手机',
  `legal_certType` varchar(64) NOT NULL COMMENT '法人证件类型',
  `legal_certCode` varchar(64) NOT NULL COMMENT '法人证件号码',
  `agent_name` varchar(64) NOT NULL COMMENT '经办人名称',
  `agent_phone` varchar(64) NOT NULL COMMENT '经办人手机',
  `agent_certType` varchar(64) NOT NULL COMMENT '经办人证件类型',
  `agent_certCode` varchar(64) NOT NULL COMMENT '经办人证件号码',
  `attachments` varchar(255) DEFAULT NULL COMMENT '附件信息',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志',
  `create_date` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '数据创建时间',
  `update_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '数据更新时间',
  `ex_flag` char(1) NOT NULL DEFAULT '0' COMMENT '数据交换标志',
  `ex_fail_reason` varchar(255) DEFAULT NULL COMMENT '交换失败原因',
  `default1` varchar(255) DEFAULT NULL COMMENT '备用字段1',
  `default2` varchar(255) DEFAULT NULL COMMENT '备用字段2',
  `default3` varchar(255) DEFAULT NULL COMMENT '备用字段3',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk` (`business_id`,`seal_type`,`seal_style`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='印章备案数据交换表';
