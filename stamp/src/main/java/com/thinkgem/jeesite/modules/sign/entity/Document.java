package com.thinkgem.jeesite.modules.sign.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.stamp.entity.stamp.Stamp;
import com.thinkgem.jeesite.modules.sys.entity.User;

import java.util.Date;

/**
 *
 * 文件
 *
 * Created by Locker on 2017/9/13.
 */
public class Document extends DataEntity<Document> {

    private String fileName; //文件名

    private String fileOrgPath; //原文件路径

    private String fileSinaturePath; //签章后文件路径

    private Date singalDate; //签章日期

    private Date createDate;//创建时间

    private User user;// 所属用户

    private String status;//状态(0-未签署,1-已签署)

    private String remarks;// 备注

    private String hexCodeOrginal;//原文件16进制码串

    private String hexCodeSignture;//签署后文件16进制码串

    private String singatureMD5;//签署后文件MD5

    private Seal seal;

    private Stamp stamp;

    public Stamp getStamp() {
        return stamp;
    }

    public void setStamp(Stamp stamp) {
        this.stamp = stamp;
    }

    public Document() {
    }

    public Document(String id) {
        super(id);
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileOrgPath() {
        return fileOrgPath;
    }

    public void setFileOrgPath(String fileOrgPath) {
        this.fileOrgPath = fileOrgPath;
    }

    public String getFileSinaturePath() {
        return fileSinaturePath;
    }

    public void setFileSinaturePath(String fileSinaturePath) {
        this.fileSinaturePath = fileSinaturePath;
    }

    public Date getSingalDate() {
        return singalDate;
    }

    public void setSingalDate(Date singalDate) {
        this.singalDate = singalDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getHexCodeOrginal() {
        return hexCodeOrginal;
    }

    public void setHexCodeOrginal(String hexCodeOrginal) {
        this.hexCodeOrginal = hexCodeOrginal;
    }

    public String getHexCodeSignture() {
        return hexCodeSignture;
    }

    public void setHexCodeSignture(String hexCodeSignture) {
        this.hexCodeSignture = hexCodeSignture;
    }

    public Seal getSeal() {
        return seal;
    }

    public void setSeal(Seal seal) {
        this.seal = seal;
    }

    public String getSingatureMD5() {
        return singatureMD5;
    }

    public void setSingatureMD5(String singatureMD5) {
        this.singatureMD5 = singatureMD5;
    }
}
