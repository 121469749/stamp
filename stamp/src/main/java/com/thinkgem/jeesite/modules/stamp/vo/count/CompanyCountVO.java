package com.thinkgem.jeesite.modules.stamp.vo.count;


import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 *
 *
 * Created by Locker on 2017/7/25.
 */
public class CompanyCountVO {

    private int stampRecordCount; //印章备案数量(5种类型结合起来)

    private int phyStampCount;//物理印章刻制数量

    private int eleStampCount;// 电子印章刻制数量

    private int logoutStampCount;//缴销印章数量

    private int reportStampCount;//挂失印章数量

    public CompanyCountVO(){

    }

    public CompanyCountVO(int stampRecordCount, int phyStampCount, int eleStampCount, int logoutStampCount, int reportStampCount) {
        this.stampRecordCount = stampRecordCount;
        this.phyStampCount = phyStampCount;
        this.eleStampCount = eleStampCount;
        this.logoutStampCount = logoutStampCount;
        this.reportStampCount = reportStampCount;
    }

    public int getStampRecordCount() {
        return stampRecordCount;
    }

    public void setStampRecordCount(int stampRecordCount) {
        this.stampRecordCount = stampRecordCount;
    }

    public int getPhyStampCount() {
        return phyStampCount;
    }

    public void setPhyStampCount(int phyStampCount) {
        this.phyStampCount = phyStampCount;
    }

    public int getEleStampCount() {
        return eleStampCount;
    }

    public void setEleStampCount(int eleStampCount) {
        this.eleStampCount = eleStampCount;
    }

    public int getLogoutStampCount() {
        return logoutStampCount;
    }

    public void setLogoutStampCount(int logoutStampCount) {
        this.logoutStampCount = logoutStampCount;
    }

    public int getReportStampCount() {
        return reportStampCount;
    }

    public void setReportStampCount(int reportStampCount) {
        this.reportStampCount = reportStampCount;
    }
}
