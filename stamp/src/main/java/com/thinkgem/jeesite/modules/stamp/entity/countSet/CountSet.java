package com.thinkgem.jeesite.modules.stamp.entity.countSet;

import com.thinkgem.jeesite.modules.stamp.common.Enumeration.CompanyType;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.StampShape;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.sys.entity.Area;
import com.thinkgem.jeesite.modules.sys.entity.User;

import java.util.Date;

/**
 *
 *  印章刻制数量设定 -entity
 *
 * Created by Locker on 2017/8/17.
 */
public class CountSet {

    private String id;

    private Company company; //被限制的公司

    private Area area; //公司对应的区域

    private StampShape stampShape; //印章类型

    private int count ; //可以刻制的数量

    private User createBy;

    private User updateBy;

    private Date createDate;

    private Date updateDate;


    public CountSet(){

    }

    public CountSet(StampShape stampShape, int count) {
        this.stampShape = stampShape;
        this.count = count;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public StampShape getStampShape() {
        return stampShape;
    }

    public void setStampShape(StampShape stampShape) {
        this.stampShape = stampShape;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public User getCreateBy() {
        return createBy;
    }

    public void setCreateBy(User createBy) {
        this.createBy = createBy;
    }

    public User getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(User updateBy) {
        this.updateBy = updateBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}
