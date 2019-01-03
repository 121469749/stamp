/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.stamp.entity.exchange;

import javax.validation.constraints.NotNull;

import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.sys.entity.Area;

import java.util.ArrayList;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 完成数据交换Entity
 * @author ADD BY LINZHIBAO
 * @version 2018-09-11
 */
public class DataExchange extends DataEntity<DataExchange> {
	
	private static final long serialVersionUID = 1L;
	private String businessId;		// 业务流水号
	private String stampType;		// 印章类型
	private String stampShape;		// 印章属性（物理/电子）
	private String stampTexture;		// 印章材料
	private Integer sealCount;		// 刻制数量
	private String businessType;		// 业务部门名称
	private Area area;		// 刻章点行政区划
	private String makeCompSocialcreditcode;		// 刻章点统一社会信用代码
	private String useCompArea;		// 企业行政区划
	private String soleCode;		// 企业统一社会信用代码
	private String companyName;		// 企业名称
	private Integer compType;		// 企业类型
	private String compAddress;		// 企业住所
	private Date useFoundingdate;		// 企业创建日期
	private String legalName;		// 法人名称
	private String legalPhone;		// 法人手机
	private String legalCertType;		// 法人证件类型
	private String legalCertCode;		// 法人证件号码
	private String agentName;		// 经办人名称
	private String agentPhone;		// 经办人手机
	private String agentCertType;		// 经办人证件类型
	private String agentCertCode;		// 经办人证件号码
	private String attachments;		// 附件信息
	private String exFlag;		// 数据交换标志
	private String exFailReason;		// 交换失败原因
	private String default1;		// 备用字段1
	private String default2;		// 备用字段2
	private String default3;		// 备用字段3
	private String sealEntityListJson; // 刻章对象, // 用来接收参数,
	private ArrayList<SealEntity> sealEntityList; // 用来数据库存储
	private Company useCompany;
	private Company makeCompany;
	
	public DataExchange() {
		super();
	}

	public DataExchange(String id){
		super(id);
	}

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public String getStampType() {
		return stampType;
	}

	public void setStampType(String stampType) {
		this.stampType = stampType;
	}

	public String getStampShape() {
		return stampShape;
	}

	public void setStampShape(String stampShape) {
		this.stampShape = stampShape;
	}

	public String getStampTexture() {
		return stampTexture;
	}

	public void setStampTexture(String stampTexture) {
		this.stampTexture = stampTexture;
	}

	public Integer getSealCount() {
		return sealCount;
	}

	public void setSealCount(Integer sealCount) {
		this.sealCount = sealCount;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public String getMakeCompSocialcreditcode() {
		return makeCompSocialcreditcode;
	}

	public void setMakeCompSocialcreditcode(String makeCompSocialcreditcode) {
		this.makeCompSocialcreditcode = makeCompSocialcreditcode;
	}

	public String getUseCompArea() {
		return useCompArea;
	}

	public void setUseCompArea(String useCompArea) {
		this.useCompArea = useCompArea;
	}

	public String getSoleCode() {
		return soleCode;
	}

	public void setSoleCode(String soleCode) {
		this.soleCode = soleCode;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Integer getCompType() {
		return compType;
	}

	public void setCompType(Integer compType) {
		this.compType = compType;
	}

	public String getCompAddress() {
		return compAddress;
	}

	public void setCompAddress(String compAddress) {
		this.compAddress = compAddress;
	}

	public Date getUseFoundingdate() {
		return useFoundingdate;
	}

	public void setUseFoundingdate(Date useFoundingdate) {
		this.useFoundingdate = useFoundingdate;
	}

	public String getLegalName() {
		return legalName;
	}

	public void setLegalName(String legalName) {
		this.legalName = legalName;
	}

	public String getLegalPhone() {
		return legalPhone;
	}

	public void setLegalPhone(String legalPhone) {
		this.legalPhone = legalPhone;
	}

	public String getLegalCertType() {
		return legalCertType;
	}

	public void setLegalCertType(String legalCertType) {
		this.legalCertType = legalCertType;
	}

	public String getLegalCertCode() {
		return legalCertCode;
	}

	public void setLegalCertCode(String legalCertCode) {
		this.legalCertCode = legalCertCode;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getAgentPhone() {
		return agentPhone;
	}

	public void setAgentPhone(String agentPhone) {
		this.agentPhone = agentPhone;
	}

	public String getAgentCertType() {
		return agentCertType;
	}

	public void setAgentCertType(String agentCertType) {
		this.agentCertType = agentCertType;
	}

	public String getAgentCertCode() {
		return agentCertCode;
	}

	public void setAgentCertCode(String agentCertCode) {
		this.agentCertCode = agentCertCode;
	}

	public String getAttachments() {
		return attachments;
	}

	public void setAttachments(String attachments) {
		this.attachments = attachments;
	}

	public String getExFlag() {
		return exFlag;
	}

	public void setExFlag(String exFlag) {
		this.exFlag = exFlag;
	}

	public String getExFailReason() {
		return exFailReason;
	}

	public void setExFailReason(String exFailReason) {
		this.exFailReason = exFailReason;
	}

	public String getDefault1() {
		return default1;
	}

	public void setDefault1(String default1) {
		this.default1 = default1;
	}

	public String getDefault2() {
		return default2;
	}

	public void setDefault2(String default2) {
		this.default2 = default2;
	}

	public String getDefault3() {
		return default3;
	}

	public void setDefault3(String default3) {
		this.default3 = default3;
	}

	public String getSealEntityListJson() {
		return sealEntityListJson;
	}

	public void setSealEntityListJson(String sealEntityListJson) {
		this.sealEntityListJson = sealEntityListJson;
	}

	public ArrayList<SealEntity> getSealEntityList() {
		return sealEntityList;
	}

	public void setSealEntityList(ArrayList<SealEntity> sealEntityList) {
		this.sealEntityList = sealEntityList;
	}

	public Company getUseCompany() {
		return useCompany;
	}

	public void setUseCompany(Company useCompany) {
		this.useCompany = useCompany;
	}

	public Company getMakeCompany() {
		return makeCompany;
	}

	public void setMakeCompany(Company makeCompany) {
		this.makeCompany = makeCompany;
	}
}