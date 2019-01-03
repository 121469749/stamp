package com.thinkgem.jeesite.modules.stamp.entity.money;

import com.thinkgem.jeesite.modules.stamp.common.Enumeration.PaymentType;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.WorkType;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.validation.Enumeration;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.sys.entity.Area;
import com.thinkgem.jeesite.modules.sys.entity.User;

import java.util.Date;

/**
 *
 * 收费设定
 *
 *
 * Created by Locker on 2017/7/17.
 */
public class MoneySetting {

    private String id; //id

    private Company company; //收费公司

    private Area area;//缴费区域

    @Enumeration(enumClass = PaymentType.class)
    private PaymentType paymentType; // 收费类型

    private String stampTexture; //物理章材料

    private int money; //金额

    private Date createDate; //创建时间

    private User createUser; // 创建用户

    private User updateUser; //更新用户

    private Date updateDate; //更新时间

    public MoneySetting(){

    }

    public MoneySetting(Company company, PaymentType paymentType) {
        this.company = company;
        this.paymentType = paymentType;
    }

    public MoneySetting(Company company, PaymentType paymentType,String stampTexture) {
        this.company = company;
        this.paymentType = paymentType;
        this.stampTexture =stampTexture;
    }

    public MoneySetting(Company company, Area area, PaymentType paymentType, int money) {
        this.company = company;
        this.area = area;
        this.paymentType = paymentType;
        this.money = money;
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

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public String getStampTexture() {
        return stampTexture;
    }

    public void setStampTexture(String stampTexture) {
        this.stampTexture = stampTexture;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public User getCreateUser() {
        return createUser;
    }

    public void setCreateUser(User createUser) {
        this.createUser = createUser;
    }

    public User getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(User updateUser) {
        this.updateUser = updateUser;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public String toString() {
        return "MoneySetting{" +
                "id='" + id + '\'' +
                ", company=" + company +
                ", area=" + area +
                ", paymentType=" + paymentType +
                ", stampTexture='" + stampTexture + '\'' +
                ", money=" + money +
                '}';
    }
}
