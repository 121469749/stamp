package com.thinkgem.jeesite.modules.stamp.entity.stamprecord;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.modules.stamp.entity.stamp.Stamp;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

import javax.xml.bind.annotation.XmlTransient;
import java.util.Date;

/**
 * 印章操作历史Entity
 * Created by sjk on 2017-05-22.s
 */
public class StampOperation {

    private Integer id;
    private Stamp stamp;    //印章id
    private User user;      //操作用户
    private String applyUsername;   //使用者名字
    private String operaType;     //操作类型
    private Date useDate;       //使用时间
    private String remarks;     //备注
    private User createBy;      //创建者
    private Date createDate;    //创建日期
    private Page<StampOperation> page;

    private Date beginDate;		// 开始日期
    private Date endDate;		// 结束日期

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Stamp getStamp() {
        return stamp;
    }

    public void setStamp(Stamp stamp) {
        this.stamp = stamp;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getOperaType() {
        return operaType;
    }

    public void setOperaType(String operaType) {
        this.operaType = operaType;
    }

    public Date getUseDate() {
        return useDate;
    }

    public void setUseDate(Date useDate) {
        this.useDate = useDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public User getCreateBy() {
        return createBy;
    }

    public void setCreateBy(User createBy) {
        this.createBy = createBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getApplyUsername() {
        return applyUsername;
    }

    public void setApplyUsername(String applyUsername) {
        this.applyUsername = applyUsername;
    }

    @JsonIgnore
    @XmlTransient
    public Page<StampOperation> getPage() {
        if (page == null){
            page = new Page<StampOperation>();
        }
        return page;
    }

    public Page<StampOperation> setPage(Page<StampOperation> page) {
        this.page = page;
        return page;
    }

    //插入前调用
    public void preInsert() {
        this.createBy = UserUtils.getUser();
        this.createDate = new Date();
    }
}
