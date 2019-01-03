package com.thinkgem.jeesite.modules.stamp.vo.policeOperation;

import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.stamp.entity.stamprecord.StampRecord;

import java.util.Date;

/**
 * Created by hjw-pc on 2017/7/11.
 */
public class StampPermissionVO {
    private String stampId;
    private String stampName;
    private String agentName;
    private String agentId;
    private Company company;
    private Company makeCompany;
    private Date applyDate;
    private String type;
    private String policeAreaName;
    private String stampTexture;  //章材
    private String userName;
    private StampRecord lastRecord;


    public StampPermissionVO(String stampId, String agentName, String agentId, Company company, Date applyDate, String type, String policeAreaName) {
        this.stampId = stampId;
        this.agentName = agentName;
        this.agentId = agentId;
        this.company = company;
        this.applyDate = applyDate;
        this.type = type;
        this.policeAreaName = policeAreaName;
    }

    public StampPermissionVO(String stampId, String stampName, String agentName, String agentId, Company company, Date applyDate, String type, String policeAreaName) {
        this.stampId = stampId;
        this.stampName = stampName;
        this.agentName = agentName;
        this.agentId = agentId;
        this.company = company;
        this.applyDate = applyDate;
        this.type = type;
        this.policeAreaName = policeAreaName;
    }

    public StampPermissionVO(String stampId, String stampName, String agentName, String agentId, Company company,Company makeCompany, Date applyDate, String type, String policeAreaName) {
        this.stampId = stampId;
        this.stampName = stampName;
        this.agentName = agentName;
        this.agentId = agentId;
        this.company = company;
        this.makeCompany = makeCompany;
        this.applyDate = applyDate;
        this.type = type;
        this.policeAreaName = policeAreaName;
    }

    public StampPermissionVO(String stampId, String stampName, String agentName, String agentId, Company company,Company makeCompany, Date applyDate, String type, String policeAreaName,String stampTexture,String userName,StampRecord lastRecord) {
        this.stampId = stampId;
        this.stampName = stampName;
        this.agentName = agentName;
        this.agentId = agentId;
        this.company = company;
        this.makeCompany = makeCompany;
        this.applyDate = applyDate;
        this.type = type;
        this.policeAreaName = policeAreaName;
        this.stampTexture = stampTexture;
        this.userName = userName;
        this.lastRecord = lastRecord;
    }

    public String getStampId() {

        return stampId;
    }


    public String getStampName() {
        return stampName;
    }

    public void setStampName(String stampName) {
        this.stampName = stampName;
    }

    public void setStampId(String stampId) {
        this.stampId = stampId;
    }

    public String getPoliceAreaName() {
        return policeAreaName;
    }

    public void setPoliceAreaName(String policeAreaName) {
        this.policeAreaName = policeAreaName;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Company getMakeCompany() {
        return makeCompany;
    }

    public void setMakeCompany(Company makeCompany) {
        this.makeCompany = makeCompany;
    }

    public String getStampTexture() {
        return stampTexture;
    }

    public void setStampTexture(String stampTexture) {
        this.stampTexture = stampTexture;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public StampRecord getLastRecord() {
        return lastRecord;
    }

    public void setLastRecord(StampRecord lastRecord) {
        this.lastRecord = lastRecord;
    }
}
