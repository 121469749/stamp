package com.thinkgem.jeesite.modules.stamp.dto.money;

import com.thinkgem.jeesite.modules.stamp.entity.company.Company;

import java.util.Date;

/**
 *
 * 刻章点  刻章统计  传输对象
 *
 * Created by Locker on 2017/7/24.
 */
public class MakeCompanyMoneyCountDTO {

    private Company company = new Company(); //公司 查询对象

    private Date startDate; // 统计开始时间

    private Date endDate; // 统计结束时间

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "MakeCompanyMoneyCountDTO{" +
                " startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
