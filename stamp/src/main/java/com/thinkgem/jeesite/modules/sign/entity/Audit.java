package com.thinkgem.jeesite.modules.sign.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.sys.entity.User;

import java.util.Date;

/**
 * 安全审计功能Entity
 * @author bb
 * @version 2018-07-16
 */
public class Audit extends DataEntity<Audit> {

    private static final long serialVersionUID = 1L;

    public static final String AUDIT_MAKE = "1";//制章
    public static final String AUDIT_SIGN = "2";//签章
    public static final String AUDIT_REVOKE = "3";//撤章
    public static final String AUDIT_VERIFY = "4";//验章

    public static final String SUCCESS = "0";//操作成功
    public static final String FAIL = "1";//操作失败


    private User user;		// 操作者（制章者/签章者/验章者）
    private String seal;		// 印章唯一标识
    private String auditType;		// 审计类型（1制章，2签章，3撤章，4验章）
    private Date auditDate;		// 审计日期
    private String fileName;		// 文件名（签/撤章有值）
    private String fileSigneddata;		// 文件摘要（签/撤章有值）
    private String auditResult;		// 操作结果（0成功，1失败）
    private String reason;		// 原因（若操作失败有值）
    private String certSerialNumber;		// 证书序列号
    private String ip;		// IP地址
    private String mac;		// MAC地址


    public Audit() {
        super();
    }

    public Audit(String id){
        super(id);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getSeal() {
        return seal;
    }

    public void setSeal(String seal) {
        this.seal = seal;
    }

    public String getAuditType() {
        return auditType;
    }

    public void setAuditType(String auditType) {
        this.auditType = auditType;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getAuditDate() {
        return auditDate;
    }

    public void setAuditDate(Date auditDate) {
        this.auditDate = auditDate;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileSigneddata() {
        return fileSigneddata;
    }

    public void setFileSigneddata(String fileSigneddata) {
        this.fileSigneddata = fileSigneddata;
    }

    public String getAuditResult() {
        return auditResult;
    }

    public void setAuditResult(String auditResult) {
        this.auditResult = auditResult;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getCertSerialNumber() {
        return certSerialNumber;
    }

    public void setCertSerialNumber(String certSerialNumber) {
        this.certSerialNumber = certSerialNumber;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

}
