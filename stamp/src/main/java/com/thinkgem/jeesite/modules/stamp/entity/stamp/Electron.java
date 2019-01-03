package com.thinkgem.jeesite.modules.stamp.entity.stamp;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.OprationState;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.validation.Enumeration;
import com.thinkgem.jeesite.modules.sys.entity.User;

import java.util.Date;

/**
 * 电子印章信息Entity
 * @author Locker
 * @version 2017-05-13
 */
public class Electron {

    private String id;

    private String stampName;//电子印章名称

    private int allowUse; //允许使用次数

    private Date createTime; //创建时间

    private Date validDateStart; //有效开始时间

    private Date validDateEnd; // 有效结束时间

    private String type; //印章类型

    private String version; // 版本

    private String vendorId; //厂商

    private User createBy; //创建者

    private User user; //使用者

    private String sealPath ;//.seal 文件

    private String sealEleModel;//所采用的电子印模

    private Date rcDate;//润城控制时间

    @Enumeration(enumClass = OprationState.class)
    private OprationState sysOprState; // 润城科技管理员操作状态

    public Electron(){

    }

    public  Electron(String id){

        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getValidDateStart() {
        return validDateStart;
    }

    public void setValidDateStart(Date validDateStart) {
        this.validDateStart = validDateStart;
    }

    public Date getValidDateEnd() {
        return validDateEnd;
    }

    public void setValidDateEnd(Date validDateEnd) {
        this.validDateEnd = validDateEnd;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public User getCreateBy() {
        return createBy;
    }

    public void setCreateBy(User createBy) {
        this.createBy = createBy;
    }

    public int getAllowUse() {
        return allowUse;
    }

    public void setAllowUse(int allowUse) {
        this.allowUse = allowUse;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getStampName() {
        return stampName;
    }

    public void setStampName(String stampName) {
        this.stampName = stampName;
    }

    public String getSealPath() {
        return sealPath;
    }

    public void setSealPath(String sealPath) {
        this.sealPath = sealPath;
    }

    public String getSealEleModel() {
        return sealEleModel;
    }

    public void setSealEleModel(String sealEleModel) {
        this.sealEleModel = sealEleModel;
    }

    public Date getRcDate() {
        return rcDate;
    }

    public void setRcDate(Date rcDate) {
        this.rcDate = rcDate;
    }


    public OprationState getSysOprState() {
        return sysOprState;
    }

    public void setSysOprState(OprationState sysOprState) {
        this.sysOprState = sysOprState;
    }
}
