package com.thinkgem.jeesite.modules.stamp.dto.money;

import com.thinkgem.jeesite.modules.stamp.common.Enumeration.PaymentType;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.validation.Enumeration;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.stamp.entity.money.MoneySetting;
import com.thinkgem.jeesite.modules.sys.entity.Area;

import java.util.List;

/**
 * Created by Locker on 2017/7/20.
 */
public class MoneySettingListDTO {

    private List<String> id; //id

    private List<PaymentType> paymentType; // 收费类型

    private List<String> stampTexture; //物理章材料

    private List<Double> money; //金额

    private Company company; //收费公司

    private Area area;//缴费区域

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

    public List<String> getId() {
        return id;
    }

    public void setId(List<String> id) {
        this.id = id;
    }

    public List<PaymentType> getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(List<PaymentType> paymentType) {
        this.paymentType = paymentType;
    }

    public List<String> getStampTexture() {
        return stampTexture;
    }

    public void setStampTexture(List<String> stampTexture) {
        this.stampTexture = stampTexture;
    }

    public List<Double> getMoney() {
        return money;
    }

    public void setMoney(List<Double> money) {
        this.money = money;
    }

}
