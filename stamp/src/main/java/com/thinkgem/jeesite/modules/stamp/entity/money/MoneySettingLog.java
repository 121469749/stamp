package com.thinkgem.jeesite.modules.stamp.entity.money;

import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.sys.entity.User;

import java.util.Date;

/**
 *
 * 收费设定修改日志
 *
 * Created by Locker on 2017/7/17.
 */
public class MoneySettingLog {

    private String id ;// id

    private String type;//操作类型

    private int uMoney; //u盾刻制收费

    private int phyMoney; //物理刻制印章收费

    private int eleMoney; // 电子刻制印章收费

    private Date createDate; //创建时间

    private User createUser; // 创建用户

    private Company company; // 所属公司

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getuMoney() {
        return uMoney;
    }

    public void setuMoney(int uMoney) {
        this.uMoney = uMoney;
    }

    public int getPhyMoney() {
        return phyMoney;
    }

    public void setPhyMoney(int phyMoney) {
        this.phyMoney = phyMoney;
    }

    public int getEleMoney() {
        return eleMoney;
    }

    public void setEleMoney(int eleMoney) {
        this.eleMoney = eleMoney;
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

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
