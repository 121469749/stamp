package com.thinkgem.jeesite.modules.stamp.dto.money;

import com.thinkgem.jeesite.modules.stamp.common.Enumeration.PaymentType;
import com.thinkgem.jeesite.modules.stamp.common.Enumeration.validation.Enumeration;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.sys.entity.Area;
import com.thinkgem.jeesite.modules.sys.entity.User;

import java.util.Date;
import java.util.List;

/**
 * Created by Locker on 2017/7/18.
 */
public class MoneySettingDTO {

    private String id; //id

    @Enumeration(enumClass = PaymentType.class)
    private PaymentType paymentTypes; // 收费类型

    private String stampTexture; //物理章材料

    private double money; //金额

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public PaymentType getPaymentTypes() {
        return paymentTypes;
    }

    public void setPaymentTypes(PaymentType paymentTypes) {
        this.paymentTypes = paymentTypes;
    }

    public String getStampTexture() {
        return stampTexture;
    }

    public void setStampTexture(String stampTexture) {
        this.stampTexture = stampTexture;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }
}
