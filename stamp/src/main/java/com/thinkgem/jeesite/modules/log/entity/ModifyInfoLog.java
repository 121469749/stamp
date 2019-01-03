/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.log.entity;

import java.util.Date;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 操作日志明细Entity
 * @author xucaikai
 * @version 2018-08-08
 */
public class ModifyInfoLog extends DataEntity<ModifyInfoLog> {
	
	private static final long serialVersionUID = 1L;
	private String tableName;		// 表名
	private String tableText;       // 表的注释,即整个表的注释
	private String columnName;		// 字段
	private String columnText;		// 字段名
	private String businessName;		// 操作对象(业务名称)
	private String operationType;		// 操作类型
	private String companyName;		// 公司名称
	private String oldValue;		// 旧值
	private String newValue;		// 新值
	private String makeCom;		// 刻章点
	private Date beginCreateDate;		// 开始 创建时间
	private Date endCreateDate;		// 结束 创建时间
	private String type;           // 操作类型,modifyCompanyInfo代表修改企业信息操作
	private String sysUserType; //用户类型,3.刻章点, 5.公安
	private String companyType; // 企业类型 1.刻章点,2.用章企业

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	
	public String getColumnText() {
		return columnText;
	}

	public void setColumnText(String columnText) {
		this.columnText = columnText;
	}
	
	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	
	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}
	
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	public String getOldValue() {
		return oldValue;
	}

	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}
	
	public String getNewValue() {
		return newValue;
	}

	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}
	
	public String getMakeCom() {
		return makeCom;
	}

	public void setMakeCom(String makeCom) {
		this.makeCom = makeCom;
	}
	
	public Date getBeginCreateDate() {
		return beginCreateDate;
	}

	public void setBeginCreateDate(Date beginCreateDate) {
		this.beginCreateDate = beginCreateDate;
	}
	
	public Date getEndCreateDate() {
		return endCreateDate;
	}

	public void setEndCreateDate(Date endCreateDate) {
		this.endCreateDate = endCreateDate;
	}

	public String getTableText() {
		return tableText;
	}

	public void setTableText(String tableText) {
		this.tableText = tableText;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSysUserType() {
		return sysUserType;
	}

	public void setSysUserType(String sysUserType) {
		this.sysUserType = sysUserType;
	}

	public String getCompanyType() {
		return companyType;
	}

	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}

	public ModifyInfoLog() {
		super();
	}

	public ModifyInfoLog(String id){
		super(id);
	}
	public ModifyInfoLog(String tableName, String tableText,String operationType,String businessName,String type,String companyName){
		this.operationType = operationType;
		this.tableName = tableName;
		this.tableText = tableText;
		this.type = type;
		this.businessName = businessName;
		this.companyName = companyName;

	}
}