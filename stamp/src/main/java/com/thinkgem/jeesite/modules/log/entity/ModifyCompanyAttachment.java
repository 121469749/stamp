/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.log.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;

/**
 * 企业信息变更Entity
 * @author linzhibao
 * @version 2018-08-22
 */
public class ModifyCompanyAttachment extends DataEntity<ModifyCompanyAttachment> {

	private static final long serialVersionUID = 1L;
	private Company company = new Company();		// 公司id
	private String attachs;		// 附件ids
	private String companyType;		// 公司类型
	private String agentName;		// 经办人
	private String agentPhone;		// 经办人手机号码
	private String agentCertType;		// 经办人证件类型
	private String agentCertCode;		// 经办人证件号码
	private String modifyReason; // 变更原因

	public ModifyCompanyAttachment() {
		super();
	}

	public ModifyCompanyAttachment(String id){
		super(id);
	}

	public Company getCompany() {
		return this.company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public String getAttachs() {
		return this.attachs;
	}

	public void setAttachs(String attachs) {
		this.attachs = attachs;
	}

	public String getCompanyType() {
		return companyType;
	}

	public void setCompanyType(String companyType) {
		this.companyType = companyType;
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

	public String getModifyReason() {
		return modifyReason;
	}

	public void setModifyReason(String modifyReason) {
		this.modifyReason = modifyReason;
	}
}