package com.thinkgem.jeesite.modules.police.entity;

import com.thinkgem.jeesite.modules.stamp.entity.company.Company;

import java.util.List;

/**
 * @Auther: linzhibao
 * @Date: 2018-08-20 09:27
 * @Description:
 */
public class ModifyCompanyInfo {

    private Company company = new Company();

    private String modifyReason; // 修改原因

    private String serialNum; // 流水号

    private String agentName; // 经办人姓名

    private String agentPhone;//经办人手机号码

    private String agentCertType;// 经办人证件类型

    private String agentCertCode;// 经办人证件号码

    private List<String> fileType;

    public String getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getModifyReason() {
        return modifyReason;
    }

    public void setModifyReason(String modifyReason) {
        this.modifyReason = modifyReason;
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

    public List<String> getFileType() {
        return fileType;
    }

    public void setFileType(List<String> fileType) {
        this.fileType = fileType;
    }
}
