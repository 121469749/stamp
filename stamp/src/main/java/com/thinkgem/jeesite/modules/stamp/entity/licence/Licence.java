/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.stamp.entity.licence;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.CheckState;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.WorkType;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.validation.Enumeration;
import com.thinkgem.jeesite.modules.stamp.common.util.attachment.entity.Attachment;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;

import java.util.List;

/**
 * 许可证业务信息Entity
 * @author Locker
 * @version 2017-05-13
 */
public class Licence extends DataEntity<Licence> {
	
	private static final long serialVersionUID = 1L;
	private Company makeComp;   // 刻章公司
//	private String makeComp;
	@Enumeration(enumClass = WorkType.class)
	private WorkType workType;		// 办理业务类型

	private String workReason;		// 办理业务理由
	private String compName;		// 单位名称
	private String busType;		// 经济性质
	private String legalName;		// 法定代表人
	private String legalPhone;		// 法人手机
	private String legalCertType;		// 法人证件类型
	private String legalCertCode;		// 法人证件号码
	private String policeName;		// 治安负责人
	private String policeIdCode;		// 治安负责人身份证号码
	private String policePhone;		// 治安负责人联系电话
	private String headCount;		// 单位总人数
	private String specialCount;		// 单位特业从业人员
	private String agentName;		// 经办人姓名
	private String agentCertCode;		// 经办人身份证号码
	private String agentPhone;		// 经办人电话号码
	private String attachs;		// 附件信息列表
	private String busRun;		// 企业经营

	@Enumeration(enumClass = CheckState.class)
	private CheckState checkState;		// 审核状态

	private String checkReason;		// 审核原因
	
	public Licence() {
		super();

	}

	public Licence(String id){
		super(id);
	}





	public Company getMakeComp() {
		return makeComp;
	}

	public void setMakeComp(Company makeComp) {
		this.makeComp = makeComp;
	}

	public WorkType getWorkType() {
		return workType;
	}

	public void setWorkType(WorkType workType) {
		this.workType = workType;
	}
	
	public String getWorkReason() {
		return workReason;
	}

	public void setWorkReason(String workReason) {
		this.workReason = workReason;
	}
	
	public String getCompName() {
		return compName;
	}

	public void setCompName(String compName) {
		this.compName = compName;
	}
	
	public String getBusType() {
		return busType;
	}

	public void setBusType(String busType) {
		this.busType = busType;
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
	
	public String getPoliceName() {
		return policeName;
	}

	public void setPoliceName(String policeName) {
		this.policeName = policeName;
	}
	
	public String getPoliceIdCode() {
		return policeIdCode;
	}

	public void setPoliceIdCode(String policeIdCode) {
		this.policeIdCode = policeIdCode;
	}
	
	public String getPolicePhone() {
		return policePhone;
	}

	public void setPolicePhone(String policePhone) {
		this.policePhone = policePhone;
	}
	
	public String getHeadCount() {
		return headCount;
	}

	public void setHeadCount(String headCount) {
		this.headCount = headCount;
	}
	
	public String getSpecialCount() {
		return specialCount;
	}

	public void setSpecialCount(String specialCount) {
		this.specialCount = specialCount;
	}
	
	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	
	public String getAgentCertCode() {
		return agentCertCode;
	}

	public void setAgentCertCode(String agentCertCode) {
		this.agentCertCode = agentCertCode;
	}
	
	public String getAgentPhone() {
		return agentPhone;
	}

	public void setAgentPhone(String agentPhone) {
		this.agentPhone = agentPhone;
	}
	
	public String getAttachs() {
		return attachs;
	}

	public void setAttachs(String attachs) {
		this.attachs = attachs;
	}
	
	public String getBusRun() {
		return busRun;
	}

	public void setBusRun(String busRun) {
		this.busRun = busRun;
	}
	
	public CheckState getCheckState() {
		return checkState;
	}

	public void setCheckState(CheckState checkState) {
		this.checkState = checkState;
	}
	
	public String getCheckReason() {
		return checkReason;
	}

	public void setCheckReason(String checkReason) {
		this.checkReason = checkReason;
	}


	
}