package com.thinkgem.jeesite.modules.stamp.dto.useCompany;

/**
 * Created by sjk on 2017-06-02.
 */
public class InsertOperationDto {

    private String stampId;     //印章id
    private String useDate;     //使用时间
    private String remarks;     //使用用途
    private String userId;      //操作者id
    private String username;    //操作者名字
    private String applyUsername;   //使用者名字
    private String companyId;   //当前登录用户企业id

    public String getStampId() {
        return stampId;
    }

    public void setStampId(String stampId) {
        this.stampId = stampId;
    }

    public String getUseDate() {
        return useDate;
    }

    public void setUseDate(String useDate) {
        this.useDate = useDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getApplyUsername() {
        return applyUsername;
    }

    public void setApplyUsername(String applyUsername) {
        this.applyUsername = applyUsername;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }
}
