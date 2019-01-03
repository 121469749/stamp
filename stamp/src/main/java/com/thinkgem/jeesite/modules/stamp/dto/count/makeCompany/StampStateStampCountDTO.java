package com.thinkgem.jeesite.modules.stamp.dto.count.makeCompany;

/**
 * Created by Administrator on 2017/10/6.
 */
public class StampStateStampCountDTO {

    private int count;//总数

    private int useCount; //启用印章数目

    private int stopCount; //停止印章数目

    private int logoutCount; //缴销印章数目

    private int reportCount; //挂失印章数目

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getUseCount() {
        return useCount;
    }

    public void setUseCount(int useCount) {
        this.useCount = useCount;
    }

    public int getStopCount() {
        return stopCount;
    }

    public void setStopCount(int stopCount) {
        this.stopCount = stopCount;
    }

    public int getLogoutCount() {
        return logoutCount;
    }

    public void setLogoutCount(int logoutCount) {
        this.logoutCount = logoutCount;
    }

    public int getReportCount() {
        return reportCount;
    }

    public void setReportCount(int reportCount) {
        this.reportCount = reportCount;
    }
}
