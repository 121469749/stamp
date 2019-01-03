package com.thinkgem.jeesite.modules.stamp.dto.count;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.stamp.entity.company.Company;
import com.thinkgem.jeesite.modules.stamp.entity.stamp.Stamp;

import java.util.Date;

/**
 *
 *  企业信息统计DTO对象
 *
 * Created by Locker on 2017/7/25.
 */
public class CompanyCountDTO extends DataEntity<Company>{

    private Company company = new Company();

    private Stamp stamp = new Stamp();

    private Date startDate;

    private Date endDate;

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }


    public Stamp getStamp() {
        return stamp;
    }

    public void setStamp(Stamp stamp) {
        this.stamp = stamp;
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
}
