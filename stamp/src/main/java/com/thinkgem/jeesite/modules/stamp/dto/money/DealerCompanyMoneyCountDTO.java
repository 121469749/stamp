package com.thinkgem.jeesite.modules.stamp.dto.money;

import com.thinkgem.jeesite.modules.sys.entity.Area;

import java.util.Date;

/**
 *
 * 省级以上经销商统计DTO
 *
 * Created by Locker on 17/7/24.
 */
public class DealerCompanyMoneyCountDTO {

    private Area area;

    private Date startDate; // 统计开始时间

    private Date endDate; // 统计结束时间


    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
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
