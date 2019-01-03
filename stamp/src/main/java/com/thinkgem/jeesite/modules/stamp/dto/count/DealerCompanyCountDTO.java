package com.thinkgem.jeesite.modules.stamp.dto.count;

import com.thinkgem.jeesite.modules.sys.entity.Area;

import java.util.Date;

/**
 * Created by Administrator on 2017/7/26.
 */
public class DealerCompanyCountDTO {

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
