/*
Navicat MySQL Data Transfer

Source Server         : 本机
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : stamp4

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2017-11-20 15:35:15
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for act_evt_log
-- ----------------------------
DROP TABLE IF EXISTS `act_evt_log`;
CREATE TABLE `act_evt_log` (
  `LOG_NR_` bigint(20) NOT NULL AUTO_INCREMENT,
  `TYPE_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TIME_STAMP_` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  `USER_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `DATA_` longblob,
  `LOCK_OWNER_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `LOCK_TIME_` timestamp(3) NULL DEFAULT NULL,
  `IS_PROCESSED_` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`LOG_NR_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for act_ge_bytearray
-- ----------------------------
DROP TABLE IF EXISTS `act_ge_bytearray`;
CREATE TABLE `act_ge_bytearray` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `REV_` int(11) DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `DEPLOYMENT_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `BYTES_` longblob,
  `GENERATED_` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_FK_BYTEARR_DEPL` (`DEPLOYMENT_ID_`),
  CONSTRAINT `ACT_FK_BYTEARR_DEPL` FOREIGN KEY (`DEPLOYMENT_ID_`) REFERENCES `act_re_deployment` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for act_ge_property
-- ----------------------------
DROP TABLE IF EXISTS `act_ge_property`;
CREATE TABLE `act_ge_property` (
  `NAME_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `VALUE_` varchar(300) COLLATE utf8_bin DEFAULT NULL,
  `REV_` int(11) DEFAULT NULL,
  PRIMARY KEY (`NAME_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for act_hi_actinst
-- ----------------------------
DROP TABLE IF EXISTS `act_hi_actinst`;
CREATE TABLE `act_hi_actinst` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `ACT_ID_` varchar(255) COLLATE utf8_bin NOT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `CALL_PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `ACT_NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `ACT_TYPE_` varchar(255) COLLATE utf8_bin NOT NULL,
  `ASSIGNEE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `START_TIME_` datetime(3) NOT NULL,
  `END_TIME_` datetime(3) DEFAULT NULL,
  `DURATION_` bigint(20) DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_HI_ACT_INST_START` (`START_TIME_`),
  KEY `ACT_IDX_HI_ACT_INST_END` (`END_TIME_`),
  KEY `ACT_IDX_HI_ACT_INST_PROCINST` (`PROC_INST_ID_`,`ACT_ID_`),
  KEY `ACT_IDX_HI_ACT_INST_EXEC` (`EXECUTION_ID_`,`ACT_ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for act_hi_attachment
-- ----------------------------
DROP TABLE IF EXISTS `act_hi_attachment`;
CREATE TABLE `act_hi_attachment` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `USER_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `DESCRIPTION_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `URL_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `CONTENT_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TIME_` datetime(3) DEFAULT NULL,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for act_hi_comment
-- ----------------------------
DROP TABLE IF EXISTS `act_hi_comment`;
CREATE TABLE `act_hi_comment` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TIME_` datetime(3) NOT NULL,
  `USER_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `ACTION_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `MESSAGE_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `FULL_MSG_` longblob,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for act_hi_detail
-- ----------------------------
DROP TABLE IF EXISTS `act_hi_detail`;
CREATE TABLE `act_hi_detail` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin NOT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `ACT_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin NOT NULL,
  `VAR_TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `REV_` int(11) DEFAULT NULL,
  `TIME_` datetime(3) NOT NULL,
  `BYTEARRAY_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `DOUBLE_` double DEFAULT NULL,
  `LONG_` bigint(20) DEFAULT NULL,
  `TEXT_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TEXT2_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_HI_DETAIL_PROC_INST` (`PROC_INST_ID_`),
  KEY `ACT_IDX_HI_DETAIL_ACT_INST` (`ACT_INST_ID_`),
  KEY `ACT_IDX_HI_DETAIL_TIME` (`TIME_`),
  KEY `ACT_IDX_HI_DETAIL_NAME` (`NAME_`),
  KEY `ACT_IDX_HI_DETAIL_TASK_ID` (`TASK_ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for act_hi_identitylink
-- ----------------------------
DROP TABLE IF EXISTS `act_hi_identitylink`;
CREATE TABLE `act_hi_identitylink` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `GROUP_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `USER_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_HI_IDENT_LNK_USER` (`USER_ID_`),
  KEY `ACT_IDX_HI_IDENT_LNK_TASK` (`TASK_ID_`),
  KEY `ACT_IDX_HI_IDENT_LNK_PROCINST` (`PROC_INST_ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for act_hi_procinst
-- ----------------------------
DROP TABLE IF EXISTS `act_hi_procinst`;
CREATE TABLE `act_hi_procinst` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `BUSINESS_KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `START_TIME_` datetime(3) NOT NULL,
  `END_TIME_` datetime(3) DEFAULT NULL,
  `DURATION_` bigint(20) DEFAULT NULL,
  `START_USER_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `START_ACT_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `END_ACT_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SUPER_PROCESS_INSTANCE_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `DELETE_REASON_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  UNIQUE KEY `PROC_INST_ID_` (`PROC_INST_ID_`),
  KEY `ACT_IDX_HI_PRO_INST_END` (`END_TIME_`),
  KEY `ACT_IDX_HI_PRO_I_BUSKEY` (`BUSINESS_KEY_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for act_hi_taskinst
-- ----------------------------
DROP TABLE IF EXISTS `act_hi_taskinst`;
CREATE TABLE `act_hi_taskinst` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TASK_DEF_KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PARENT_TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `DESCRIPTION_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `OWNER_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `ASSIGNEE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `START_TIME_` datetime(3) NOT NULL,
  `CLAIM_TIME_` datetime(3) DEFAULT NULL,
  `END_TIME_` datetime(3) DEFAULT NULL,
  `DURATION_` bigint(20) DEFAULT NULL,
  `DELETE_REASON_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `PRIORITY_` int(11) DEFAULT NULL,
  `DUE_DATE_` datetime(3) DEFAULT NULL,
  `FORM_KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CATEGORY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_HI_TASK_INST_PROCINST` (`PROC_INST_ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for act_hi_varinst
-- ----------------------------
DROP TABLE IF EXISTS `act_hi_varinst`;
CREATE TABLE `act_hi_varinst` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin NOT NULL,
  `VAR_TYPE_` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `REV_` int(11) DEFAULT NULL,
  `BYTEARRAY_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `DOUBLE_` double DEFAULT NULL,
  `LONG_` bigint(20) DEFAULT NULL,
  `TEXT_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TEXT2_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `CREATE_TIME_` datetime(3) DEFAULT NULL,
  `LAST_UPDATED_TIME_` datetime(3) DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_HI_PROCVAR_PROC_INST` (`PROC_INST_ID_`),
  KEY `ACT_IDX_HI_PROCVAR_NAME_TYPE` (`NAME_`,`VAR_TYPE_`),
  KEY `ACT_IDX_HI_PROCVAR_TASK_ID` (`TASK_ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for act_id_group
-- ----------------------------
DROP TABLE IF EXISTS `act_id_group`;
CREATE TABLE `act_id_group` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `REV_` int(11) DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for act_id_info
-- ----------------------------
DROP TABLE IF EXISTS `act_id_info`;
CREATE TABLE `act_id_info` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `REV_` int(11) DEFAULT NULL,
  `USER_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TYPE_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `VALUE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PASSWORD_` longblob,
  `PARENT_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for act_id_membership
-- ----------------------------
DROP TABLE IF EXISTS `act_id_membership`;
CREATE TABLE `act_id_membership` (
  `USER_ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `GROUP_ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  PRIMARY KEY (`USER_ID_`,`GROUP_ID_`),
  KEY `ACT_FK_MEMB_GROUP` (`GROUP_ID_`),
  CONSTRAINT `ACT_FK_MEMB_GROUP` FOREIGN KEY (`GROUP_ID_`) REFERENCES `act_id_group` (`ID_`),
  CONSTRAINT `ACT_FK_MEMB_USER` FOREIGN KEY (`USER_ID_`) REFERENCES `act_id_user` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for act_id_user
-- ----------------------------
DROP TABLE IF EXISTS `act_id_user`;
CREATE TABLE `act_id_user` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `REV_` int(11) DEFAULT NULL,
  `FIRST_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `LAST_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `EMAIL_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PWD_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PICTURE_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for act_procdef_info
-- ----------------------------
DROP TABLE IF EXISTS `act_procdef_info`;
CREATE TABLE `act_procdef_info` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `INFO_JSON_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  UNIQUE KEY `ACT_UNIQ_INFO_PROCDEF` (`PROC_DEF_ID_`),
  KEY `ACT_IDX_INFO_PROCDEF` (`PROC_DEF_ID_`),
  KEY `ACT_FK_INFO_JSON_BA` (`INFO_JSON_ID_`),
  CONSTRAINT `ACT_FK_INFO_JSON_BA` FOREIGN KEY (`INFO_JSON_ID_`) REFERENCES `act_ge_bytearray` (`ID_`),
  CONSTRAINT `ACT_FK_INFO_PROCDEF` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `act_re_procdef` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for act_re_deployment
-- ----------------------------
DROP TABLE IF EXISTS `act_re_deployment`;
CREATE TABLE `act_re_deployment` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CATEGORY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  `DEPLOY_TIME_` timestamp(3) NULL DEFAULT NULL,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for act_re_model
-- ----------------------------
DROP TABLE IF EXISTS `act_re_model`;
CREATE TABLE `act_re_model` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CATEGORY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CREATE_TIME_` timestamp(3) NULL DEFAULT NULL,
  `LAST_UPDATE_TIME_` timestamp(3) NULL DEFAULT NULL,
  `VERSION_` int(11) DEFAULT NULL,
  `META_INFO_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `DEPLOYMENT_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EDITOR_SOURCE_VALUE_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EDITOR_SOURCE_EXTRA_VALUE_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  PRIMARY KEY (`ID_`),
  KEY `ACT_FK_MODEL_SOURCE` (`EDITOR_SOURCE_VALUE_ID_`),
  KEY `ACT_FK_MODEL_SOURCE_EXTRA` (`EDITOR_SOURCE_EXTRA_VALUE_ID_`),
  KEY `ACT_FK_MODEL_DEPLOYMENT` (`DEPLOYMENT_ID_`),
  CONSTRAINT `ACT_FK_MODEL_DEPLOYMENT` FOREIGN KEY (`DEPLOYMENT_ID_`) REFERENCES `act_re_deployment` (`ID_`),
  CONSTRAINT `ACT_FK_MODEL_SOURCE` FOREIGN KEY (`EDITOR_SOURCE_VALUE_ID_`) REFERENCES `act_ge_bytearray` (`ID_`),
  CONSTRAINT `ACT_FK_MODEL_SOURCE_EXTRA` FOREIGN KEY (`EDITOR_SOURCE_EXTRA_VALUE_ID_`) REFERENCES `act_ge_bytearray` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for act_re_procdef
-- ----------------------------
DROP TABLE IF EXISTS `act_re_procdef`;
CREATE TABLE `act_re_procdef` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `CATEGORY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `KEY_` varchar(255) COLLATE utf8_bin NOT NULL,
  `VERSION_` int(11) NOT NULL,
  `DEPLOYMENT_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `RESOURCE_NAME_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `DGRM_RESOURCE_NAME_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `DESCRIPTION_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `HAS_START_FORM_KEY_` tinyint(4) DEFAULT NULL,
  `HAS_GRAPHICAL_NOTATION_` tinyint(4) DEFAULT NULL,
  `SUSPENSION_STATE_` int(11) DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  PRIMARY KEY (`ID_`),
  UNIQUE KEY `ACT_UNIQ_PROCDEF` (`KEY_`,`VERSION_`,`TENANT_ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for act_ru_event_subscr
-- ----------------------------
DROP TABLE IF EXISTS `act_ru_event_subscr`;
CREATE TABLE `act_ru_event_subscr` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `EVENT_TYPE_` varchar(255) COLLATE utf8_bin NOT NULL,
  `EVENT_NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `ACTIVITY_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `CONFIGURATION_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CREATED_` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_EVENT_SUBSCR_CONFIG_` (`CONFIGURATION_`),
  KEY `ACT_FK_EVENT_EXEC` (`EXECUTION_ID_`),
  CONSTRAINT `ACT_FK_EVENT_EXEC` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `act_ru_execution` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for act_ru_execution
-- ----------------------------
DROP TABLE IF EXISTS `act_ru_execution`;
CREATE TABLE `act_ru_execution` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `REV_` int(11) DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `BUSINESS_KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PARENT_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `SUPER_EXEC_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `ACT_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `IS_ACTIVE_` tinyint(4) DEFAULT NULL,
  `IS_CONCURRENT_` tinyint(4) DEFAULT NULL,
  `IS_SCOPE_` tinyint(4) DEFAULT NULL,
  `IS_EVENT_SCOPE_` tinyint(4) DEFAULT NULL,
  `SUSPENSION_STATE_` int(11) DEFAULT NULL,
  `CACHED_ENT_STATE_` int(11) DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `LOCK_TIME_` timestamp(3) NULL DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_EXEC_BUSKEY` (`BUSINESS_KEY_`),
  KEY `ACT_FK_EXE_PROCINST` (`PROC_INST_ID_`),
  KEY `ACT_FK_EXE_PARENT` (`PARENT_ID_`),
  KEY `ACT_FK_EXE_SUPER` (`SUPER_EXEC_`),
  KEY `ACT_FK_EXE_PROCDEF` (`PROC_DEF_ID_`),
  CONSTRAINT `ACT_FK_EXE_PARENT` FOREIGN KEY (`PARENT_ID_`) REFERENCES `act_ru_execution` (`ID_`),
  CONSTRAINT `ACT_FK_EXE_PROCDEF` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `act_re_procdef` (`ID_`),
  CONSTRAINT `ACT_FK_EXE_PROCINST` FOREIGN KEY (`PROC_INST_ID_`) REFERENCES `act_ru_execution` (`ID_`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `ACT_FK_EXE_SUPER` FOREIGN KEY (`SUPER_EXEC_`) REFERENCES `act_ru_execution` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for act_ru_identitylink
-- ----------------------------
DROP TABLE IF EXISTS `act_ru_identitylink`;
CREATE TABLE `act_ru_identitylink` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `REV_` int(11) DEFAULT NULL,
  `GROUP_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `USER_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_IDENT_LNK_USER` (`USER_ID_`),
  KEY `ACT_IDX_IDENT_LNK_GROUP` (`GROUP_ID_`),
  KEY `ACT_IDX_ATHRZ_PROCEDEF` (`PROC_DEF_ID_`),
  KEY `ACT_FK_TSKASS_TASK` (`TASK_ID_`),
  KEY `ACT_FK_IDL_PROCINST` (`PROC_INST_ID_`),
  CONSTRAINT `ACT_FK_ATHRZ_PROCEDEF` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `act_re_procdef` (`ID_`),
  CONSTRAINT `ACT_FK_IDL_PROCINST` FOREIGN KEY (`PROC_INST_ID_`) REFERENCES `act_ru_execution` (`ID_`),
  CONSTRAINT `ACT_FK_TSKASS_TASK` FOREIGN KEY (`TASK_ID_`) REFERENCES `act_ru_task` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for act_ru_job
-- ----------------------------
DROP TABLE IF EXISTS `act_ru_job`;
CREATE TABLE `act_ru_job` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin NOT NULL,
  `LOCK_EXP_TIME_` timestamp(3) NULL DEFAULT NULL,
  `LOCK_OWNER_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `EXCLUSIVE_` tinyint(1) DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROCESS_INSTANCE_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `RETRIES_` int(11) DEFAULT NULL,
  `EXCEPTION_STACK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EXCEPTION_MSG_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `DUEDATE_` timestamp(3) NULL DEFAULT NULL,
  `REPEAT_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `HANDLER_TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `HANDLER_CFG_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  PRIMARY KEY (`ID_`),
  KEY `ACT_FK_JOB_EXCEPTION` (`EXCEPTION_STACK_ID_`),
  CONSTRAINT `ACT_FK_JOB_EXCEPTION` FOREIGN KEY (`EXCEPTION_STACK_ID_`) REFERENCES `act_ge_bytearray` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for act_ru_task
-- ----------------------------
DROP TABLE IF EXISTS `act_ru_task`;
CREATE TABLE `act_ru_task` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `REV_` int(11) DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PARENT_TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `DESCRIPTION_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TASK_DEF_KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `OWNER_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `ASSIGNEE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `DELEGATION_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PRIORITY_` int(11) DEFAULT NULL,
  `CREATE_TIME_` timestamp(3) NULL DEFAULT NULL,
  `DUE_DATE_` datetime(3) DEFAULT NULL,
  `CATEGORY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SUSPENSION_STATE_` int(11) DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  `FORM_KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_TASK_CREATE` (`CREATE_TIME_`),
  KEY `ACT_FK_TASK_EXE` (`EXECUTION_ID_`),
  KEY `ACT_FK_TASK_PROCINST` (`PROC_INST_ID_`),
  KEY `ACT_FK_TASK_PROCDEF` (`PROC_DEF_ID_`),
  CONSTRAINT `ACT_FK_TASK_EXE` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `act_ru_execution` (`ID_`),
  CONSTRAINT `ACT_FK_TASK_PROCDEF` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `act_re_procdef` (`ID_`),
  CONSTRAINT `ACT_FK_TASK_PROCINST` FOREIGN KEY (`PROC_INST_ID_`) REFERENCES `act_ru_execution` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for act_ru_variable
-- ----------------------------
DROP TABLE IF EXISTS `act_ru_variable`;
CREATE TABLE `act_ru_variable` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin NOT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin NOT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `BYTEARRAY_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `DOUBLE_` double DEFAULT NULL,
  `LONG_` bigint(20) DEFAULT NULL,
  `TEXT_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TEXT2_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_VARIABLE_TASK_ID` (`TASK_ID_`),
  KEY `ACT_FK_VAR_EXE` (`EXECUTION_ID_`),
  KEY `ACT_FK_VAR_PROCINST` (`PROC_INST_ID_`),
  KEY `ACT_FK_VAR_BYTEARRAY` (`BYTEARRAY_ID_`),
  CONSTRAINT `ACT_FK_VAR_BYTEARRAY` FOREIGN KEY (`BYTEARRAY_ID_`) REFERENCES `act_ge_bytearray` (`ID_`),
  CONSTRAINT `ACT_FK_VAR_EXE` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `act_ru_execution` (`ID_`),
  CONSTRAINT `ACT_FK_VAR_PROCINST` FOREIGN KEY (`PROC_INST_ID_`) REFERENCES `act_ru_execution` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for area_attachment
-- ----------------------------
DROP TABLE IF EXISTS `area_attachment`;
CREATE TABLE `area_attachment` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL,
  `area_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '区域',
  `type` varchar(10) COLLATE utf8_bin NOT NULL COMMENT '业务办理类型',
  `attachList` varchar(500) COLLATE utf8_bin NOT NULL COMMENT '附件类型列表',
  `create_by` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `del_flag` char(1) COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='区域对应办事附件';

-- ----------------------------
-- Table structure for cms_article
-- ----------------------------
DROP TABLE IF EXISTS `cms_article`;
CREATE TABLE `cms_article` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '编号',
  `category_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '栏目编号',
  `title` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '标题',
  `link` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '文章链接',
  `color` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '标题颜色',
  `image` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '文章图片',
  `keywords` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '关键字',
  `description` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '描述、摘要',
  `weight` int(11) DEFAULT '0' COMMENT '权重，越大越靠前',
  `weight_date` datetime DEFAULT NULL COMMENT '权重期限',
  `hits` int(11) DEFAULT '0' COMMENT '点击数',
  `posid` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '推荐位，多选',
  `custom_content_view` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '自定义内容视图',
  `view_config` text COLLATE utf8_bin COMMENT '视图配置',
  `create_by` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `cms_article_create_by` (`create_by`),
  KEY `cms_article_title` (`title`),
  KEY `cms_article_keywords` (`keywords`),
  KEY `cms_article_del_flag` (`del_flag`),
  KEY `cms_article_weight` (`weight`),
  KEY `cms_article_update_date` (`update_date`),
  KEY `cms_article_category_id` (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='文章表';

-- ----------------------------
-- Table structure for cms_article_data
-- ----------------------------
DROP TABLE IF EXISTS `cms_article_data`;
CREATE TABLE `cms_article_data` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '编号',
  `content` text COLLATE utf8_bin COMMENT '文章内容',
  `copyfrom` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '文章来源',
  `relation` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '相关文章',
  `allow_comment` char(1) COLLATE utf8_bin DEFAULT NULL COMMENT '是否允许评论',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='文章详表';

-- ----------------------------
-- Table structure for cms_category
-- ----------------------------
DROP TABLE IF EXISTS `cms_category`;
CREATE TABLE `cms_category` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '编号',
  `parent_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '父级编号',
  `parent_ids` varchar(2000) COLLATE utf8_bin NOT NULL COMMENT '所有父级编号',
  `site_id` varchar(64) COLLATE utf8_bin DEFAULT '1' COMMENT '站点编号',
  `office_id` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '归属机构',
  `module` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '栏目模块',
  `name` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '栏目名称',
  `image` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '栏目图片',
  `href` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '链接',
  `target` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '目标',
  `description` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '描述',
  `keywords` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '关键字',
  `sort` int(11) DEFAULT '30' COMMENT '排序（升序）',
  `in_menu` char(1) COLLATE utf8_bin DEFAULT '1' COMMENT '是否在导航中显示',
  `in_list` char(1) COLLATE utf8_bin DEFAULT '1' COMMENT '是否在分类页中显示列表',
  `show_modes` char(1) COLLATE utf8_bin DEFAULT '0' COMMENT '展现方式',
  `allow_comment` char(1) COLLATE utf8_bin DEFAULT NULL COMMENT '是否允许评论',
  `is_audit` char(1) COLLATE utf8_bin DEFAULT NULL COMMENT '是否需要审核',
  `custom_list_view` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '自定义列表视图',
  `custom_content_view` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '自定义内容视图',
  `view_config` text COLLATE utf8_bin COMMENT '视图配置',
  `create_by` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `cms_category_parent_id` (`parent_id`),
  KEY `cms_category_module` (`module`),
  KEY `cms_category_name` (`name`),
  KEY `cms_category_sort` (`sort`),
  KEY `cms_category_del_flag` (`del_flag`),
  KEY `cms_category_office_id` (`office_id`),
  KEY `cms_category_site_id` (`site_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='栏目表';

-- ----------------------------
-- Table structure for cms_comment
-- ----------------------------
DROP TABLE IF EXISTS `cms_comment`;
CREATE TABLE `cms_comment` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '编号',
  `category_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '栏目编号',
  `content_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '栏目内容的编号',
  `title` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '栏目内容的标题',
  `content` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '评论内容',
  `name` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '评论姓名',
  `ip` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '评论IP',
  `create_date` datetime NOT NULL COMMENT '评论时间',
  `audit_user_id` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '审核人',
  `audit_date` datetime DEFAULT NULL COMMENT '审核时间',
  `del_flag` char(1) COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `cms_comment_category_id` (`category_id`),
  KEY `cms_comment_content_id` (`content_id`),
  KEY `cms_comment_status` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='评论表';

-- ----------------------------
-- Table structure for cms_guestbook
-- ----------------------------
DROP TABLE IF EXISTS `cms_guestbook`;
CREATE TABLE `cms_guestbook` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '编号',
  `type` char(1) COLLATE utf8_bin NOT NULL COMMENT '留言分类',
  `content` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '留言内容',
  `name` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '姓名',
  `email` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '邮箱',
  `phone` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '电话',
  `workunit` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '单位',
  `ip` varchar(100) COLLATE utf8_bin NOT NULL COMMENT 'IP',
  `create_date` datetime NOT NULL COMMENT '留言时间',
  `re_user_id` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '回复人',
  `re_date` datetime DEFAULT NULL COMMENT '回复时间',
  `re_content` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '回复内容',
  `del_flag` char(1) COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `cms_guestbook_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='留言板';

-- ----------------------------
-- Table structure for cms_link
-- ----------------------------
DROP TABLE IF EXISTS `cms_link`;
CREATE TABLE `cms_link` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '编号',
  `category_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '栏目编号',
  `title` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '链接名称',
  `color` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '标题颜色',
  `image` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '链接图片',
  `href` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '链接地址',
  `weight` int(11) DEFAULT '0' COMMENT '权重，越大越靠前',
  `weight_date` datetime DEFAULT NULL COMMENT '权重期限',
  `create_by` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `cms_link_category_id` (`category_id`),
  KEY `cms_link_title` (`title`),
  KEY `cms_link_del_flag` (`del_flag`),
  KEY `cms_link_weight` (`weight`),
  KEY `cms_link_create_by` (`create_by`),
  KEY `cms_link_update_date` (`update_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='友情链接';

-- ----------------------------
-- Table structure for cms_site
-- ----------------------------
DROP TABLE IF EXISTS `cms_site`;
CREATE TABLE `cms_site` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '编号',
  `name` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '站点名称',
  `title` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '站点标题',
  `logo` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '站点Logo',
  `domain` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '站点域名',
  `description` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '描述',
  `keywords` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '关键字',
  `theme` varchar(255) COLLATE utf8_bin DEFAULT 'default' COMMENT '主题',
  `copyright` text COLLATE utf8_bin COMMENT '版权信息',
  `custom_index_view` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '自定义站点首页视图',
  `create_by` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `cms_site_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='站点表';

-- ----------------------------
-- Table structure for gen_scheme
-- ----------------------------
DROP TABLE IF EXISTS `gen_scheme`;
CREATE TABLE `gen_scheme` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '编号',
  `name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '名称',
  `category` varchar(2000) COLLATE utf8_bin DEFAULT NULL COMMENT '分类',
  `package_name` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '生成包路径',
  `module_name` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '生成模块名',
  `sub_module_name` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '生成子模块名',
  `function_name` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '生成功能名',
  `function_name_simple` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '生成功能名（简写）',
  `function_author` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '生成功能作者',
  `gen_table_id` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '生成表编号',
  `create_by` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '删除标记（0：正常；1：删除）',
  PRIMARY KEY (`id`),
  KEY `gen_scheme_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='生成方案';

-- ----------------------------
-- Table structure for gen_table
-- ----------------------------
DROP TABLE IF EXISTS `gen_table`;
CREATE TABLE `gen_table` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '编号',
  `name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '名称',
  `comments` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '描述',
  `class_name` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '实体类名称',
  `parent_table` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '关联父表',
  `parent_table_fk` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '关联父表外键',
  `create_by` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '删除标记（0：正常；1：删除）',
  PRIMARY KEY (`id`),
  KEY `gen_table_name` (`name`),
  KEY `gen_table_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='业务表';

-- ----------------------------
-- Table structure for gen_table_column
-- ----------------------------
DROP TABLE IF EXISTS `gen_table_column`;
CREATE TABLE `gen_table_column` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '编号',
  `gen_table_id` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '归属表编号',
  `name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '名称',
  `comments` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '描述',
  `jdbc_type` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '列的数据类型的字节长度',
  `java_type` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT 'JAVA类型',
  `java_field` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT 'JAVA字段名',
  `is_pk` char(1) COLLATE utf8_bin DEFAULT NULL COMMENT '是否主键',
  `is_null` char(1) COLLATE utf8_bin DEFAULT NULL COMMENT '是否可为空',
  `is_insert` char(1) COLLATE utf8_bin DEFAULT NULL COMMENT '是否为插入字段',
  `is_edit` char(1) COLLATE utf8_bin DEFAULT NULL COMMENT '是否编辑字段',
  `is_list` char(1) COLLATE utf8_bin DEFAULT NULL COMMENT '是否列表字段',
  `is_query` char(1) COLLATE utf8_bin DEFAULT NULL COMMENT '是否查询字段',
  `query_type` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '查询方式（等于、不等于、大于、小于、范围、左LIKE、右LIKE、左右LIKE）',
  `show_type` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '字段生成方案（文本框、文本域、下拉框、复选框、单选框、字典选择、人员选择、部门选择、区域选择）',
  `dict_type` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '字典类型',
  `settings` varchar(2000) COLLATE utf8_bin DEFAULT NULL COMMENT '其它设置（扩展字段JSON）',
  `sort` decimal(10,0) DEFAULT NULL COMMENT '排序（升序）',
  `create_by` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '删除标记（0：正常；1：删除）',
  PRIMARY KEY (`id`),
  KEY `gen_table_column_table_id` (`gen_table_id`),
  KEY `gen_table_column_name` (`name`),
  KEY `gen_table_column_sort` (`sort`),
  KEY `gen_table_column_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='业务表字段';

-- ----------------------------
-- Table structure for gen_template
-- ----------------------------
DROP TABLE IF EXISTS `gen_template`;
CREATE TABLE `gen_template` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '编号',
  `name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '名称',
  `category` varchar(2000) COLLATE utf8_bin DEFAULT NULL COMMENT '分类',
  `file_path` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '生成文件路径',
  `file_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '生成文件名',
  `content` text COLLATE utf8_bin COMMENT '内容',
  `create_by` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '删除标记（0：正常；1：删除）',
  PRIMARY KEY (`id`),
  KEY `gen_template_del_falg` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='代码模板表';

-- ----------------------------
-- Table structure for sys_area
-- ----------------------------
DROP TABLE IF EXISTS `sys_area`;
CREATE TABLE `sys_area` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '编号',
  `parent_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '父级编号',
  `parent_ids` varchar(2000) COLLATE utf8_bin NOT NULL COMMENT '所有父级编号',
  `name` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '名称',
  `sort` decimal(10,0) NOT NULL COMMENT '排序',
  `code` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '区域编码',
  `type` char(1) COLLATE utf8_bin DEFAULT NULL COMMENT '区域类型',
  `create_by` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '创建者',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '更新者',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `sys_area_parent_id` (`parent_id`),
  KEY `sys_area_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='区域表';

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '编号',
  `value` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '数据值',
  `label` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '标签名',
  `type` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '类型',
  `description` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '描述',
  `sort` decimal(10,0) NOT NULL COMMENT '排序（升序）',
  `parent_id` varchar(64) COLLATE utf8_bin DEFAULT '0' COMMENT '父级编号',
  `create_by` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '创建者',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '更新者',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `sys_dict_value` (`value`),
  KEY `sys_dict_label` (`label`),
  KEY `sys_dict_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='字典表';

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '编号',
  `type` char(1) COLLATE utf8_bin DEFAULT '1' COMMENT '日志类型',
  `title` varchar(255) COLLATE utf8_bin DEFAULT '' COMMENT '日志标题',
  `create_by` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `remote_addr` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '操作IP地址',
  `user_agent` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '用户代理',
  `request_uri` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '请求URI',
  `method` varchar(5) COLLATE utf8_bin DEFAULT NULL COMMENT '操作方式',
  `params` text COLLATE utf8_bin COMMENT '操作提交的数据',
  `exception` text COLLATE utf8_bin COMMENT '异常信息',
  PRIMARY KEY (`id`),
  KEY `sys_log_create_by` (`create_by`),
  KEY `sys_log_request_uri` (`request_uri`),
  KEY `sys_log_type` (`type`),
  KEY `sys_log_create_date` (`create_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='日志表';

-- ----------------------------
-- Table structure for sys_mdict
-- ----------------------------
DROP TABLE IF EXISTS `sys_mdict`;
CREATE TABLE `sys_mdict` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '编号',
  `parent_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '父级编号',
  `parent_ids` varchar(2000) COLLATE utf8_bin NOT NULL COMMENT '所有父级编号',
  `name` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '名称',
  `sort` decimal(10,0) NOT NULL COMMENT '排序',
  `description` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '描述',
  `create_by` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '创建者',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '更新者',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `sys_mdict_parent_id` (`parent_id`),
  KEY `sys_mdict_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='多级字典表';

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '编号',
  `parent_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '父级编号',
  `parent_ids` varchar(2000) COLLATE utf8_bin NOT NULL COMMENT '所有父级编号',
  `name` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '名称',
  `sort` decimal(10,0) NOT NULL COMMENT '排序',
  `href` varchar(2000) COLLATE utf8_bin DEFAULT NULL COMMENT '链接',
  `target` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '目标',
  `icon` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '图标',
  `is_show` char(1) COLLATE utf8_bin NOT NULL COMMENT '是否在菜单中显示',
  `permission` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '权限标识',
  `create_by` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '创建者',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '更新者',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '删除标记',
  `role_types` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '所属用户类型组',
  PRIMARY KEY (`id`),
  KEY `sys_menu_parent_id` (`parent_id`),
  KEY `sys_menu_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='菜单表';

-- ----------------------------
-- Table structure for sys_office
-- ----------------------------
DROP TABLE IF EXISTS `sys_office`;
CREATE TABLE `sys_office` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '编号',
  `parent_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '父级编号',
  `parent_ids` varchar(2000) COLLATE utf8_bin NOT NULL COMMENT '所有父级编号',
  `name` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '名称',
  `sort` decimal(10,0) NOT NULL COMMENT '排序',
  `area_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '归属区域',
  `code` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '区域编码',
  `type` char(1) COLLATE utf8_bin NOT NULL COMMENT '机构类型',
  `grade` char(1) COLLATE utf8_bin NOT NULL COMMENT '机构等级',
  `address` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '联系地址',
  `zip_code` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '邮政编码',
  `master` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '负责人',
  `phone` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '电话',
  `fax` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '传真',
  `email` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '邮箱',
  `USEABLE` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '是否启用',
  `PRIMARY_PERSON` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '主负责人',
  `DEPUTY_PERSON` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '副负责人',
  `create_by` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '创建者',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '更新者',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `sys_office_parent_id` (`parent_id`),
  KEY `sys_office_del_flag` (`del_flag`),
  KEY `sys_office_type` (`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='机构表';

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '编号',
  `office_id` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '归属机构',
  `name` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '角色名称',
  `enname` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '英文名称',
  `role_type` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '角色类型',
  `data_scope` char(1) COLLATE utf8_bin DEFAULT NULL COMMENT '数据范围',
  `is_sys` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '是否系统数据',
  `useable` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '是否可用',
  `dept_type` char(2) COLLATE utf8_bin DEFAULT NULL,
  `create_by` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '创建者',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '更新者',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `sys_role_del_flag` (`del_flag`),
  KEY `sys_role_enname` (`enname`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='角色表';

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `role_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '角色编号',
  `menu_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '菜单编号',
  PRIMARY KEY (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='角色-菜单';

-- ----------------------------
-- Table structure for sys_role_office
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_office`;
CREATE TABLE `sys_role_office` (
  `role_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '角色编号',
  `office_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '机构编号',
  PRIMARY KEY (`role_id`,`office_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='角色-机构';

-- ----------------------------
-- Table structure for sys_user_1
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_1`;
CREATE TABLE `sys_user_1` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '编号',
  `company_id` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '归属公司',
  `office_id` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '归属部门',
  `login_name` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '登录名',
  `password` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '密码',
  `no` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '工号',
  `name` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '姓名',
  `email` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '电话',
  `mobile` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '手机',
  `user_type` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '用户类型',
  `photo` varchar(1000) COLLATE utf8_bin DEFAULT NULL COMMENT '用户头像',
  `login_ip` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '最后登陆IP',
  `login_date` datetime DEFAULT NULL COMMENT '最后登陆时间',
  `login_flag` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '是否可登录',
  `create_by` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) COLLATE utf8_bin DEFAULT '0' COMMENT '删除标记',
  `userTypeId` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `is_sysrole` char(1) COLLATE utf8_bin DEFAULT NULL,
  `cert_modulus` text COLLATE utf8_bin,
  `cert_file_path` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `sys_user_office_id` (`office_id`),
  KEY `sys_user_login_name` (`login_name`),
  KEY `sys_user_company_id` (`company_id`),
  KEY `sys_user_update_date` (`update_date`),
  KEY `sys_user_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户表(系统管理员)';

-- ----------------------------
-- Table structure for sys_user_2
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_2`;
CREATE TABLE `sys_user_2` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '编号',
  `company_id` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '归属公司',
  `office_id` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '归属部门',
  `login_name` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '登录名',
  `password` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '密码',
  `no` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '工号',
  `name` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '姓名',
  `email` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '电话',
  `mobile` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '手机',
  `user_type` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '用户类型',
  `photo` varchar(1000) COLLATE utf8_bin DEFAULT NULL COMMENT '用户头像',
  `login_ip` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '最后登陆IP',
  `login_date` datetime DEFAULT NULL COMMENT '最后登陆时间',
  `login_flag` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '是否可登录',
  `create_by` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) COLLATE utf8_bin DEFAULT '0' COMMENT '删除标记',
  `userTypeId` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `is_sysrole` char(1) COLLATE utf8_bin DEFAULT NULL,
  `cert_modulus` text COLLATE utf8_bin,
  `cert_file_path` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `sys_user_office_id` (`office_id`),
  KEY `sys_user_login_name` (`login_name`),
  KEY `sys_user_company_id` (`company_id`),
  KEY `sys_user_update_date` (`update_date`),
  KEY `sys_user_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户表(经销商)';

-- ----------------------------
-- Table structure for sys_user_3
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_3`;
CREATE TABLE `sys_user_3` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '编号',
  `company_id` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '归属公司',
  `office_id` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '归属部门',
  `login_name` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '登录名',
  `password` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '密码',
  `no` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '工号',
  `name` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '姓名',
  `email` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '电话',
  `mobile` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '手机',
  `user_type` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '用户类型',
  `photo` varchar(1000) COLLATE utf8_bin DEFAULT NULL COMMENT '用户头像',
  `login_ip` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '最后登陆IP',
  `login_date` datetime DEFAULT NULL COMMENT '最后登陆时间',
  `login_flag` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '是否可登录',
  `create_by` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) COLLATE utf8_bin DEFAULT '0' COMMENT '删除标记',
  `userTypeId` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `is_sysrole` char(1) COLLATE utf8_bin DEFAULT NULL,
  `cert_modulus` text COLLATE utf8_bin,
  `cert_file_path` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `sys_user_office_id` (`office_id`),
  KEY `sys_user_login_name` (`login_name`),
  KEY `sys_user_company_id` (`company_id`),
  KEY `sys_user_update_date` (`update_date`),
  KEY `sys_user_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户表(刻章点)';

-- ----------------------------
-- Table structure for sys_user_4
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_4`;
CREATE TABLE `sys_user_4` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '编号',
  `company_id` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '归属公司',
  `office_id` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '归属部门',
  `login_name` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '登录名',
  `password` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '密码',
  `no` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '工号',
  `name` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '姓名',
  `email` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '电话',
  `mobile` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '手机',
  `user_type` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '用户类型',
  `photo` varchar(1000) COLLATE utf8_bin DEFAULT NULL COMMENT '用户头像',
  `login_ip` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '最后登陆IP',
  `login_date` datetime DEFAULT NULL COMMENT '最后登陆时间',
  `login_flag` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '是否可登录',
  `create_by` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) COLLATE utf8_bin DEFAULT '0' COMMENT '删除标记',
  `userTypeId` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `is_sysrole` char(1) COLLATE utf8_bin DEFAULT NULL,
  `cert_modulus` text COLLATE utf8_bin,
  `cert_file_path` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `sys_user_office_id` (`office_id`),
  KEY `sys_user_login_name` (`login_name`),
  KEY `sys_user_company_id` (`company_id`),
  KEY `sys_user_update_date` (`update_date`),
  KEY `sys_user_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户表(用章单位)';

-- ----------------------------
-- Table structure for sys_user_5
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_5`;
CREATE TABLE `sys_user_5` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '编号',
  `company_id` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '归属公司',
  `office_id` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '归属部门',
  `login_name` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '登录名',
  `password` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '密码',
  `no` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '工号',
  `name` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '姓名',
  `email` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '电话',
  `mobile` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '手机',
  `user_type` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '用户类型',
  `photo` varchar(1000) COLLATE utf8_bin DEFAULT NULL COMMENT '用户头像',
  `login_ip` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '最后登陆IP',
  `login_date` datetime DEFAULT NULL COMMENT '最后登陆时间',
  `login_flag` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '是否可登录',
  `create_by` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) COLLATE utf8_bin DEFAULT '0' COMMENT '删除标记',
  `userTypeId` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `is_sysrole` char(1) COLLATE utf8_bin DEFAULT NULL,
  `cert_modulus` text COLLATE utf8_bin,
  `cert_file_path` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `sys_user_office_id` (`office_id`),
  KEY `sys_user_login_name` (`login_name`),
  KEY `sys_user_company_id` (`company_id`),
  KEY `sys_user_update_date` (`update_date`),
  KEY `sys_user_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户表(公安机关)';

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `user_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '用户编号',
  `role_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '角色编号',
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户-角色';

-- ----------------------------
-- Table structure for test_data
-- ----------------------------
DROP TABLE IF EXISTS `test_data`;
CREATE TABLE `test_data` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '编号',
  `user_id` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '归属用户',
  `office_id` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '归属部门',
  `area_id` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '归属区域',
  `name` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '名称',
  `sex` char(1) COLLATE utf8_bin DEFAULT NULL COMMENT '性别',
  `in_date` date DEFAULT NULL COMMENT '加入日期',
  `create_by` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '创建者',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '更新者',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `test_data_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='业务数据表';

-- ----------------------------
-- Table structure for test_data_child
-- ----------------------------
DROP TABLE IF EXISTS `test_data_child`;
CREATE TABLE `test_data_child` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '编号',
  `test_data_main_id` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '业务主表ID',
  `name` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '名称',
  `create_by` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '创建者',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '更新者',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `test_data_child_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='业务数据子表';

-- ----------------------------
-- Table structure for test_data_main
-- ----------------------------
DROP TABLE IF EXISTS `test_data_main`;
CREATE TABLE `test_data_main` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '编号',
  `user_id` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '归属用户',
  `office_id` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '归属部门',
  `area_id` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '归属区域',
  `name` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '名称',
  `sex` char(1) COLLATE utf8_bin DEFAULT NULL COMMENT '性别',
  `in_date` date DEFAULT NULL COMMENT '加入日期',
  `create_by` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '创建者',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '更新者',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `test_data_main_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='业务数据表';

-- ----------------------------
-- Table structure for test_tree
-- ----------------------------
DROP TABLE IF EXISTS `test_tree`;
CREATE TABLE `test_tree` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '编号',
  `parent_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '父级编号',
  `parent_ids` varchar(2000) COLLATE utf8_bin NOT NULL COMMENT '所有父级编号',
  `name` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '名称',
  `sort` decimal(10,0) NOT NULL COMMENT '排序',
  `create_by` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '创建者',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '更新者',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `test_tree_del_flag` (`del_flag`),
  KEY `test_data_parent_id` (`parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='树结构表';

-- ----------------------------
-- Table structure for t_attachment
-- ----------------------------
DROP TABLE IF EXISTS `t_attachment`;
CREATE TABLE `t_attachment` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL,
  `attach_Path` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '附件路径',
  `attach_type` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '附件类型',
  `attach_suffix` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='附件表';

-- ----------------------------
-- Table structure for t_company_1
-- ----------------------------
DROP TABLE IF EXISTS `t_company_1`;
CREATE TABLE `t_company_1` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL,
  `comp_type` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '单位系统类型',
  `area_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '所属区域',
  `sole_code` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '单位唯一编码',
  `company_Name` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '单位名称',
  `type1` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '单位一级类别',
  `type2` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '单位二级类别',
  `type3` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '单位三级类别',
  `legal_Name` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '法定代表人',
  `legal_Phone` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '法人手机',
  `legal_certType` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '法人证件类型',
  `legal_certCode` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '法人证件号码',
  `police_Name` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '治安负责人',
  `police_IdCode` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '治安负责人身份证号码',
  `police_Phone` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '治安负责人联系电话',
  `bus_model` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '经营方式',
  `bus_type` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '经济性质',
  `head_count` int(11) DEFAULT NULL COMMENT '单位总人数',
  `special_count` int(11) DEFAULT NULL COMMENT '单位特业从业人员',
  `comp_address` varchar(300) COLLATE utf8_bin DEFAULT NULL COMMENT '单位地址',
  `comp_phone` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '单位电话',
  `postCode` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '邮编',
  `bus_licNum` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '企业营业执照号',
  `bus_tagNum` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '企业税务登记证号',
  `comp_state` varchar(10) COLLATE utf8_bin NOT NULL COMMENT '企业状态',
  `comp_creatDate` datetime DEFAULT NULL COMMENT '成立日期',
  `bus_startDate` datetime DEFAULT NULL COMMENT '营业期限起始时间',
  `bus_endDate` datetime DEFAULT NULL COMMENT '营业期限截止时间',
  `licence_state` char(10) COLLATE utf8_bin DEFAULT 'NULL',
  `record_unit` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '登记机关',
  `bus_scope` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '经营范围',
  `reg_cap` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '注册资本',
  `sysOpr_state` varchar(10) COLLATE utf8_bin DEFAULT 'OPEN' COMMENT '润城科技管理员操作状态',
  `create_by` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `del_flag` char(1) COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '删除标记',
  `last_licence_state` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '上次许可证申请类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='公司/单位（刻章）';

-- ----------------------------
-- Table structure for t_company_2
-- ----------------------------
DROP TABLE IF EXISTS `t_company_2`;
CREATE TABLE `t_company_2` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL,
  `comp_type` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '单位系统类型',
  `area_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '所属区域',
  `sole_code` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '单位唯一编码',
  `company_Name` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '单位名称',
  `type1` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '单位一级类别',
  `type2` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '单位二级类别',
  `type3` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '单位三级类别',
  `legal_Name` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '法定代表人',
  `legal_Phone` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '法人手机',
  `legal_certType` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '法人证件类型',
  `legal_certCode` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '法人证件号码',
  `police_Name` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '治安负责人',
  `police_IdCode` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '治安负责人身份证号码',
  `police_Phone` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '治安负责人联系电话',
  `bus_model` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '经营方式',
  `bus_type` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '经济性质',
  `head_count` int(11) DEFAULT NULL COMMENT '单位总人数',
  `special_count` int(11) DEFAULT NULL COMMENT '单位特业从业人员',
  `comp_address` varchar(300) COLLATE utf8_bin NOT NULL COMMENT '单位地址',
  `comp_phone` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '单位电话',
  `postCode` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '邮编',
  `bus_licNum` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '企业营业执照号',
  `bus_tagNum` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '企业税务登记证号',
  `comp_state` varchar(10) COLLATE utf8_bin NOT NULL COMMENT '企业状态',
  `comp_creatDate` datetime DEFAULT NULL COMMENT '成立日期',
  `bus_startDate` datetime DEFAULT NULL COMMENT '营业期限起始时间',
  `bus_endDate` datetime DEFAULT NULL COMMENT '营业期限截止时间',
  `licence_state` char(10) COLLATE utf8_bin DEFAULT 'NULL',
  `record_unit` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '登记机关',
  `bus_scope` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '经营范围',
  `reg_cap` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '注册资本',
  `sysOpr_state` varchar(10) COLLATE utf8_bin DEFAULT 'OPEN' COMMENT '润城科技管理员操作状态',
  `create_by` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `del_flag` char(1) COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '删除标记',
  `last_licence_state` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '上次许可证申请类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='公司/单位（用章）';

-- ----------------------------
-- Table structure for t_company_3
-- ----------------------------
DROP TABLE IF EXISTS `t_company_3`;
CREATE TABLE `t_company_3` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL,
  `comp_type` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '单位系统类型',
  `area_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '所属区域',
  `sole_code` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '单位唯一编码',
  `company_Name` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '单位名称',
  `type1` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '单位一级类别',
  `type2` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '单位二级类别',
  `type3` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '单位三级类别',
  `legal_Name` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '法定代表人',
  `legal_Phone` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '法人手机',
  `legal_certType` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '法人证件类型',
  `legal_certCode` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '法人证件号码',
  `police_Name` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '治安负责人',
  `police_IdCode` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '治安负责人身份证号码',
  `police_Phone` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '治安负责人联系电话',
  `bus_model` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '经营方式',
  `bus_type` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '经济性质',
  `head_count` int(11) DEFAULT NULL COMMENT '单位总人数',
  `special_count` int(11) DEFAULT NULL COMMENT '单位特业从业人员',
  `comp_address` varchar(300) COLLATE utf8_bin NOT NULL COMMENT '单位地址',
  `comp_phone` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '单位电话',
  `postCode` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '邮编',
  `bus_licNum` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '企业营业执照号',
  `bus_tagNum` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '企业税务登记证号',
  `comp_state` varchar(10) COLLATE utf8_bin NOT NULL COMMENT '企业状态',
  `comp_creatDate` datetime DEFAULT NULL COMMENT '成立日期',
  `bus_startDate` datetime DEFAULT NULL COMMENT '营业期限起始时间',
  `bus_endDate` datetime DEFAULT NULL COMMENT '营业期限截止时间',
  `licence_state` char(10) COLLATE utf8_bin DEFAULT 'NULL',
  `record_unit` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '登记机关',
  `bus_scope` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '经营范围',
  `reg_cap` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '注册资本',
  `sysOpr_state` varchar(10) COLLATE utf8_bin DEFAULT 'OPEN' COMMENT '润城科技管理员操作状态',
  `create_by` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `del_flag` char(1) COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '删除标记',
  `last_licence_state` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '上次许可证申请类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='公司/单位（经销商）';

-- ----------------------------
-- Table structure for t_count_set
-- ----------------------------
DROP TABLE IF EXISTS `t_count_set`;
CREATE TABLE `t_count_set` (
  `id` varchar(64) NOT NULL,
  `company_id` varchar(64) DEFAULT NULL COMMENT '被设定公司id',
  `area_id` varchar(64) DEFAULT NULL COMMENT '对应区域',
  `stamp_shape` varchar(10) DEFAULT NULL COMMENT '印章形式',
  `create_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `update_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `count` int(11) DEFAULT NULL COMMENT '数量',
  `create_by` varchar(64) DEFAULT NULL,
  `update_by` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数量控制';

-- ----------------------------
-- Table structure for t_document
-- ----------------------------
DROP TABLE IF EXISTS `t_document`;
CREATE TABLE `t_document` (
  `id` varchar(64) NOT NULL,
  `file_name` text,
  `file_undone_path` text,
  `file_undone_md5` text,
  `file_done_path` text,
  `file_done_md5` text,
  `stamp_id` varchar(64) DEFAULT NULL,
  `singal_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `user_id` varchar(64) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `remarks` text,
  `status` char(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_electron
-- ----------------------------
DROP TABLE IF EXISTS `t_electron`;
CREATE TABLE `t_electron` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL,
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `validDateStart` datetime NOT NULL COMMENT '有效开始日期',
  `validDateEnd` datetime NOT NULL COMMENT '有效结束日期',
  `stamp_type` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '印章类型',
  `stamp_version` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '印章格式版本',
  `stamp_vendorId` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '厂商名称',
  `seal_path` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '电子印模文件路径',
  `create_by` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `stamp_name` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `seal_model` varchar(500) COLLATE utf8_bin DEFAULT NULL,
  `allow_use` int(11) DEFAULT NULL,
  `rc_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='电子印章信息';

-- ----------------------------
-- Table structure for t_licence_1
-- ----------------------------
DROP TABLE IF EXISTS `t_licence_1`;
CREATE TABLE `t_licence_1` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL,
  `make_comp` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '刻章公司',
  `work_type` varchar(10) COLLATE utf8_bin NOT NULL COMMENT '办理业务类型',
  `work_reason` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '办理业务理由',
  `comp_name` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '单位名称',
  `bus_type` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '经济性质',
  `legal_Name` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '法定代表人',
  `legal_Phone` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '法人手机',
  `legal_certType` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '法人证件类型',
  `legal_certCode` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '法人证件号码',
  `police_Name` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '治安负责人',
  `police_IdCode` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '治安负责人身份证号码',
  `police_Phone` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '治安负责人联系电话',
  `head_count` int(11) DEFAULT NULL COMMENT '单位总人数',
  `special_count` int(11) DEFAULT NULL COMMENT '单位特业从业人员',
  `agent_name` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '经办人姓名',
  `agent_certCode` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '经办人身份证号码',
  `agent_phone` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '经办人电话号码',
  `attachs` text COLLATE utf8_bin COMMENT '附件信息列表',
  `bus_run` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '企业经营',
  `check_state` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '审核状态',
  `check_reason` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '审核原因',
  `create_by` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `del_flag` char(1) COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='许可证业务信息（开办申请）';

-- ----------------------------
-- Table structure for t_licence_2
-- ----------------------------
DROP TABLE IF EXISTS `t_licence_2`;
CREATE TABLE `t_licence_2` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL,
  `make_comp` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '刻章公司',
  `work_type` varchar(10) COLLATE utf8_bin NOT NULL COMMENT '办理业务类型',
  `work_reason` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '办理业务理由',
  `comp_name` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '单位名称',
  `bus_type` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '经济性质',
  `legal_Name` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '法定代表人',
  `legal_Phone` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '法人手机',
  `legal_certType` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '法人证件类型',
  `legal_certCode` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '法人证件号码',
  `police_Name` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '治安负责人',
  `police_IdCode` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '治安负责人身份证号码',
  `police_Phone` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '治安负责人联系电话',
  `head_count` int(11) DEFAULT NULL COMMENT '单位总人数',
  `special_count` int(11) DEFAULT NULL COMMENT '单位特业从业人员',
  `agent_name` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '经办人姓名',
  `agent_certCode` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '经办人身份证号码',
  `agent_phone` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '经办人电话号码',
  `attachs` text COLLATE utf8_bin COMMENT '附件信息列表',
  `bus_run` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '企业经营',
  `check_state` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '审核状态',
  `check_reason` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '审核原因',
  `create_by` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `del_flag` char(1) COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='许可证业务信息（年审）';

-- ----------------------------
-- Table structure for t_licence_3
-- ----------------------------
DROP TABLE IF EXISTS `t_licence_3`;
CREATE TABLE `t_licence_3` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL,
  `make_comp` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '刻章公司',
  `work_type` varchar(10) COLLATE utf8_bin NOT NULL COMMENT '办理业务类型',
  `work_reason` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '办理业务理由',
  `comp_name` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '单位名称',
  `bus_type` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '经济性质',
  `legal_Name` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '法定代表人',
  `legal_Phone` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '法人手机',
  `legal_certType` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '法人证件类型',
  `legal_certCode` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '法人证件号码',
  `police_Name` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '治安负责人',
  `police_IdCode` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '治安负责人身份证号码',
  `police_Phone` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '治安负责人联系电话',
  `head_count` int(11) DEFAULT NULL COMMENT '单位总人数',
  `special_count` int(11) DEFAULT NULL COMMENT '单位特业从业人员',
  `agent_name` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '经办人姓名',
  `agent_certCode` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '经办人身份证号码',
  `agent_phone` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '经办人电话号码',
  `attachs` text COLLATE utf8_bin COMMENT '附件信息列表',
  `bus_run` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '企业经营',
  `check_state` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '审核状态',
  `check_reason` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '审核原因',
  `create_by` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `del_flag` char(1) COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='许可证业务信息（变更）';

-- ----------------------------
-- Table structure for t_licence_4
-- ----------------------------
DROP TABLE IF EXISTS `t_licence_4`;
CREATE TABLE `t_licence_4` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL,
  `make_comp` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '刻章公司',
  `work_type` varchar(10) COLLATE utf8_bin NOT NULL COMMENT '办理业务类型',
  `work_reason` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '办理业务理由',
  `comp_name` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '单位名称',
  `bus_type` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '经济性质',
  `legal_Name` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '法定代表人',
  `legal_Phone` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '法人手机',
  `legal_certType` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '法人证件类型',
  `legal_certCode` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '法人证件号码',
  `police_Name` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '治安负责人',
  `police_IdCode` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '治安负责人身份证号码',
  `police_Phone` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '治安负责人联系电话',
  `head_count` int(11) DEFAULT NULL COMMENT '单位总人数',
  `special_count` int(11) DEFAULT NULL COMMENT '单位特业从业人员',
  `agent_name` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '经办人姓名',
  `agent_certCode` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '经办人身份证号码',
  `agent_phone` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '经办人电话号码',
  `attachs` text COLLATE utf8_bin COMMENT '附件信息列表',
  `bus_run` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '企业经营',
  `check_state` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '审核状态',
  `check_reason` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '审核原因',
  `create_by` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `del_flag` char(1) COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='许可证业务信息（注销）';

-- ----------------------------
-- Table structure for t_login_log
-- ----------------------------
DROP TABLE IF EXISTS `t_login_log`;
CREATE TABLE `t_login_log` (
  `id` varchar(255) NOT NULL,
  `login_name` varchar(255) DEFAULT NULL,
  `ip` varchar(255) DEFAULT NULL,
  `login_date` datetime DEFAULT NULL,
  `login_status` char(255) DEFAULT NULL,
  `reason` varchar(255) DEFAULT NULL,
  `user_type` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT '' COMMENT '登入或登出',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_moneysetting
-- ----------------------------
DROP TABLE IF EXISTS `t_moneysetting`;
CREATE TABLE `t_moneysetting` (
  `id` varchar(64) NOT NULL,
  `company_id` varchar(64) DEFAULT NULL COMMENT '收费公司',
  `money` int(11) DEFAULT NULL COMMENT '金额',
  `stampTexture` varchar(10) DEFAULT NULL COMMENT '物理章材料',
  `area_id` varchar(64) DEFAULT NULL COMMENT '缴费区域',
  `payment_type` varchar(10) DEFAULT NULL COMMENT '收费类型',
  `create_Date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_User` varchar(64) DEFAULT NULL COMMENT '创建者',
  `update_Date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_User` varchar(64) DEFAULT NULL COMMENT '更新者',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='收费设定';

-- ----------------------------
-- Table structure for t_moneysetting_log
-- ----------------------------
DROP TABLE IF EXISTS `t_moneysetting_log`;
CREATE TABLE `t_moneysetting_log` (
  `id` varchar(64) NOT NULL,
  `u_money` int(11) DEFAULT NULL COMMENT 'U盾收费',
  `phy_money` int(11) DEFAULT NULL COMMENT '物理印章收费',
  `ele_moeny` int(11) DEFAULT NULL COMMENT '电子印章收费',
  `opr_type` varchar(10) DEFAULT NULL COMMENT '操作类型',
  `company_id` varchar(64) DEFAULT NULL COMMENT '公司id',
  `create_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_By` varchar(64) DEFAULT NULL COMMENT '创建者',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='收费设定日志';

-- ----------------------------
-- Table structure for t_moulage
-- ----------------------------
DROP TABLE IF EXISTS `t_moulage`;
CREATE TABLE `t_moulage` (
  `id` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `stamp_text` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '印章文本',
  `across_text` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '横排文字',
  `sub_text` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '下标文字',
  `security_code` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '防伪码',
  `inside_image` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '内框图',
  `ring_up` double DEFAULT NULL COMMENT '环形文本上下偏移',
  `ring_size` double DEFAULT NULL COMMENT '环形字号大小',
  `ring_width` double DEFAULT NULL COMMENT '环形文本宽度',
  `ring_angle` double DEFAULT NULL COMMENT '环形文本角度',
  `across_offset` double DEFAULT NULL COMMENT '横排文本偏移',
  `across_size` double DEFAULT NULL COMMENT '横排文本大小',
  `across_width` double DEFAULT NULL COMMENT '横排文本宽度',
  `code_size` double DEFAULT NULL COMMENT '防伪码字体大小',
  `code_width` double DEFAULT NULL COMMENT '防伪码字体宽度',
  `code_up` double DEFAULT NULL COMMENT '防伪码上下偏移',
  `code_angle` double DEFAULT NULL COMMENT '防伪码角度',
  `phy_model` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '物理印模存储路径',
  `ele_model` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '电子印模存储路径',
  `del_flag` char(1) COLLATE utf8_bin DEFAULT '0' COMMENT '删除标记',
  `stamp_type` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `use_company` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `make_company` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `moulage_name` varchar(255) COLLATE utf8_bin DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='印模信息';

-- ----------------------------
-- Table structure for t_police
-- ----------------------------
DROP TABLE IF EXISTS `t_police`;
CREATE TABLE `t_police` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL,
  `area_id` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '所属区域',
  `principal` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '负责人',
  `address` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '单位地址',
  `phone` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '电话',
  `postcode` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '邮政编码',
  `create_by` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `del_flag` char(1) COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '删除标记',
  `name` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='公安机关';

-- ----------------------------
-- Table structure for t_police_log
-- ----------------------------
DROP TABLE IF EXISTS `t_police_log`;
CREATE TABLE `t_police_log` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL,
  `type` char(1) COLLATE utf8_bin DEFAULT NULL,
  `operate_type` varchar(12) COLLATE utf8_bin DEFAULT NULL,
  `title` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `content` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `area` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `operator` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `del_flag` char(1) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for t_stampauth
-- ----------------------------
DROP TABLE IF EXISTS `t_stampauth`;
CREATE TABLE `t_stampauth` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `stamp_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '印章',
  `user_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '被授权用户',
  `start_time` datetime DEFAULT NULL COMMENT '起始时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `remarks` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '操作者',
  `create_date` datetime NOT NULL COMMENT '操作时间',
  `opr_state` varchar(10) COLLATE utf8_bin NOT NULL COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='印章授权信息';

-- ----------------------------
-- Table structure for t_stamplog
-- ----------------------------
DROP TABLE IF EXISTS `t_stamplog`;
CREATE TABLE `t_stamplog` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `title` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '日志标题',
  `create_by` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='印章操作日志';

-- ----------------------------
-- Table structure for t_stampopr_1
-- ----------------------------
DROP TABLE IF EXISTS `t_stampopr_1`;
CREATE TABLE `t_stampopr_1` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `stamp_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '印章id',
  `user_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '操作用户',
  `applyUsername` varchar(64)  COLLATE utf8_bin NOT NULL COMMENT '使用者名字',
  `opr_type` varchar(10) COLLATE utf8_bin NOT NULL COMMENT '操作类型',
  `use_date` date DEFAULT NULL COMMENT '使用日期',
  `remarks` varchar(5000) COLLATE utf8_bin DEFAULT NULL COMMENT '备注用途',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '创建者',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='物理印章操作历史';

-- ----------------------------
-- Table structure for t_stampopr_2
-- ----------------------------
DROP TABLE IF EXISTS `t_stampopr_2`;
CREATE TABLE `t_stampopr_2` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `stamp_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '印章id',
  `user_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '使用用户',
  `opr_type` varchar(10) COLLATE utf8_bin NOT NULL COMMENT '操作类型',
  `use_date` date DEFAULT NULL COMMENT '使用日期',
  `remarks` varchar(5000) COLLATE utf8_bin DEFAULT NULL COMMENT '备注用途',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '创建者',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='电子印章操作历史';

-- ----------------------------
-- Table structure for t_stamprecord_1
-- ----------------------------
DROP TABLE IF EXISTS `t_stamprecord_1`;
CREATE TABLE `t_stamprecord_1` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL,
  `serial_num` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '业务流水号',
  `use_comp` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '用章公司',
  `make_comp` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '刻章公司',
  `stamp_id` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '印章id',
  `work_type` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '办事类型',
  `work_remakrs` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '办事理由',
  `agent_name` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '经办人姓名',
  `agent_certType` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '经办人证件类型',
  `agent_certCode` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '经办人证件号码',
  `agent_phone` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '经办人电话号码',
  `apply_Infos` text COLLATE utf8_bin COMMENT '申请印章信息类别',
  `attachs` text COLLATE utf8_bin COMMENT '附件信息列表',
  `getStamp_name` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '取章人',
  `getStamp_idCode` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '取章人身份证',
  `finish_date` datetime DEFAULT NULL COMMENT '刻制完成时间',
  `finish_file` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '已盖章材料(文件虚拟地址)',
  `delivery_date` datetime DEFAULT NULL COMMENT '交付时间',
  `delivery_photo` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '交付现场照片(文件虚拟地址)',
  `permission_photo` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '印章操作许可证(文件虚拟地址)',
  `create_by` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `del_flag` char(1) COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '删除标记',
  `legalName` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `legalPhone` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `legalCertType` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `legalCertCode` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `soleCode` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `company_name` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `type1` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `comp_address` varchar(300) COLLATE utf8_bin DEFAULT NULL,
  `comp_phone` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='印章备案信息(申请)';

-- ----------------------------
-- Table structure for t_stamprecord_2
-- ----------------------------
DROP TABLE IF EXISTS `t_stamprecord_2`;
CREATE TABLE `t_stamprecord_2` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL,
  `serial_num` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '业务流水号',
  `use_comp` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '用章公司',
  `make_comp` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '刻章公司',
  `stamp_id` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '印章id',
  `work_type` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '办事类型',
  `work_remakrs` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '办事理由',
  `agent_name` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '经办人姓名',
  `agent_certType` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '经办人证件类型',
  `agent_certCode` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '经办人证件号码',
  `agent_phone` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '经办人电话号码',
  `apply_Infos` text COLLATE utf8_bin COMMENT '申请印章信息类别',
  `attachs` text COLLATE utf8_bin COMMENT '附件信息列表',
  `getStamp_name` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '取章人',
  `getStamp_idCode` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '取章人身份证',
  `finish_date` datetime DEFAULT NULL COMMENT '刻制完成时间',
  `finish_file` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '已盖章材料(文件虚拟地址)',
  `delivery_date` datetime DEFAULT NULL COMMENT '交付时间',
  `delivery_photo` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '交付现场照片(文件虚拟地址)',
  `permission_photo` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '印章操作许可证(文件虚拟地址)',
  `create_by` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `del_flag` char(1) COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '删除标记',
  `legalName` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `legalPhone` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `legalCertType` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `legalCertCode` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `soleCode` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `company_name` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `type1` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `comp_address` varchar(300) COLLATE utf8_bin DEFAULT NULL,
  `comp_phone` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='印章备案信息(缴销)';

-- ----------------------------
-- Table structure for t_stamprecord_3
-- ----------------------------
DROP TABLE IF EXISTS `t_stamprecord_3`;
CREATE TABLE `t_stamprecord_3` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL,
  `serial_num` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '业务流水号',
  `use_comp` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '用章公司',
  `make_comp` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '刻章公司',
  `stamp_id` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '印章id',
  `work_type` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '办事类型',
  `work_remakrs` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '办事理由',
  `agent_name` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '经办人姓名',
  `agent_certType` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '经办人证件类型',
  `agent_certCode` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '经办人证件号码',
  `agent_phone` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '经办人电话号码',
  `apply_Infos` text COLLATE utf8_bin COMMENT '申请印章信息类别',
  `attachs` text COLLATE utf8_bin COMMENT '附件信息列表',
  `getStamp_name` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '取章人',
  `getStamp_idCode` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '取章人身份证',
  `finish_date` datetime DEFAULT NULL COMMENT '刻制完成时间',
  `finish_file` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '已盖章材料(文件虚拟地址)',
  `delivery_date` datetime DEFAULT NULL COMMENT '交付时间',
  `delivery_photo` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '交付现场照片(文件虚拟地址)',
  `permission_photo` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '印章操作许可证(文件虚拟地址)',
  `create_by` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `del_flag` char(1) COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '删除标记',
  `legalName` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `legalPhone` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `legalCertCode` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `legalCertType` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `soleCode` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `company_name` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `type1` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `comp_address` varchar(300) COLLATE utf8_bin DEFAULT NULL,
  `comp_phone` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='印章备案信息(挂失)';

-- ----------------------------
-- Table structure for t_stamprecord_4
-- ----------------------------
DROP TABLE IF EXISTS `t_stamprecord_4`;
CREATE TABLE `t_stamprecord_4` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL,
  `serial_num` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '业务流水号',
  `use_comp` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '用章公司',
  `make_comp` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '刻章公司',
  `stamp_id` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '印章id',
  `work_type` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '办事类型',
  `work_remakrs` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '办事理由',
  `agent_name` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '经办人姓名',
  `agent_certType` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '经办人证件类型',
  `agent_certCode` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '经办人证件号码',
  `agent_phone` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '经办人电话号码',
  `apply_Infos` text COLLATE utf8_bin COMMENT '申请印章信息类别',
  `attachs` text COLLATE utf8_bin COMMENT '附件信息列表',
  `getStamp_name` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '取章人',
  `getStamp_idCode` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '取章人身份证',
  `finish_date` datetime DEFAULT NULL COMMENT '刻制完成时间',
  `finish_file` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '已盖章材料(文件虚拟地址)',
  `delivery_date` datetime DEFAULT NULL COMMENT '交付时间',
  `delivery_photo` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '交付现场照片(文件虚拟地址)',
  `permission_photo` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '印章操作许可证(文件虚拟地址)',
  `create_by` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `del_flag` char(1) COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '删除标记',
  `legalName` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `legalPhone` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `legalCertType` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `legalCertCode` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `soleCode` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `company_name` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `type1` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `comp_address` varchar(300) COLLATE utf8_bin DEFAULT NULL,
  `comp_phone` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='印章备案信息(补刻)';

-- ----------------------------
-- Table structure for t_stamprecord_5
-- ----------------------------
DROP TABLE IF EXISTS `t_stamprecord_5`;
CREATE TABLE `t_stamprecord_5` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL,
  `serial_num` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '业务流水号',
  `use_comp` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '用章公司',
  `make_comp` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '刻章公司',
  `stamp_id` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '印章id',
  `work_type` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '办事类型',
  `work_remakrs` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '办事理由',
  `agent_name` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '经办人姓名',
  `agent_certType` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '经办人证件类型',
  `agent_certCode` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '经办人证件号码',
  `agent_phone` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '经办人电话号码',
  `apply_Infos` text COLLATE utf8_bin COMMENT '申请印章信息类别',
  `attachs` text COLLATE utf8_bin COMMENT '附件信息列表',
  `getStamp_name` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '取章人',
  `getStamp_idCode` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '取章人身份证',
  `finish_date` datetime DEFAULT NULL COMMENT '刻制完成时间',
  `finish_file` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '已盖章材料(文件虚拟地址)',
  `delivery_date` datetime DEFAULT NULL COMMENT '交付时间',
  `delivery_photo` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '交付现场照片(文件虚拟地址)',
  `permission_photo` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '印章操作许可证(文件虚拟地址)',
  `create_by` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `del_flag` char(1) COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '删除标记',
  `legalName` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `legalPhone` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `legalCertCode` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `legalCertType` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `soleCode` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `company_name` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `type1` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `comp_address` varchar(300) COLLATE utf8_bin DEFAULT NULL,
  `comp_phone` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='印章备案信息(变更)';

-- ----------------------------
-- Table structure for t_stamp_1
-- ----------------------------
DROP TABLE IF EXISTS `t_stamp_1`;
CREATE TABLE `t_stamp_1` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL,
  `use_comp` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '用章单位',
  `make_comp` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '刻章点',
  `now_comp` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '现在所属刻章公司',
  `record_id` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '最后一次备案id',
  `stamp_name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '印章名称',
  `allow_useNum` int(11) DEFAULT NULL COMMENT '印章允许使用次数',
  `stamp_state` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '印章制作状态',
  `stamp_shape` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '印章形式',
  `use_state` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '印章使用状态',
  `sys_state` varchar(20) COLLATE utf8_bin DEFAULT '' COMMENT '印章系统管控状态',
  `record_state` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '印章备案状态',
  `lastState_by` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '印章状态最后修改人',
  `lastState_date` datetime DEFAULT NULL COMMENT '印章状态最后修改时间',
  `stamp_texture` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '印章材质',
  `stamp_type` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '印章类型',
  `stamp_subtype` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '印章子类型',
  `shape_id` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '印模信息ID',
  `record_date` datetime DEFAULT NULL COMMENT '备案日期',
  `make_date` datetime DEFAULT NULL COMMENT '制作日期',
  `delivery_date` datetime DEFAULT NULL COMMENT '交付日期',
  `create_by` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `livePhoto` text COLLATE utf8_bin,
  `recordPhoto` text COLLATE utf8_bin,
  `phy_model` varchar(500) COLLATE utf8_bin DEFAULT NULL,
  `ele_model` varchar(500) COLLATE utf8_bin DEFAULT NULL,
  `remarks` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `del_flag` char(1) COLLATE utf8_bin DEFAULT '0' COMMENT '删除标记',
  `stamp_code` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  `make_money` int(11) DEFAULT NULL COMMENT '刻章点收费',
  `province_money` int(11) DEFAULT NULL COMMENT '省经销商收费',
  `rc_money` int(11) DEFAULT NULL COMMENT '润城收费',
  `city_money` int(11) DEFAULT NULL COMMENT '市经销商收费',
  `bind_stamp` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '绑定电子/物理印章',
  `water_model` varchar(500) COLLATE utf8_bin DEFAULT NULL,
  `anti_fake_id` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `anti_fake_write` char(2) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='印章信息(物理)';

-- ----------------------------
-- Table structure for t_stamp_2
-- ----------------------------
DROP TABLE IF EXISTS `t_stamp_2`;
CREATE TABLE `t_stamp_2` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL,
  `use_comp` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '用章单位',
  `make_comp` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '刻章点',
  `now_comp` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '现在所属刻章公司',
  `record_id` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '最后一次备案id',
  `stamp_name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '印章名称',
  `allow_useNum` int(11) DEFAULT NULL COMMENT '印章允许使用次数',
  `stamp_state` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '印章制作状态',
  `stamp_shape` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '印章形式',
  `use_state` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '印章使用状态',
  `sys_state` varchar(20) COLLATE utf8_bin DEFAULT '' COMMENT '印章系统管控状态',
  `record_state` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '印章备案状态',
  `lastState_by` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '印章状态最后修改人',
  `lastState_date` datetime DEFAULT NULL COMMENT '印章状态最后修改时间',
  `stamp_texture` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '印章材质',
  `stamp_type` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '印章类型',
  `stamp_subtype` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '印章子类型',
  `stamp_code` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `shape_id` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '印模信息ID',
  `record_date` datetime DEFAULT NULL COMMENT
  `create_by` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '''备案日期'',
  `make_date` datetime DEFAULT NULL COMMENT ''制作日期'',
  `delivery_date` datetime DEFAULT NULL COMMENT ''交付日期'',创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `livePhoto` text COLLATE utf8_bin,
  `recordPhoto` text COLLATE utf8_bin,
  `phy_model` varchar(500) COLLATE utf8_bin DEFAULT NULL,
  `ele_model` varchar(500) COLLATE utf8_bin DEFAULT NULL,
  `remarks` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `del_flag` char(1) COLLATE utf8_bin DEFAULT '0' COMMENT 删除标记','
  `make_money` int(11) DEFAULT NULL COMMENT '刻章点费',
  `province_money` int(11) DEFAULT NULL COMMENT '省收经销商收费',
  `rc_money` int(11) DEFAULT NULL COMMENT '润城收费',
  `city_money` int(11) DEFAULT NULL COMMENT '市经销商收费',
  `bind_stamp` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '绑定电子/物理印章',
  `water_model` varchar(500) COLLATE utf8_bin DEFAULT NULL,
  `anti_fake_id` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `anti_fake_write` char(2) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='印章信息(电子)';

-- ----------------------------
-- Table structure for t_water
-- ----------------------------
DROP TABLE IF EXISTS `t_water`;
CREATE TABLE `t_water` (
  `id` varchar(64) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `file_path` varchar(500) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `update_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `create_by` varchar(64) DEFAULT NULL,
  `update_by` varchar(64) DEFAULT NULL,
  `del_flag` char(1) NOT NULL DEFAULT '0',
  `remarks` longtext,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
