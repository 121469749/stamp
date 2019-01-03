package com.thinkgem.jeesite.modules.stamp.entity.stamprecord;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

import javax.xml.bind.annotation.XmlTransient;
import java.util.Date;

/**
 * 印章操作日志Entity
 * Created by sjk on 2017-07-03.
 */
public class StampLog {

    private Integer id;
    private String title;
    private User createBy;
    private Date createDate;

    private Date beginDate;		// 开始日期
    private Date endDate;		// 结束日期

    private Page<StampLog> page;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    @JsonIgnore
    @XmlTransient
    public Page<StampLog> getPage() {
        if (page == null){
            page = new Page<StampLog>();
        }
        return page;
    }

    public Page<StampLog> setPage(Page<StampLog> page) {
        this.page = page;
        return page;
    }

    //插入前调用
    public void preInsert() {
        this.createBy = UserUtils.getUser();
        this.createDate = new Date();
    }
}
